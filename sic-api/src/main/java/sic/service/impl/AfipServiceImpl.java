package sic.service.impl;

import sic.service.IAfipService;
import afip.wsaa.wsdl.LoginCms;
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
import sic.service.IConfiguracionDelSistemaService;

@Service
public class AfipServiceImpl implements IAfipService {

    private final AfipWebServiceSOAPClient afipWebServiceSOAPClient;
    private final IConfiguracionDelSistemaService configuracionDelSistemaService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AfipServiceImpl(AfipWebServiceSOAPClient afipWebServiceSOAPClient, IConfiguracionDelSistemaService configuracionDelSistemaService) {
        this.afipWebServiceSOAPClient = afipWebServiceSOAPClient;
        this.configuracionDelSistemaService = configuracionDelSistemaService;
    }

    @Override
    public void getToken() {
        String service = "wsfe";        
        String p12file = configuracionDelSistemaService.getConfiguracionDelSistemaPorId(1).getPathCertificadoAfip();
        String p12signer = configuracionDelSistemaService.getConfiguracionDelSistemaPorId(1).getFirmanteCertificadoAfip();
        String p12pass = configuracionDelSistemaService.getConfiguracionDelSistemaPorId(1).getPasswordCertificadoAfip();
        Long ticketTime = 3600000L;
        byte[] loginTicketRequest_xml_cms = afipWebServiceSOAPClient.crearCMS(p12file, p12pass, p12signer, service, ticketTime);
        LoginCms loginCms = new LoginCms();
        loginCms.setIn0(Base64.getEncoder().encodeToString(loginTicketRequest_xml_cms));
        String loginTicketResponse = afipWebServiceSOAPClient.loginCMS(loginCms);
        try {
            Reader tokenReader = new StringReader(loginTicketResponse);
            Document tokenDoc = new SAXReader(false).read(tokenReader);
            String token = tokenDoc.valueOf("/loginTicketResponse/credentials/token");
            String sign = tokenDoc.valueOf("/loginTicketResponse/credentials/sign");
            System.out.println("TOKEN: " + token);
            System.out.println("SIGN: " + sign);
        } catch (DocumentException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
