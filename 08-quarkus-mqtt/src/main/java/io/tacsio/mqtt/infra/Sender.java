package io.tacsio.mqtt.infra;

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
    private Emitter<Anime> animeEmitter;

    public void sendAnime(Anime anime) {
        log.info("Trying to send msg.");

        animeEmitter.send(anime)
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        log.error("fail to send.");
                    } else {
                        log.info("send works!");
                    }
                });
    }
}
