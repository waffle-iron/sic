package sic.controller;

import java.util.Date;
import java.util.ResourceBundle;
import javax.persistence.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sic.service.BusinessServiceException;

@ControllerAdvice
public class ControllersExceptionHandler {
    
    private static final Logger LOGGER = Logger.getLogger(ControllersExceptionHandler.class.getPackage().getName());
     
    @ExceptionHandler(BusinessServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleBusinessServiceException(BusinessServiceException ex) {        
        String mensaje = ex.getMessage() + " Reference ID: " + new Date().getTime();
        LOGGER.error(mensaje);
        return mensaje;
    }
    
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String handleUnauthorizedException(UnauthorizedException ex) {
        String mensaje = ex.getMessage() + " Reference ID: " + new Date().getTime();
        LOGGER.error(mensaje + " " + ex.getCause());
        return mensaje;
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleEntityNotFoundException(EntityNotFoundException ex) {        
        String mensaje = ex.getMessage() + " Reference ID: " + new Date().getTime();
        LOGGER.error(mensaje);
        return mensaje;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception ex) {
        String mensaje = " Reference ID: " + new Date().getTime();
        LOGGER.error(ex.getMessage() + mensaje);
        return ResourceBundle.getBundle("Mensajes").getString("mensaje_error_request") + mensaje;
    }
}
