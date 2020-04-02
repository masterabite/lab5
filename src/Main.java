import Enums.MovieGenre;
import Stored.Coordinates;
import Stored.Movie;
import Stored.Person;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        HashSet<Movie> movies = new HashSet<>();

        Movie kek  = new Movie(
                    "El camino",
                    new Coordinates(),
                    new Integer(0),
                    1,
                    1,
                    MovieGenre.DRAMA,
                    new Person()
        );

        movies.add(kek);




        for(Movie i: movies) {
            i.printInformation();
        }

    }
}
