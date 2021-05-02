package com.timeline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2) // Docket : Swagger 설정을 할 수 있게 도와주는 클래스
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.
                        basePackage("com.timeline.controller")) //.apis(): API 문서를 만들어줄 범위를 지정한다. com.timeline 하위 구조를 탐색하여 문서를 생성해준다.
                .paths(PathSelectors.any()) //패키지 안에 모든 API를 한 번에 볼 수 있다
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "title",
                "description",
                "version",
                "http://localhost:8080",
                new Contact("Contact Me", "http://localhost:8080", "timeline0418@gmail.com"),
                "Licenses",
                "http://localhost:8080",
                new ArrayList<>()
        );
    }

    /*
    @Bean
    public Docket apiV2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false) // useDefaultResponseMessages() : false로 설정하면 불필요한 응답코드와 설명을 제거할 수 있다
                .groupName("groupName2") // groupName() :  Bean이 여러 개면 명시해줘야함. Bean이 여러 개면 groupName이 출동하여 오류를 발생하기 때문
                .select() // select() : ApiSelectorBuilder를 생성하여 apis()와 paths()를 사용할 수 있게 해줌
                .apis(RequestHandlerSelectors.
                        basePackage("javable.controller")) // apis() : api가 작성되있는 패키지를 지정. 예를들어  javable > controller 안에 api controller가 있으면 basePackage를 javable.controller로 지정
                .paths(PathSelectors.ant("/posts/**")).build();  // .paths(): API 의 URL 경로를 지정할 수 있다. .paths(PathSelectors.ant("api/v1/**")) 와 같이 하면 http://localhost/api/v1/ 하위 경로를 가지는 API에 대해 문서를 생성해준다
    }
     */

}
