package io.tacsio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "greeting")
public class GreetingSubProps {

	private String message;
	private Optional<String> name;
	private String suffix = "!";
	private String prefix;

	// attribute name must be the same of property
	public StarWarsConfig starwars;

	public static class StarWarsConfig {
		public Integer jediPower;
		public List<String> jedi;
	}

	public String getGreeting() {
		return prefix + " " + message + " " + name.orElse("world") + suffix;
	}

	public String getJedi() {
		return starwars.jedi.stream().collect(Collectors.joining(",")) + " power: " + starwars.jediPower;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Optional<String> getName() {
		return name;
	}

	public void setName(Optional<String> name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
