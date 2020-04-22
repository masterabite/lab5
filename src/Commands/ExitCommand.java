package Commands;

import Monitoring.Assistant;

/**
 * Команда для прекращения работы приложения
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit", "завершить программу(без сохранения файла)");
    }
    @Override
    public void commit(Assistant assistant, boolean visible) {
        assistant.stop();
    }
}
