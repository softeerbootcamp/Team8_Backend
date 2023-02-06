package site.devroad.softeer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:8080", "http://localhost:8081",
                        "http://www.devroad.site", "http://devroad.site",
                        "https://www.devroad.site", "https://devroad.site")
                .exposedHeaders("jwt")
                .maxAge(3600); // 3600초 동안 preflight 결과를 캐시에 저장
    }
}