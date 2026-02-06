package com.pauletchells.europa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pauletchells.europa.auth.ApiAuthFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class SecurityConfig {

	@Bean
	public FilterRegistrationBean<ApiAuthFilter> apiAuthFilter() {
		FilterRegistrationBean<ApiAuthFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new ApiAuthFilter());
		registrationBean.addUrlPatterns("/api/game/*", "/api/player/*", "/api/board/*");
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
