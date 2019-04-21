package com.redislabs.handsonlab;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.redislabs.modules.rejson.JReJSON;

@Configuration
public class JReJSONConfig {

	@Bean
	JReJSON jReJson(RedisProperties props) {
		return new JReJSON(props.getHost(), props.getPort());
	}

}