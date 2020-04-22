package Commands;

import Monitoring.Assistant;
import Stored.Movie;

/**
 * Команда выводит элементы коллекции в строков представлении
 */
public class ShowCommand extends Command {

    public ShowCommand() {
        super("show", "вывести в стандартный поток вывода " +
                "все элементы коллекции в строковом представлении");
    }
    @Override
    public void commit(Assistant assistant, boolean visible) {
        for (Movie i : assistant.getMovies()) {
            System.out.print(i.toCSV());
        }
        if (assistant.getMovies().size() == 0) {
            System.out.println("Похоже в коллекции нет элементов...");
        }
    }
}
