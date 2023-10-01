package io.tacsio.mqtt.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.MessageConverter;
import io.tacsio.mqtt.domain.Anime;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;

@ApplicationScoped
public class AnimeConverter implements MessageConverter {
    private final Logger log = LoggerFactory.getLogger(AnimeConverter.class);

    @Inject
    private ObjectMapper mapper;

    @Override
    public boolean canConvert(Message<?> message, Type type) {
        log.info(type.getTypeName());

        return type.equals(Anime.class);
    }

    @Override
    public Message<?> convert(Message<?> message, Type type) {
        byte[] payload = (byte[]) message.getPayload();
        try {
            Anime anime = mapper.readValue(payload, Anime.class);
            return message.withPayload(anime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
