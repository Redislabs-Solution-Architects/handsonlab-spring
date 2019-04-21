package com.redislabs.handsonlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class HelloRedisApplication implements ApplicationRunner {

	@Autowired
	StringRedisTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(HelloRedisApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Set the hello message in Redis

		// TODO Retrieve the hello message from Redis
		String message = "Not yet implemented";
		log.info(message);

		// TODO Increment a counter to keep track of the number of runs
		long count = 0;
		log.info("Hello Redis has been run {} times", count);
	}

}
