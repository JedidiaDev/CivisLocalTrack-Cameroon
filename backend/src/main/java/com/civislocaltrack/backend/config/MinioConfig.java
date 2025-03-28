package com.civislocaltrack.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioConfig {
    
    @Value("${minio.endpoint}") // URL du serveur MinIO (par exemple, http://localhost:9000)
    private String endpoint;

    @Value("${minio.access-key}") // Identifiant d'accès (par exemple, minioadmin)
    private String accessKey;

    @Value("${minio.secret-key}") // Mot de passe secret (par exemple, minioadmin)
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
    
}
