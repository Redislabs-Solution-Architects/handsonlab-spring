package com.redislabs.handsonlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ConsumerApplication implements ApplicationRunner {

	@Autowired
	StatefulRedisConnection<String, String> connection;

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run(ApplicationArguments args) throws Exception {
		RedisCommands<String, String> commands = connection.sync();
		// Step 1: Listen for messages
		// TODO Use the commands object above to read and print messages
		
		// Step 2: Listen for messages using a consumer group
		// TODO Modify the previous to use {@link io.lettuce.core.api.RedisStreamCommands} xreadgroup
	}

}
