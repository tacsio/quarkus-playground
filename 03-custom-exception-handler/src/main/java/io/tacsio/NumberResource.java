package io.tacsio;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/check")
public class NumberResource {

    private final NumberService service;

    @Inject
    public NumberResource(NumberService service) {
        this.service = service;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@PathParam("id") Integer id) {
        String result = this.service.someBusinessLogic(id);
        return result;
    }

}