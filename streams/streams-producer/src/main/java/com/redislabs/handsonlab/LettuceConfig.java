package com.redislabs.handsonlab;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;

@Configuration
public class LettuceConfig {

	@Bean(destroyMethod = "shutdown")
	ClientResources clientResources() {
		return DefaultClientResources.create();
	}

	@Bean(destroyMethod = "shutdown")
	RedisClient redisClient(RedisProperties props, ClientResources clientResources) {
		return RedisClient.create(clientResources, RedisURI.create(props.getHost(), props.getPort()));
	}

	@Bean(destroyMethod = "close")
	StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
		return redisClient.connect();
	}
}