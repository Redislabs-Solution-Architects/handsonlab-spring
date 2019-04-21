package com.redislabs.handsonlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.redislabs.handsonlab.Person.Name;
import com.redislabs.modules.rejson.JReJSON;
import com.redislabs.modules.rejson.Path;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@SpringBootApplication
@Slf4j
public class RedisJSONApplication implements ApplicationRunner {

	@Autowired
	JReJSON json;
	@Autowired
	Jedis jedis;

	public static void main(String[] args) {
		SpringApplication.run(RedisJSONApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String key = "sal";
		Person sal = Person.builder().name(Name.builder().first("Salvatore").build()).location("Campobello di Licata")
				.github("https://github.com/antirez").age(40).build();
		json.set(key, sal);
		String path = ".name.first";
		Object firstName = json.get(key, new Path(path));
		log.info("First name ({}): {}", path, firstName);
		json.set(key, "Sanfilippo", new Path(".name.last"));
	}

}
