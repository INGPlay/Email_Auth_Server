package server.api.emailAuth.config;

import server.api.emailAuth.config.converter.StringToActiveStatusConverter;
import server.api.emailAuth.config.converter.StringToOAuthTypeConverter;
import server.api.emailAuth.config.converter.StringToRoleEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToActiveStatusConverter());
        registry.addConverter(new StringToOAuthTypeConverter());
        registry.addConverter(new StringToRoleEnumConverter());
    }
}
