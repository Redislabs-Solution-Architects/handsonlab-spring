package com.redislabs.handsonlab;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class Person {

	private Name name;
	@Singular
	private List<String> locations;
	private String github;
	private int age;

	@Data
	@Builder
	public static class Name {
		private String first;
		private String last;
	}

}
