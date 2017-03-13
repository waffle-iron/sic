package sic.service.impl;

import sic.service.IAfipService;
import afip.wsaa.wsdl.LoginCms;
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

@Service
public class AfipServiceImpl implements IAfipService {

    private final AfipWebServiceSOAPClient afipWebServiceSOAPClient;
    private final IConfiguracionDelSistemaService configuracionDelSistemaService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());    
    private final FormatterFechaHora formatterFechaHora = new FormatterFechaHora(FormatterFechaHora.FORMATO_FECHA_INTERNACIONAL);

    @Autowired
    public AfipServiceImpl(AfipWebServiceSOAPClient afipWebServiceSOAPClient, IConfiguracionDelSistemaService configuracionDelSistemaService) {
        this.afipWebServiceSOAPClient = afipWebServiceSOAPClient;
        this.configuracionDelSistemaService = configuracionDelSistemaService;
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
        cabecera.setCantReg(1); // Cantidad de registros del detalle del comprobante o lote de comprobantes de ingreso
        cabecera.setPtoVta(1); // Punto de Venta del comprobante que se está informando. Si se informa más de un comprobante, todos deben corresponder al mismo punto de venta
        // 1: Factura A, 2: Nota de Débito A, 3: Nota de Crédito A, 6: Factura B, 7: Nota de Débito B, 8: Nota de Crédito B. 11: Factura C
        switch (factura.getTipoFactura()) {
            case 'A':
                cabecera.setCbteTipo(1);
                break;
            case 'B':
                cabecera.setCbteTipo(6);
                break;
            case 'C':
                cabecera.setCbteTipo(11);
                break;
        }
        fecaeRequest.setFeCabReq(cabecera);
        ArrayOfFECAEDetRequest arrayDetalle = new ArrayOfFECAEDetRequest();
        FECAEDetRequest detalle = new FECAEDetRequest();
        detalle.setCbteDesde(1); // numero de comprobante ???
        detalle.setCbteHasta(1); // numero de comprobante ???
        detalle.setConcepto(1); // Concepto del Comprobante. Valores permitidos: 1 Productos, 2 Servicios, 3 Productos y Servicios
        switch (factura.getTipoFactura()) {
            case 'A':
                detalle.setDocTipo(80);
                detalle.setDocNro(Long.valueOf(factura.getCliente().getIdFiscal()));
                break;
            case 'B':
                detalle.setDocTipo(99);
                detalle.setDocNro(0);
                break;
            case 'C':
                detalle.setDocTipo(0);
                detalle.setDocNro(0);
                break;
        }
        detalle.setCbteFch(formatterFechaHora.format(factura.getFecha()).replace('/', ' ')); // Fecha del comprobante (yyyymmdd)
        detalle.setImpTotal(factura.getTotal()); // Importe total del comprobante, Debe ser igual a Importe neto no gravado + Importe exento + Importe neto gravado + todos los campos de IVA al XX% + Importe de tributos
        detalle.setImpTotConc(0); // Importe neto no gravado. Debe ser menor o igual a Importe total y no puede ser menor a cero. No puede ser mayor al Importe total de la operación ni menor a cero (0). Para comprobantes tipo C debe ser igual a cero (0). Para comprobantes tipo Bienes Usados – Emisor Monotributista este campo corresponde al importe subtotal
        detalle.setImpNeto(factura.getSubTotal_neto()); // Importe neto gravado. Debe ser menor o igual a Importe total y no puede ser menor a cero. Para comprobantes tipo C este campo corresponde al Importe del Sub Total
        detalle.setImpOpEx(0); // Importe exento. Debe ser menor o igual a Importe total y no puede ser menor a cero. Para comprobantes tipo C debe ser igual a cero (0)
        detalle.setImpIVA(factura.getIva_105_neto() + factura.getIva_21_neto()); // Suma de los importes del array de IVA. Para comprobantes tipo C debe ser igual a cero (0).
        detalle.setImpTrib(0); // Suma de los importes del array de tributos
        detalle.setMonId("PES"); // Código de moneda del comprobante. Consultar método FEParamGetTiposMonedas para valores posibles
        detalle.setMonCotiz(1); // Cotización de la moneda informada. Para PES, pesos argentinos la misma debe ser 1            
        arrayDetalle.getFECAEDetRequest().add(detalle);
        fecaeRequest.setFeDetReq(arrayDetalle);
        return fecaeRequest;
    }

}
