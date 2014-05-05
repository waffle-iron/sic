package sic.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sic.modelo.XMLFileConfig;

public class ConfiguracionRepository {

    private String pathConexionConfig = System.getProperty("user.home") + "/conexionConfig.xml";
    private static final String mensaje_ErrorTransformerConfigurationException = ResourceBundle.getBundle("Mensajes").getString("mensaje_ErrorTransformerConfigurationException");
    private static final String mensaje_ErrorTransformerException = ResourceBundle.getBundle("Mensajes").getString("mensaje_ErrorTransformerException");
    private static final String mensaje_ErrorParserConfigurationException = ResourceBundle.getBundle("Mensajes").getString("mensaje_ErrorParserConfigurationException");
    private static final String mensaje_ErrorXPathExpressionException = ResourceBundle.getBundle("Mensajes").getString("mensaje_ErrorXPathExpressionException");
    private static final String mensaje_ErrorSAXException = ResourceBundle.getBundle("Mensajes").getString("mensaje_ErrorSAXException");
    private static final String mensaje_ErrorIOException = ResourceBundle.getBundle("Mensajes").getString("mensaje_ErrorIOException");
    private static final String mensaje_ErrorFileNotFoundException = ResourceBundle.getBundle("Mensajes").getString("mensaje_ErrorFileNotFoundException");
    private static final Logger log = Logger.getLogger(ConfiguracionRepository.class.getPackage().getName());

    private void contruirXMLconDOM() throws XMLException {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlVersion("1.0");

            Element configuracion = (Element) document.createElement("CONFIGURACION");
            Element conexion;
            Element datosConexion;

            //nodo Conexion
            conexion = document.createElement("CONEXION");
            configuracion.appendChild(conexion);

            datosConexion = document.createElement("HOST");
            datosConexion.appendChild(document.createTextNode(""));
            conexion.appendChild(datosConexion);

            datosConexion = document.createElement("BD");
            datosConexion.appendChild(document.createTextNode(""));
            conexion.appendChild(datosConexion);

            datosConexion = document.createElement("PORT");
            datosConexion.appendChild(document.createTextNode(""));
            conexion.appendChild(datosConexion);

            document.appendChild(configuracion);

            // Escritura de fichero_destino.xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(pathConexionConfig));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException ex) {
            log.error(mensaje_ErrorTransformerConfigurationException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorTransformerConfigurationException, ex);

        } catch (TransformerException ex) {
            log.error(mensaje_ErrorTransformerException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorTransformerException, ex);

        } catch (ParserConfigurationException ex) {
            log.error(mensaje_ErrorParserConfigurationException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorParserConfigurationException, ex);
        }
    }

    public void leerXMLconDOM() throws XMLException {
        try {
            //verifica si existe el archivo
            File archivo = new File(pathConexionConfig);
            if (!archivo.canRead()) {
                contruirXMLconDOM();
            }

            // Implementacion DOM por defecto de Java
            // Construimos nuestro DocumentBuilder
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Procesamos el fichero XML y obtenemos nuestro objeto Document
            Document doc = documentBuilder.parse(new InputSource(new FileInputStream(pathConexionConfig)));

            // Buscamos una etiqueta mediante XPath.
            // Implementacion de XPath por defecto en Java

            //CONEXION
            Node etiqueta = (Node) (XPathFactory.newInstance().newXPath().evaluate(
                    "/CONFIGURACION/CONEXION/HOST", doc, XPathConstants.NODE));
            if (etiqueta != null) {
                XMLFileConfig.setHostConexion(etiqueta.getTextContent());
            }

            etiqueta = (Node) (XPathFactory.newInstance().newXPath().evaluate(
                    "/CONFIGURACION/CONEXION/BD", doc, XPathConstants.NODE));
            if (etiqueta != null) {
                XMLFileConfig.setBdConexion(etiqueta.getTextContent());
            }

            etiqueta = (Node) (XPathFactory.newInstance().newXPath().evaluate(
                    "/CONFIGURACION/CONEXION/PORT", doc, XPathConstants.NODE));
            if (etiqueta != null && !etiqueta.getTextContent().equals("")) {
                XMLFileConfig.setPuertoConexion(Integer.parseInt(etiqueta.getTextContent()));
            } else {
                XMLFileConfig.setPuertoConexion(0);
            }

        } catch (XPathExpressionException ex) {
            log.error(mensaje_ErrorXPathExpressionException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorXPathExpressionException, ex);

        } catch (ParserConfigurationException ex) {
            log.error(mensaje_ErrorParserConfigurationException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorParserConfigurationException, ex);

        } catch (SAXException ex) {
            log.error(mensaje_ErrorSAXException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorSAXException, ex);

        } catch (IOException ex) {
            log.error(mensaje_ErrorIOException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorIOException, ex);
        }
    }

    public void guardarXMLconDOM(String pathEtiqueta, String valor) throws XMLException {
        try {
            //verifica si existe el archivo
            File archivo = new File(pathConexionConfig);
            if (!archivo.canRead()) {
                contruirXMLconDOM();
            }
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = documentBuilder.parse(new InputSource(new FileInputStream(pathConexionConfig)));
            Node etiqueta = (Node) (XPathFactory.newInstance().newXPath().evaluate(pathEtiqueta, doc, XPathConstants.NODE));
            if (etiqueta != null) {
                etiqueta.setTextContent(valor);

                // Escritura de fichero_destino.xml
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(pathConexionConfig));
                transformer.transform(source, result);
            }

        } catch (TransformerConfigurationException ex) {
            log.error(mensaje_ErrorTransformerConfigurationException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorTransformerConfigurationException, ex);

        } catch (TransformerException ex) {
            log.error(mensaje_ErrorTransformerException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorTransformerException, ex);

        } catch (ParserConfigurationException ex) {
            log.error(mensaje_ErrorParserConfigurationException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorParserConfigurationException, ex);

        } catch (FileNotFoundException ex) {
            log.error(mensaje_ErrorFileNotFoundException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorFileNotFoundException, ex);

        } catch (SAXException ex) {
            log.error(mensaje_ErrorSAXException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorSAXException, ex);

        } catch (IOException ex) {
            log.error(mensaje_ErrorIOException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorIOException, ex);

        } catch (XPathExpressionException ex) {
            log.error(mensaje_ErrorXPathExpressionException + " - " + ex.getMessage());
            throw new XMLException(mensaje_ErrorXPathExpressionException, ex);
        }
    }
}
