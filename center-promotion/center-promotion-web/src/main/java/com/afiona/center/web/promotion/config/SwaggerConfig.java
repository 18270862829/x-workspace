package com.afiona.center.web.promotion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置
 *
 * @author ZhangShuaiQiang
 * @date 2019/12/26 下午7:04
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.afiona.center.web.promotion.api")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("促销中心服务层")
                //创建人
                .contact(new Contact("afiona", "http://www.deepexi.com", "chailinbo@deepexi.com"))
                //版本号
                .version("1.0")
                //描述
                .description("api管理").build();
    }
}