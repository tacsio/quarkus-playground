package io.tacsio;

import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "greeting")
public interface GreetingInterfaceConfig {

	@ConfigProperty(name = "message")
	String message();

	Optional<String> name();

	@ConfigProperty(defaultValue = "!")
	String suffix();

	String prefix();

	default String getGreeting() {
		return prefix() + " " + message() + " " + name().orElse("world") + suffix();
	}

}
