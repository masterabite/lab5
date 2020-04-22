package Commands;

import Monitoring.Assistant;
import Stored.Movie;

/**
 * Команда добавляет объект в коллекцию из консоли/скрипта
 */
public class AddCommand extends Command {
    public AddCommand() {
        super("add", "добавить новый элемент в коллекцию");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        assistant.getMovies().add(assistant.getReader().readMovie(new Movie(), visible));
    }
}

