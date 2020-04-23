package com.afiona.center.user.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * @author liuqiang
 * @date 2020/1/2 11:28
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${config.oauth2.accessTokenUri}")
    private String accessTokenUri;

    @Value("${config.oauth2.clientId}")
    private String clientId;

    @Value("${config.oauth2.clientSecret}")
    private String clientSecret;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
//                .apis(RequestHandlerSelectors.basePackage("com.afiona.center.user"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("用户中心层")
                //创建人
                .contact(new Contact("deepexi", "http://www.deepexi.com", "zhangshuaiqiang@deepexi.com"))
                //版本号
                .version("1.0")
                //描述
                .description("api管理").build();
    }

    private OAuth securitySchema(){
        List<GrantType> grantTypes = Lists.newArrayList();
        GrantType passwordCredentialGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
        grantTypes.add(passwordCredentialGrant);
        return new OAuth("oauth2", authorizationScopes(), grantTypes);
    }

    private List<AuthorizationScope> authorizationScopes(){
        List<AuthorizationScope> authorizationScopes = Lists.newArrayList();
        authorizationScopes.add(new AuthorizationScope("any", "normal"));
        authorizationScopes.add(new AuthorizationScope("miniapp", "only miniapp"));
        return authorizationScopes;
    }

    @Bean
    public SecurityConfiguration securityConfiguration(){
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .realm("afiona-realm")
                .appName("afiona")
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(
                Collections.singletonList(new SecurityReference("oauth2", authorizationScopes().toArray(new AuthorizationScope[]{}))))
                .forPaths(PathSelectors.any())
                .build();
    }
}
