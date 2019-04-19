package com.redislabs.handsonlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloRedisSolution implements ApplicationRunner {

	@Autowired
	StringRedisTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(HelloRedisSolution.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Step 1: Set the hello message in Redis
		template.opsForValue().set("message", "Hello Redis!");

		// Step 2: Retrieve the hello message from Redis
		String message = template.opsForValue().get("message");
		log.info(message);

		// Step 3: Increment our run counter
		long count = template.opsForValue().increment("count");
		log.info("Hello Redis has been run {} times", count);
	}

}
