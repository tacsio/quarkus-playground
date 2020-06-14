package io.tacsio;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ExampleResponse hello() {
        return new ExampleResponse("Hello Quarkus");
    }
}

class ExampleResponse {

    private String msg;

    public ExampleResponse(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}