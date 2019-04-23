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
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ProducerApplication implements ApplicationRunner {

	@Autowired
	StatefulRedisConnection<String, String> connection;

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		RedisCommands<String, String> commands = connection.sync();
		int index = 1;
		while (true) {
			Map<String, String> message = new HashMap<>();
			message.put("field1", "value" + index);
			message.put("field2", String.valueOf(index));
			// TODO send message to stream and assign its ID to the messageId variable
			String messageId = "TBD";
			log.info("Sent message {} with ID {}", message, messageId);
			Thread.sleep(3000);
			index++;
		}
	}

}
