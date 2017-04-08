package sic.service.impl;

import sic.service.IAfipService;
import afip.wsaa.wsdl.LoginCms;
import afip.wsfe.wsdl.AlicIva;
import afip.wsfe.wsdl.ArrayOfAlicIva;
import afip.wsfe.wsdl.ArrayOfFECAEDetRequest;
import afip.wsfe.wsdl.FEAuthRequest;
import afip.wsfe.wsdl.FECAECabRequest;
import afip.wsfe.wsdl.FECAEDetRequest;
import afip.wsfe.wsdl.FECAERequest;
import afip.wsfe.wsdl.FECAESolicitar;
import java.io.Reader;
import java.io.StringReader;
import java.util.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sic.modelo.AfipWSAACredencial;
import sic.modelo.Empresa;
import sic.modelo.FacturaVenta;
import sic.service.BusinessServiceException;
import sic.service.IConfiguracionDelSistemaService;
import sic.util.FormatterFechaHora;
import sic.util.Utilidades;

@Service
public class AfipServiceImpl implements IAfipService {

    private final AfipWebServiceSOAPClient afipWebServiceSOAPClient;
    private final IConfiguracionDelSistemaService configuracionDelSistemaService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());    
    private final FormatterFechaHora formatterFechaHora = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHA_INTERNACIONAL);

    @Autowired
    public AfipServiceImpl(AfipWebServiceSOAPClient afipWebServiceSOAPClient, IConfiguracionDelSistemaService cds) {
        this.afipWebServiceSOAPClient = afipWebServiceSOAPClient;
        this.configuracionDelSistemaService = cds;
    }

    @Override
    public AfipWSAACredencial getAfipWSAACredencial(String afipNombreServicio, Empresa empresa) {
        AfipWSAACredencial afipCred = new AfipWSAACredencial();
        String p12file = configuracionDelSistemaService.getConfiguracionDelSistemaPorEmpresa(empresa).getPathCertificadoAfip();
        String p12signer = configuracionDelSistemaService.getConfiguracionDelSistemaPorEmpresa(empresa).getFirmanteCertificadoAfip();
        String p12pass = configuracionDelSistemaService.getConfiguracionDelSistemaPorEmpresa(empresa).getPasswordCertificadoAfip();
        Long ticketTime = 3600000L; //siempre devuelve por 12hs
        byte[] loginTicketRequest_xml_cms = afipWebServiceSOAPClient.crearCMS(p12file, p12pass, p12signer, afipNombreServicio, ticketTime);
        LoginCms loginCms = new LoginCms();
        loginCms.setIn0(Base64.getEncoder().encodeToString(loginTicketRequest_xml_cms));
        String loginTicketResponse = afipWebServiceSOAPClient.loginCMS(loginCms);
        try {
            Reader tokenReader = new StringReader(loginTicketResponse);
            Document tokenDoc = new SAXReader(false).read(tokenReader);
            afipCred.setToken(tokenDoc.valueOf("/loginTicketResponse/credentials/token"));
            afipCred.setSign(tokenDoc.valueOf("/loginTicketResponse/credentials/sign"));
        } catch (DocumentException ex) {
            LOGGER.error(ex.getMessage());
            throw new BusinessServiceException("Error procesando el XML de la respuesta");
        }
        return afipCred;
    }

    @Override
    public void autorizarFacturaVenta(FacturaVenta factura) {
        AfipWSAACredencial afipCredencial = this.getAfipWSAACredencial("wsfe", factura.getEmpresa());        
        FEAuthRequest feAuthRequest = new FEAuthRequest();
        feAuthRequest.setCuit(factura.getEmpresa().getCuip());
        feAuthRequest.setSign(afipCredencial.getSign());
        feAuthRequest.setToken(afipCredencial.getToken());        
        FECAESolicitar fecaeSolicitud = new FECAESolicitar();
        fecaeSolicitud.setAuth(feAuthRequest);
        fecaeSolicitud.setFeCAEReq(this.transformFacturaVentaToFECAERequest(factura));
        afipWebServiceSOAPClient.FECAESolicitar(fecaeSolicitud);
    }    
    
    public FECAERequest transformFacturaVentaToFECAERequest(FacturaVenta factura) {
        FECAERequest fecaeRequest = new FECAERequest();        
        FECAECabRequest cabecera = new FECAECabRequest();
        FECAEDetRequest detalle = new FECAEDetRequest();        
        // 1: Factura A, 2: Nota de Débito A, 3: Nota de Crédito A, 6: Factura B, 7: Nota de Débito B, 8: Nota de Crédito B. 11: Factura C
        switch (factura.getTipoComprobante()) {
            case FACTURA_A:
                cabecera.setCbteTipo(1);
                detalle.setDocTipo(80);
                detalle.setDocNro(Long.valueOf(factura.getCliente().getIdFiscal().replace("-", "")));
                break;
            case FACTURA_B:
                cabecera.setCbteTipo(6);
                detalle.setDocTipo(99);
                detalle.setDocNro(0);
                break;
            case FACTURA_C:
                cabecera.setCbteTipo(11);
                detalle.setDocTipo(0);
                detalle.setDocNro(0);
                break;
        }
        cabecera.setCantReg(1); // Cantidad de registros del detalle del comprobante o lote de comprobantes de ingreso
        cabecera.setPtoVta(1); // Punto de Venta del comprobante que se está informando. Si se informa más de un comprobante, todos deben corresponder al mismo punto de venta
        fecaeRequest.setFeCabReq(cabecera);
        ArrayOfFECAEDetRequest arrayDetalle = new ArrayOfFECAEDetRequest();        
        detalle.setCbteDesde(2); // numero de comprobante ???
        detalle.setCbteHasta(2); // numero de comprobante ???
        detalle.setConcepto(1); // Concepto del Comprobante. Valores permitidos: 1 Productos, 2 Servicios, 3 Productos y Servicios        
        detalle.setCbteFch(formatterFechaHora.format(factura.getFecha()).replace("/", "")); // Fecha del comprobante (yyyymmdd)
        detalle.setImpTotal(Utilidades.round(factura.getTotal(), 2)); // Importe total del comprobante, Debe ser igual a Importe neto no gravado + Importe exento + Importe neto gravado + todos los campos de IVA al XX% + Importe de tributos        
        detalle.setImpNeto(Utilidades.round(factura.getSubTotal_neto(),2)); // Importe neto gravado. Debe ser menor o igual a Importe total y no puede ser menor a cero. Para comprobantes tipo C este campo corresponde al Importe del Sub Total        
        detalle.setImpIVA(Utilidades.round(factura.getIva_105_neto() + factura.getIva_21_neto(),2)); // Suma de los importes del array de IVA. Para comprobantes tipo C debe ser igual a cero (0).
        detalle.setImpTrib(0); // Suma de los importes del array de tributos
        detalle.setMonId("PES"); // Código de moneda del comprobante. Consultar método FEParamGetTiposMonedas para valores posibles
        detalle.setMonCotiz(1); // Cotización de la moneda informada. Para PES, pesos argentinos la misma debe ser 1            
        ArrayOfAlicIva arrayIVA = new ArrayOfAlicIva();
        AlicIva alicIVA21 = new AlicIva();
        alicIVA21.setId(5); // Valores: 5 (21%), 4 (10.5%)
        alicIVA21.setBaseImp(Utilidades.round((100 * factura.getIva_21_neto()) / 21, 2)); // Se calcula con: (100 * IVA_neto) / %IVA
        alicIVA21.setImporte(Utilidades.round(factura.getIva_21_neto(),2));
        arrayIVA.getAlicIva().add(alicIVA21);        
//        AlicIva alicIVA105 = new AlicIva();
//        alicIVA105.setId(4); // Valores: 5 (21%), 4 (10.5%)
//        alicIVA105.setBaseImp(Utilidades.round((100 * factura.getIva_105_neto()) / 10.5, 2)); // Se calcula con: (100 * IVA_neto) / %IVA
//        alicIVA105.setImporte(Utilidades.round(factura.getIva_105_neto(),2));
//        arrayIVA.getAlicIva().add(alicIVA105);
        detalle.setIva(arrayIVA); // Array para informar las alícuotas y sus importes asociados a un comprobante <AlicIva>. Para comprobantes tipo C y Bienes Usados – Emisor Monotributista no debe informar el array.
        arrayDetalle.getFECAEDetRequest().add(detalle);        
        // FALTA AGREGAR TRIBUTOS PARA IMP. INTERNO
        fecaeRequest.setFeDetReq(arrayDetalle);
        return fecaeRequest;
    }

}
