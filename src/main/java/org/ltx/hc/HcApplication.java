package org.ltx.hc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.nio.charset.Charset;

/**
 * @author ltxlouis
 */
@SpringBootApplication
public class HcApplication {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return builder.additionalMessageConverters(m).build();
    }

//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/", buildConfig());
//        return new CorsFilter(source);
//    }

    public static void main(String[] args) {
        SpringApplication.run(HcApplication.class, args);
    }
}
