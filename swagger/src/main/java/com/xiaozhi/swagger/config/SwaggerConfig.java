package com.xiaozhi.swagger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * Swagger接口文档配置类
 *
 * @author zhongyong
 * @date 20181119
 * @since v1.0
 */
@ConditionalOnProperty(name = "cps.swagger.show.enabled", matchIfMissing = true)
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket sysApi() {
        return getDocket("后台管理", "后台管理相关接口", "1.0",
                "A1-后台管理", "com.meitq.cifox.api.module.sys");
    }

    @Bean
    public Docket basicApi() {
        return getDocket("基础数据", "基础数据相关接口", "1.0",
                "A2-基础数据", "com.meitq.cifox.api.module.basic");
    }

    @Bean
    public Docket extensionApi() {
        return getDocket("延伸期预测", "延伸期预测相关接口", "1.0",
                "A3-延伸期预测", "com.meitq.cifox.api.module.extension");
    }

    @Bean
    public Docket climateEventsApi() {
        return getDocket("专项预测", "气候事件相关接口", "1.0",
                "A4-专项预测", "com.meitq.cifox.api.module.climateevents");
    }

    @Bean
    public Docket intelligenceApi() {
        return getDocket("智能预测", "智能预测相关接口", "1.0",
                "A5-智能预测", "com.meitq.cifox.api.module.intelligence");
    }

    @Bean
    public Docket gridForecastApi() {
        return getDocket("格点预测", "格点预测相关接口", "1.0",
                "A6-格点预测", "com.meitq.cifox.api.module.gridforecast");
    }

    @Bean
    public Docket gridSummerPreApi() {
        return getDocket("夏季次季节降水预测", "夏季次季节降水预测相关接口", "1.0",
                "A7-夏季次季节降水预测", "com.meitq.cifox.api.module.summerpre");
    }

    private Docket getDocket(String name, String description, String version, String groupName, String packgetPath) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(name, description, version))
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packgetPath))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(String name, String description, String version) {
        return new ApiInfoBuilder().title("内蒙古气候预测系统-" + name).description(description).version(version).build();
    }

}
