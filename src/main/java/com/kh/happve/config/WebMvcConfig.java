package com.kh.happve.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//외부 이미지 매핑할 uri 지정
		registry.addResourceHandler("/getFiles/**")
		//실제 파일이 존재하는 디렉토리 지정
		        .addResourceLocations("file:///C:/upload/");
	}
}
