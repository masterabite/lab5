package Commands;

import Monitoring.Assistant;
import Stored.Movie;

import java.util.ArrayList;

/**
 * Команда удаляет все элементы коллекции значение которых меньше введенного элемента
 */
public class RemoveLowerCommand extends Command {
    public RemoveLowerCommand() {
        super("remove_lower", "удалить из коллекции все элементы, " +
                "меньшие, чем заданный");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        ArrayList<Movie> excessMovie = new ArrayList<>();
        Movie movie = assistant.getReader().readMovie(new Movie(), visible);
        for (Movie m: assistant.getMovies()) {
            if (assistant.getMovieComparator().compare(m, movie) < 0) {
                excessMovie.add(m);
            }
        }
        for (Movie m: excessMovie) {
            assistant.getMovies().remove(m);
        }
    }
}

