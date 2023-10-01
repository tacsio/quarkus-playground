package io.tacsio.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tacsio.mqtt.domain.Anime;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class Sender {

    private final Logger log = LoggerFactory.getLogger(Sender.class);

    @Inject
    @Channel("topic-anime")
    private Emitter<byte[]> animeEmitter;

    @Inject
    private ObjectMapper mapper;

    public void sendAnime(Anime anime) {
        log.info("Trying to send msg.");

        try {
            byte[] bytes = mapper.writeValueAsBytes(anime);
            animeEmitter.send(bytes)
                    .whenComplete((success, failure) -> {
                        if (failure != null) {
                            log.error("fail to send.");
                        } else {
                            log.info("send works!");
                        }
                    });

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
