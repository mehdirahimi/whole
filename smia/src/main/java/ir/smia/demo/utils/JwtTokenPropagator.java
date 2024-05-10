package ir.smia.demo.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenPropagator implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // Retrieve the JWT token from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof Jwt jwt) {
            String jwtToken = jwt.getTokenValue();
            template.header("Authorization", "Bearer " + jwtToken);
        }
    }
}
