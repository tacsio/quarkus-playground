package io.tacsio.mqtt.infra;

import io.tacsio.mqtt.domain.Anime;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class Receiver {

    private final Logger log = LoggerFactory.getLogger(Receiver.class);

    @Incoming("animes")
    public void receiveAnime(Anime anime) {
        log.info("Received {}", anime);
    }
}
