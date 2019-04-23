package com.redislabs.handsonlab;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.lettuce.core.Consumer;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.XReadArgs.StreamOffset;
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
		// TODO Modify the previous to use {@link
		// io.lettuce.core.api.RedisStreamCommands} xreadgroup

		// add a message to create the stream data structure
		commands.xadd("my-stream", Collections.singletonMap("key", "value"));
		// delete group as it may already exist
		commands.xgroupDestroy("my-stream", "my-group");
		// add a group pointing to the stream head
		commands.xgroupCreate(StreamOffset.from("my-stream", "$"), "my-group");
		Consumer<String> consumer = Consumer.from("my-group", consumerId());
		XReadArgs xargs = XReadArgs.Builder.block(Duration.ofSeconds(2));
		StreamOffset<String> stream = StreamOffset.lastConsumed("my-stream");
		while (true) {
			List<StreamMessage<String, String>> messages = commands.xreadgroup(consumer, xargs, stream);
			messages.forEach(m -> log.info("Received message {}", m));
		}
	}

	private String consumerId() {
		return UUID.randomUUID().toString();
	}

}
