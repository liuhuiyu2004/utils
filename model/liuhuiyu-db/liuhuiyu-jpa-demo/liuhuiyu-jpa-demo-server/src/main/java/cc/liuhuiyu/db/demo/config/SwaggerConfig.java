package cc.liuhuiyu.db.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-11-13 14:19
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket allApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("全部api", "全部-api", "1.0"))
                .select()
                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant(WebAddressConstant.API_ROOT + "/**"))
                .paths(PathSelectors.ant( "/**"))
                .build()
                .groupName("全部api接口文档V1.0")
                .pathMapping("/");
    }



    /**
     * api信息
     *
     * @param name        标题
     * @param description 描述
     * @param version     版本
     * @return ApiInfo
     */
    private ApiInfo apiInfo(String name, String description, String version) {
        return new ApiInfoBuilder()
                .title(name)
                .description(description)
                .version(version)
                .build();
    }
}
