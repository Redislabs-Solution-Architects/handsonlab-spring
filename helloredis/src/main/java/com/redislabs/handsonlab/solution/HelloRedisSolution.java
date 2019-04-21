package com.redislabs.handsonlab.solution;

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
		// Set the hello message in Redis
		template.opsForValue().set("message", "Hello Redis!");

		// Retrieve the hello message from Redis
		String message = template.opsForValue().get("message");
		log.info(message);

		// Increment a counter to keep track of the number of runs
		long count = template.opsForValue().increment("counter");
		log.info("Hello Redis has been run {} times", count);
	}

}
