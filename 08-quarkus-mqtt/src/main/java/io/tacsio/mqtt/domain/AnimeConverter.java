package io.tacsio.mqtt.domain;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnimeConverter {
    public List<Anime> convert() {
        try {
            URL resource = getClass().getClassLoader().getResource("animes.txt");
            Path path = Paths.get(resource.toURI());
            List<String> names = Files.readAllLines(path);

            return names.stream()
                    .map(name -> new Anime(name, new Random().nextInt(11)))
                    .collect(Collectors.toList());

        } catch (URISyntaxException | IOException e) {
            return new ArrayList<>();
        }
    }
}
