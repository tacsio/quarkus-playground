package io.tacsio.mqtt.domain;

import io.tacsio.mqtt.infra.Sender;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;
import java.util.Random;

@Path("/animes")
public class AnimeController {

    private Random random = new Random();
    private List<Anime> animes;
    private Sender sender;

    public AnimeController(AnimeParser animeParser, Sender sender) {
        this.animes = animeParser.parse();
        this.sender = sender;
    }

    @GET
    public Anime send() {
        Anime anime = animes.get(random.nextInt(animes.size() - 1));
        sender.sendAnime(anime);

        return anime;
    }
}
