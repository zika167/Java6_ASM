package poly.edu.asm_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Static resources mapping
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCachePeriod(3600);
        
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/")
                .setCachePeriod(3600);
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/")
                .setCachePeriod(3600);
        
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/")
                .setCachePeriod(3600);
        
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
    }
}