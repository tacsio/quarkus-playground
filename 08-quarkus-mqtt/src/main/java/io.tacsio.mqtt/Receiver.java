package io.tacsio.mqtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tacsio.mqtt.domain.Anime;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ApplicationScoped
public class Receiver {

    private final Logger log = LoggerFactory.getLogger(Receiver.class);
    @Inject
    private ObjectMapper mapper;

    @Incoming("animes")
    public void receiveAnime(byte[] payload) {
        try {
            Anime anime = mapper.readValue(payload, Anime.class);
            log.info("Received {}", anime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
