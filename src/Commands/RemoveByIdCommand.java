package Commands;

import Monitoring.Assistant;

/**
 * Команда удаляет элемент коллекции по его id
 */
public class RemoveByIdCommand extends Command {
    public RemoveByIdCommand() {
        super("remove_by_id", "удалить элемент из коллекции по его id");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        int id = assistant.getReader().readInteger("", visible);
        if (assistant.findMovie(id) != null) {
            assistant.getMovies().remove(assistant.findMovie(id));
        } else {
            System.out.println("Такого элемента нет.\n");
        }
    }
}
