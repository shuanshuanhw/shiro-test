package com.example.shirotest.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


//@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    //    registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/css/**").excludePathPatterns("/js/**").excludePathPatterns("/imagesss/**").excludePathPatterns("/images/**").excludePathPatterns("/index").excludePathPatterns("/userland").excludePathPatterns("/goout").excludePathPatterns("/qrImageByOutputStream").excludePathPatterns("/qrImageByResponseEntity").excludePathPatterns("/userLandEva");

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");

     //   registry.addViewController("/admineva").setViewName("admineva");
    }
/*  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//映射图片保存地址
        registry.addResourceHandler("/images/**").addResourceLocations("file:C:\\pm\\src\\main\\resources\\static\\images\\");
    }*/
}