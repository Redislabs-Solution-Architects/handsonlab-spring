package com.redislabs.handsonlab;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

@SpringBootApplication
public class RedisStreamsProducerApplication implements ApplicationRunner {

	@Autowired
	StatefulRedisConnection<String, String> connection;

	public static void main(String[] args) {
		SpringApplication.run(RedisStreamsProducerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		RedisCommands<String, String> commands = connection.sync();
		while (true) {
			Map<String, String> fields = new HashMap<>();
			fields.put("field1", "value1");
			commands.xadd("my-stream", fields);
			Thread.sleep(5000);
		}
	}

}
