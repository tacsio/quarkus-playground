package io.tacsio.endpoints;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/public")
@Produces(MediaType.TEXT_PLAIN)
public class PublicResource {

	@GET
	@PermitAll
	public String publicPath() {
		return "public";
	}
}
