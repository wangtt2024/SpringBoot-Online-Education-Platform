package io.course.access.CourseAccess.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // This maps to all endpoints.  You can be more specific.
            .allowedOrigins("http://localhost:5173") // Allowed origin.  Update with your Vue app's URL
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Allowed HTTP methods
            .allowedHeaders("*") // Allowed headers.  Consider being more restrictive in production
            .allowCredentials(true) // Important if you're using cookies or Authorization headers
            .maxAge(3600); // Max age of the preflight request (in seconds)
    }
}