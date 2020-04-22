package Commands;

import Monitoring.Assistant;
import Stored.Movie;

/**
 * Команда вывести количество элементов,
 * значение поля totalBoxOffice которых равно заданному
 */
public class CountByTotalBoxOfficeCommand extends Command {

    public CountByTotalBoxOfficeCommand() {
        super("count_by_total_box_office", "вывести количество элементов, " +
                "значение поля totalBoxOffice которых равно заданному");
    }

    @Override
    public void commit(Assistant assistant, boolean visible) {
        Integer totalBoxOffice = assistant.getReader().readInteger("Введите totalBoxOffice: ", visible);
        int cnt = 0;
        for (Movie movie: assistant.getMovies()) {
            if (movie.getTotalBoxOffice() == totalBoxOffice) {
                ++cnt;
            }
        }
        System.out.println("Кол-во соответствующих элементов: " + cnt);
    }
}

