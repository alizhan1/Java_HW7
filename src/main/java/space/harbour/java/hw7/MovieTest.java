package space.harbour.java.hw7;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    private List<Movie> movies;

    @BeforeEach
    void setUp() {
        Movie bladerunner = new Movie();
        Movie batman = new Movie();
        Movie joker = new Movie();

        bladerunner.readFromJsonFile("BladeRunner.json");
        batman.readFromJsonFile("Batman.json");
        joker.readFromJsonFile("Joker.json");

        movies = new ArrayList<>();
        movies.add(joker);
        movies.add(batman);
        movies.add(bladerunner);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sortByYearTest() {
        Collections.sort(movies, (Movie x, Movie y) -> x.year.compareTo(y.year));
        Assertions.assertTrue(movies.get(0).year < movies.get(1).year);
        Assertions.assertTrue(movies.get(1).year < movies.get(2).year);
    }

    @Test
    void sortByRuntime() {
        Collections.sort(movies, (Movie x, Movie y) -> x.runtime.compareTo(y.runtime));
        Assertions.assertTrue(movies.get(0).runtime < movies.get(1).runtime);
        Assertions.assertTrue(movies.get(1).runtime < movies.get(2).runtime);
    }

    @Test
    void sortByRating() {
        Collections.sort(movies, (Movie x, Movie y) -> (Float.parseFloat(x.ratings.get(0).value.split("/")[0]) > Float.parseFloat(y.ratings.get(0).value.split("/")[0])) ? 1
        : (Float.parseFloat(x.ratings.get(0).value.split("/")[0]) < Float.parseFloat(y.ratings.get(0).value.split("/")[0])) ? -1
        : 0);
        Assertions.assertTrue(Float.parseFloat(movies.get(0).ratings.get(0).value.split("/")[0]) < Float.parseFloat(movies.get(1).ratings.get(0).value.split("/")[0]));
        Assertions.assertTrue(Float.parseFloat(movies.get(1).ratings.get(0).value.split("/")[0]) < Float.parseFloat(movies.get(2).ratings.get(0).value.split("/")[0]));
    }

    @Test
    void filterByGenre() {
        Set<String> director_names = new HashSet<>();
        director_names.add(movies.get(0).director.name);
        director_names.add(movies.get(1).director.name);
        director_names.add(movies.get(2).director.name);

        List<Movie> filtered_movies = movies.stream()
                .filter(m -> m.director.name == director_names.toArray()[0])
                .collect(Collectors.toList());
        Assertions.assertTrue(filtered_movies.size() == 1);
    }

    @Test
    void filterByActors() {
        Set<String> actors_names = new HashSet<>();
        for (int i = 0; i < movies.get(0).actors.size(); i++) {
            actors_names.add(movies.get(0).actors.get(i).name);
        }

        List<Movie.Actor> filtered_movies = movies.stream()
                .map(m -> m.actors)
                .collect(Collectors.toList()).get(0).stream()
                .filter(actor -> actor.name == actors_names.toArray()[0])
                .collect(Collectors.toList());

        Assertions.assertTrue(filtered_movies.size() == 1);
    }
}