package org.vang.john.hotelbooking.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/index").setViewName("index");
//		registry.addViewController("/").setViewName("index");
//		registry.addViewController("/").setViewName("");
//		registry.addViewController("/admin").setViewName("admin");
//		registry.addViewController("/employee").setViewName("employee");
//		registry.addViewController("/user").setViewName("user");
		
		//login
		registry.addViewController("/login").setViewName("xlogin");
		
	}

}
