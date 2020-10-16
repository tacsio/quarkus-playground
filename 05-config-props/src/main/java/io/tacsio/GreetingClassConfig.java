package io.tacsio;

import java.util.Optional;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "greeting")
public class GreetingClassConfig {

	public String message;
	public Optional<String> name;
	public String suffix = "!";
	public String prefix;

	public String getGreeting() {
		return prefix + " " + message + " " + name.orElse("world") + suffix;
	}

}