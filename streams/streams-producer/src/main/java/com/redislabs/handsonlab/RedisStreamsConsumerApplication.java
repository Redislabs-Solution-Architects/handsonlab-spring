package com.redislabs.handsonlab;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.lettuce.core.Consumer;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.XReadArgs.StreamOffset;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class RedisStreamsConsumerApplication implements ApplicationRunner {

	@Autowired
	StatefulRedisConnection<String, String> connection;

	public static void main(String[] args) {
		SpringApplication.run(RedisStreamsConsumerApplication.class, args);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run(ApplicationArguments args) throws Exception {
		RedisCommands<String, String> commands = connection.sync();
		commands.xadd("my-stream", Collections.singletonMap("key", "value"));
		commands.xgroupDestroy("my-stream", "my-group");
		commands.xgroupCreate(StreamOffset.from("my-stream", "$"), "my-group");
		while (true) {
			Map<String, String> fields = new HashMap<>();
			fields.put("field1", "value1");
			commands.xreadgroup(Consumer.from("my-group", "consumer" + UUID.randomUUID().toString()),
					XReadArgs.Builder.block(Duration.ofHours(1)), StreamOffset.lastConsumed(("my-stream")))
					.forEach(msg -> log.info("Received message {}", msg));
		}
	}

}
