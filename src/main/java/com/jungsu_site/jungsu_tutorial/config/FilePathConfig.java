package com.jungsu_site.jungsu_tutorial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilePathConfig implements WebMvcConfigurer {

    //BG_img관련
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Uploads/**")
                .addResourceLocations("file:./src/main/resources/Uploads/");
    }

    // 기타 다른 스프링 MVC 구성 메소드들을 추가할 수 있습니다.
}