package site.devroad.softeer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class SpringConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;
    private DataSource dataSource;

    public SpringConfig(AuthInterceptor authInterceptor, DataSource dataSource) {
        this.authInterceptor = authInterceptor;
        this.dataSource = dataSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/api/user/signup")
                .excludePathPatterns("/api/user/signin");
    }
}
