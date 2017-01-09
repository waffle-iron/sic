package sic;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus.Series;
import org.springframework.web.client.RestClientResponseException;

public class ApiResponseErrorHandler implements ResponseErrorHandler {
    
    private static final Logger LOGGER = Logger.getLogger(ApiResponseErrorHandler.class.getPackage().getName());

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        Series series = response.getStatusCode().series();
        return (Series.CLIENT_ERROR.equals(series) || Series.SERVER_ERROR.equals(series));
    }   
    
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String mensaje = IOUtils.toString(response.getBody());      
        LOGGER.error(response.getStatusCode() + " - " + mensaje);                
        throw new RestClientResponseException(mensaje, response.getRawStatusCode(),
                                              response.getStatusText(), response.getHeaders(),
                                              null, Charset.defaultCharset());
    }
    
}
