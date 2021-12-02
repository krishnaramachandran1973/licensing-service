package com.cts.license.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "example")
public class ServiceConfig {

	@Value("${example.property}")
	private String property;

	@Value("${example.redis.server}")
	private String redisServer = "";

	@Value("${example.redis.port}")
	private String redisPort = "";

}
