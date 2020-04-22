package Commands;

import Monitoring.Assistant;

/**
 * Команда очищает коллекцию
 */
public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear", "очистить коллекцию");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        assistant.getMovies().clear();
    }
}

