package com.example.springcloudgateway.config;

import com.example.springcloudgateway.filter.CustomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author : 최종철
 */
@Configuration
public class FilterConfig {

	@Autowired
	private CustomFilter customFilter;
	
	/**
	 * yml에 route 설정되어 있으면 우선적으로 적용 되어 아래 bean은 동작 하지 않음.
	 * @param builder
	 * @return
	 */
	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				//first-service
				.route(r -> r.path("/first-service/**")
						//.filters(f -> f.filter(customFilter.apply((CustomFilter.Config) null)))//bean 꽂을때 사용
						.uri("http://localhost:8081"))
				//second-service
				.route(r -> r.path("/second-service/**")
						.filters(f -> f.addResponseHeader("second-request", "second-request-header")
								.addResponseHeader("second-response", "second-response-header"))
						.uri("http://localhost:8082"))
				.build();
	}
}
