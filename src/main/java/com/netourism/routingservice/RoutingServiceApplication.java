package com.netourism.routingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
//@EnableZuulProxy
@Configuration
@EnableDiscoveryClient
public class RoutingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoutingServiceApplication.class, args);
	}
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("event-service",r -> r
						.path("/events/**")
						.uri("http://localhost:8000")

						//.uri("lb://event-service")
				)
				.route(r -> r.path("/locations/**")
						.filters(f -> f
								//.prefixPath("/api")
								.addResponseHeader("X-Powered-By","Netourism Gateway Service")
						)
						.uri("http://localhost:9000")
				)

				.route(r -> r.path("/recommendations/**")
						.filters(f -> f
								//.prefixPath("/api")
								.addResponseHeader("X-Powered-By","Netourism Gateway Service")
						)
						.uri("http://localhost:3002")
				)
				.route(r -> r.path("/auth/**")
						.filters(f -> f
								//.prefixPath("/api")
								.addResponseHeader("X-Powered-By","Netourism Gateway Service")
						)
						.uri("http://localhost:3000")
				)

				.build();
	}
}
