package com.redislabs.handsonlab.solution;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;

import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.XReadArgs.StreamOffset;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerSolution implements ApplicationRunner {

	@Autowired
	StatefulRedisConnection<String, String> connection;

	public static void main(String[] args) {
		SpringApplication.run(ConsumerSolution.class, args);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void run(ApplicationArguments args) throws Exception {
		XReadArgs xargs = XReadArgs.Builder.block(Duration.ofSeconds(1));
		RedisCommands<String, String> commands = connection.sync();
		StreamOffset<String> stream = StreamOffset.latest("my-stream");
		while (true) {
			List<StreamMessage<String, String>> messages = commands.xread(xargs, stream);
			messages.forEach(m -> log.info("Received message {}", m));
		}
	}

}
