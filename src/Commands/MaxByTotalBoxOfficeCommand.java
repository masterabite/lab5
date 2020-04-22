package Commands;

import Monitoring.Assistant;
import Stored.Movie;

/**
 * Команда выводит первый объект из коллекции
 * значение поля totalBoxOffice которого является максимальным
 */
public class MaxByTotalBoxOfficeCommand extends Command {

    public MaxByTotalBoxOfficeCommand() {
        super("max_by_total_box_office", "вывести любой объект из коллекции, " +
                "значение поля totalBoxOffice которого является максимальным");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        for (Movie movie: assistant.getMovies()) {
            if (movie.getTotalBoxOffice() == assistant.getMaxTotalBoxOffice()) {
                System.out.print(movie.toCSV());
                break;
            }
        }
    }
}

