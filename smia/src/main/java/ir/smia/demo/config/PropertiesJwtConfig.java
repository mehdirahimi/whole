package ir.smia.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("jwt.auth.converter")
public class PropertiesJwtConfig {
    private String resourceId;
    private String principalAttribute;
}
