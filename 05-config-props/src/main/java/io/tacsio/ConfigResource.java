package io.tacsio;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class ConfigResource {

	@ConfigProperty(name = "greeting.message")
	String message;

	@ConfigProperty(name = "greeting.name")
	Optional<String> name;

	@ConfigProperty(name = "greeting.suffix", defaultValue = "!")
	String suffix;

	// programatically
	String prefix = ConfigProvider.getConfig().getValue("greeting.prefix", String.class);
	// String prefix =
	// ConfigProvider.getConfig().getOptionalValue("greeting.prefix", String.class);

	@GET
	@Path("/attr")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloAttr() {
		return "[ATTRIBUTE] " + prefix + " " + message + " " + name.orElse("world") + suffix;
	}

	/**
	 * * Class configuration
	 */

	@Inject
	GreetingClassConfig configClass;

	@GET
	@Path("/class")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloClass() {
		return "[CLASS] " + configClass.getGreeting();
	}

	/**
	 * * Interface configuration
	 */

	@Inject
	GreetingInterfaceConfig configInterface;

	@GET
	@Path("/interface")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloInterface() {
		return "[INTERFACE] " + configInterface.getGreeting();
	}

	/**
	 * * Subprops configuration
	 */

	@Inject
	GreetingSubProps subProps;

	@GET
	@Path("/sub")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloSubrProps() {
		return "[SUB] " + subProps.getGreeting();
	}
	@GET
	@Path("/sub/jedi")
	@Produces(MediaType.TEXT_PLAIN)
	public String jediSubProps() {
		return subProps.getJedi();
	}
}
