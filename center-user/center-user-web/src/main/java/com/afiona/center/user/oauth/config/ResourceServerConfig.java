package com.afiona.center.user.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;

import java.util.Arrays;

/**
 * 资源服务配置
 *
 * @author dengweiyi
 * @date 2020-03-12
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().disable().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and().requestMatchers().anyRequest()
                .and().anonymous()
                .and().authorizeRequests().antMatchers("/", "/v2/**", "/swagger-ui.html", "/swagger-ui.html/swagger-resources/**",
                "/null/swagger-resources/**", "/swagger-resources/**", "/v2/api-docs",
                "/webjars/**", "/favicon.ico", "/css/**", "/js/**", "/images/**", "/index").permitAll()
                .and().authorizeRequests().antMatchers("/oauth/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/oauth/**").permitAll()
                .and().authorizeRequests().antMatchers("/**").authenticated();
//                .and().headers().addHeaderWriter((request, response) -> {
//                response.addHeader("Access-Control-Allow-Origin", "*");
//                if (request.getMethod().equals("OPTIONS")) {
//                    response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
//                    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
//                }});
    }
}
