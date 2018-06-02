//package org.ltx.hc.business.config;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @author ltxlouis
// * @since 3/22/2018
// */
//@EnableWebSecurity
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private static final String[] AUTH_WHITELIST = {
//            // -- swagger ui
//            "/swagger-resources/configuration/ui",
//            "/swagger-resources",
//            "/swagger-resources/configuration/security",
//            "/swagger-ui.html",
//            "/v2/api-docs",
//            "/webjars/**",
//            "/configuration/ui",
//            "/configuration/security"
//    };
//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers(AUTH_WHITELIST);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//    }
//
//}
