package sic;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.ResourceBundle;
import sic.controller.UnauthorizedException;

public class JwtFilter extends GenericFilterBean {
    
    private final String secretkey;
    
    public JwtFilter(String key) {
        this.secretkey = key;
    }
    
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res,
            final FilterChain chain) 
            throws IOException, ServletException {
        
        final HttpServletRequest httpRequest = (HttpServletRequest) req;
        final String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        final String token = authHeader.substring(7); // The part after "Bearer "

        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(secretkey)
                                .parseClaimsJws(token)
                                .getBody();
            httpRequest.setAttribute("claims", claims);
        } catch (JwtException ex) {
            throw new UnauthorizedException(ResourceBundle.getBundle("Mensajes")
                    .getString("mensaje_usuario_logInInvalido"));
        }

        chain.doFilter(req, res);
    }     
}
