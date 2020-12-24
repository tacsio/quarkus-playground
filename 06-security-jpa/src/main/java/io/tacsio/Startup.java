package io.tacsio;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import io.quarkus.runtime.StartupEvent;
import io.tacsio.models.User;

@Singleton
public class Startup {

    @Transactional
    public void loadUsers(@Observes StartupEvent event) {

        User.deleteAll();
        User.addUser("admin", "admin", "admin");
        User.addUser("user", "user", "user");
    }
}
