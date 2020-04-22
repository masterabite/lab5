package Commands;

import Monitoring.Assistant;
import Stored.Movie;

import java.util.ArrayList;

/**
 * Команда выводит элементы, значение поля name которых содержит заданную подстроку
 */
public class FilterContainsNameCommand extends Command {

    public FilterContainsNameCommand() {
        super("filter_contains_name", "вывести элементы, значение поля name которых содержит заданную подстроку");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        ArrayList<String> contains = new ArrayList<>();
        String name = assistant.getReader().readLine("Введите подстроку: ", visible);
        for (Movie movie: assistant.getMovies()) {
            if (movie.getName().contains(name)) {
                contains.add(movie.toCSV());
            }
        }
        if (contains.size() == 0) {
            System.out.println("Таких элементов нет.");
        } else {
            for (String i: contains) {
                System.out.print(i);
            }
        }
    }
}

