package Commands;

import Monitoring.Assistant;
import Stored.Movie;


/**
 * Команда добавляет объект в коллекцию из консоли/скрипта если он меньше минимального
 */
public class AddIfMinCommand extends Command {
    public AddIfMinCommand() {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, " +
                "чем у наименьшего элемента этой коллекции");
    }


    /**
     * функция проверяет является ли объект минимальным
     * @param assistant наш ассистент
     * @param movie введенный объект класса Movie
     * @return Является ли элемент меньше минимального
     */
    public boolean checkIfMin(Assistant assistant, Movie movie) {
        for (Movie i: assistant.getMovies()) {
            if (assistant.getMovieComparator().compare(movie, i) >= 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        Movie movie = assistant.getReader().readMovie(new Movie(), visible);
        if (checkIfMin(assistant, movie)) {
            assistant.getMovies().add(movie);
            System.out.println("Элемент добавлен");
        } else {
            System.out.println("Элемент не добавлен");
        }
    }
}

