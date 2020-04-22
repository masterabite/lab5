package Commands;

import Monitoring.Assistant;

/**
 * Команды выводит информацию о доступных ногах
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "получить список доступных комманд");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        for (Command currentCommand: assistant.getCommands()) {
            System.out.println(currentCommand.getHelp());
        }
    }
}
