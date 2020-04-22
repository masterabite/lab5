package Commands;

import Monitoring.Assistant;

/**
 * Команда выводит последние 7 команд (без их аргументов)
 */
public class HistoryCommand extends Command {

    public HistoryCommand() {
        super("history", " вывести последние 7 команд (без их аргументов)");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        System.out.print("Последние 7 комманд: ");
        for (int i = assistant.getHistory().size() - 1; i >= Math.max(assistant.getHistory().size() - 7, 0); --i) {
            System.out.print(assistant.getHistory().get(i) + ' ');
        }
        System.out.println();
    }
}
