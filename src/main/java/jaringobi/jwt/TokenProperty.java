package jaringobi.jwt;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class TokenProperty {
    private String secret = "default-secret-value";
    private long tokenLifeTime = 600;
    private long tokenRefreshTime = 24*60*60; // 86400
}
