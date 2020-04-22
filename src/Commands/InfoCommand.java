package Commands;

import Monitoring.Assistant;

/**
 * Команда выводит информацию о коллекции
 */
public class InfoCommand extends Command {

    public InfoCommand() {
        super("info", "вывести в стандартный поток вывода " +
                "информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        System.out.print("Дата инициализации коллекции: ");
        System.out.println(assistant.getMoviesInitializationDate());
        System.out.print("Тип коллекции: ");
        System.out.println(assistant.getMovies().getClass());
        System.out.print("Количество элементов в коллекци: ");
        System.out.println(assistant.getMovies().size());
    }

}
