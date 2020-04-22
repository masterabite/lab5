package Commands;

import Monitoring.Assistant;
import Stored.Movie;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Команда сохраняет коллекцию в файл
 */
public class SaveCommand extends Command {

    public SaveCommand() {
        super("save", "сохранить коллекцию в файл");
    }
    @Override
    public void commit(Assistant assistant, boolean visible) {
        try {
            assistant.setBw(new BufferedWriter(new FileWriter(assistant.getMyFile())));
            assistant.getBw().write(assistant.getHead());
            for (Movie i : assistant.getMovies()) {
                assistant.getBw().write(i.toCSV());
            }
            assistant.getBw().flush();
            assistant.getBw().close();
        } catch (IOException | NullPointerException e) {
            System.out.println("Не удалось сохранить коллекцию.");
        }
    }
}
