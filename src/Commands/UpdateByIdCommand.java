package Commands;

import Monitoring.Assistant;
import Stored.Movie;

/**
 * Команда удаляет элемент коллекции по его id
 */
public class UpdateByIdCommand extends Command {
    public UpdateByIdCommand() {
        super("update", "обновить значение " +
                "элемента коллекции, id которого равен заданному");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        int id = assistant.getReader().readInteger("", visible);
        Movie curMovie = assistant.findMovie(id);
        if (curMovie != null) {
            assistant.getReader().readMovie(curMovie, visible);
            curMovie.setId(id);
        } else {
            System.out.println("Такого элемента нет.\n");
        }
    }
}
