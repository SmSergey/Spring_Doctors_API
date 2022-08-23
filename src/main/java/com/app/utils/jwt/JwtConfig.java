package com.app.utils.jwt;


import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Data
@Getter
@Repository
@ConfigurationProperties(prefix = "jwt")
@PropertySource("classpath:jwt.yml")
public class JwtConfig {
    private String header;
    private String secret;

    private Long accessExpiration;
    private Long refreshExpiration;

}
