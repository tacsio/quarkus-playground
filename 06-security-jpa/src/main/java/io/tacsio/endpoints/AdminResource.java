package io.tacsio.endpoints;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/admin")
@Produces(MediaType.TEXT_PLAIN)
public class AdminResource {

    @GET
    @RolesAllowed("admin")
    public String adminPath() {
        return "admin path";
    }
}
