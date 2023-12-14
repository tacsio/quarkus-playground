package io.tacsio.virtual;

import java.util.List;

import io.quarkus.logging.Log;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/")
@RunOnVirtualThread
public class TodoResource {

    @GET
    public List<Todo> getAll() {
        Log.info("all todos");
        return Todo.listAll();
    }

    @POST
    @Transactional
    public void create(@Valid Todo todo) {
        Log.info("creating todo");
        todo.persist();
    }
}
