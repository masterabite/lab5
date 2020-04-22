package Monitoring;

import Commands.*;
import Exceptions.*;
import IO.Reader;
import Stored.Movie;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Класс асистента который реализует взаимодействие с пользователем и работу комманд
 */
public class Assistant {
    /**
     * Наша коллекция
     */
    private HashSet<Movie> movies;

    /**
     * Дата инициализации коллекции
     */
    private Date moviesInitializationDate;

    /**
     * файл для считывания и записи
     */
    private File myFile;

    private Reader reader;

    /**
     * компаратор для сравнения элементов коллекции по принципу сравнения их hashCode)
     */
    private Comparator<Movie> movieComparator = Comparator.comparingInt(Movie::hashCode);

    /**
     * объек для записи коллекции в файл
     */
    private BufferedWriter bw;

    /**
     * Хранить список выполненных комманд
     */
    private ArrayList<String> history;

    /**
     * Максимальные сборы среди всех элементов в коллекции
     */
    private Long maxTotalBoxOffice;

    /**
     * Хранит доступные комманды
     */
    private ArrayList<Command> commands;

    /**
     * переменная для завершения работы программы
     * fale- если работа завершена, true- иначе
     */
    private boolean execution;

    public Assistant(String[] args) {
        maxTotalBoxOffice = -1L;
        commands = new ArrayList<>();
        history = new ArrayList<>();
        movies = new HashSet<>();
        moviesInitializationDate = new Date();
        myFile = new File(args[0]);
        reader = new Reader(myFile, this);
        readFromFile();
        if (reader.getScan() != null) {
            reader.getScan().close();
        }
    }

    /**
     * функция осуществляет считывания содержимого файл и записи в коллекцию
     */
    public void readFromFile() {
        int wrongStringCnt = 0;
        try {
            reader.getScan().nextLine();
        } catch (NullPointerException e) {
            System.out.println("Не удалось считать файл");
        }
        if (reader.getScan() != null) {
            while (reader.getScan().hasNext()) {
                try {
                    Movie newMovie = new Movie();
                    movies.add(reader.nextMovie(newMovie));
                    maxTotalBoxOffice = Math.max(maxTotalBoxOffice, newMovie.getTotalBoxOffice());
                } catch (NumberFormatException | CoordinateXException |
                        MovieGenreException | NoSuchElementException | DateTimeParseException |
                        ColorException | NotUniqueIdException | LessThanZeroException e) {
                    ++wrongStringCnt;
                }
            }
            if (wrongStringCnt > 0) {
                System.out.println("Кол-во поврежденных элементов: " + wrongStringCnt);
            }
        }
    }


    public BufferedWriter getBw() {
        return bw;
    }

    public void setBw(BufferedWriter bw) {
        this.bw = bw;
    }

    public Reader getReader() {
        return reader;
    }

    public Date getMoviesInitializationDate() {
        return moviesInitializationDate;
    }

    /**
     * @return возвращает верхнюю строку для записи в файл
     */
    public String getHead() {
        return "Id,MovieName,CoordinateX,CoordinateY," +
                "CreationDate,OscarCount,TotalBoxOffice," +
                "USABoxOffice,Genre,OperatorName,OperatorBirthDay,OperatorHairColor," +
                "OperatorLocationX,OperatorLocationY,OperatorLocationName\n";
    }

    public File getMyFile() {
        return myFile;
    }

    public Long getMaxTotalBoxOffice() {
        return maxTotalBoxOffice;
    }

    public Comparator<Movie> getMovieComparator() {
        return movieComparator;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public HashSet<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    /**
     * процедура ищет команду среди существующих и выполняет, при нахождении
     * @param string строка, содержащая имя команды
     * @param visible определяет нужно ли выводить саму команду (если ввод осуществляется из скрипта)
     */
    public void commitCommand(String string, boolean visible) {
        Command command = findCommand(string);
        if (null == command) {
            System.out.println("К сожалению, я такой команды не знаю :(\nПопробуйте еще...");
        } else {
            this.history.add(string);
            System.out.println("Выполняю :)");
            command.commit(this, visible);
        }
    }

    /**
     * процедура добавляет все доступные комманды, и начинает интерактивное общение с пользователем
     */
    public void start() {
        sortMovies();
        this.execution = true;
        this.commands.add(new HelpCommand());
        this.commands.add(new InfoCommand());
        this.commands.add(new ShowCommand());
        this.commands.add(new AddCommand());
        this.commands.add(new UpdateByIdCommand());
        this.commands.add(new RemoveByIdCommand());
        this.commands.add(new ClearCommand());
        this.commands.add(new SaveCommand());
        this.commands.add(new ExecuteScriptCommand());
        this.commands.add(new ExitCommand());
        this.commands.add(new AddIfMinCommand());
        this.commands.add(new RemoveLowerCommand());
        this.commands.add(new HistoryCommand());
        this.commands.add(new MaxByTotalBoxOfficeCommand());
        this.commands.add(new CountByTotalBoxOfficeCommand());
        this.commands.add(new FilterContainsNameCommand());

        System.out.println("help- получить список комманд");

        while (this.execution) {
            try {
                commitCommand(this.reader.readLine("Введите комманду: ", false), false);
            } catch (NoSuchElementException e) {
                System.out.println("Скрипт поврежден");
                reader.getConsoleScan().close();
                reader.setConsoleScan(new Scanner(System.in));
            }
        }
    }

    /**
     * @return возвращает элемент коллекции, id котрого равен данному и
     * null если такого элемента в коллекции нет
     * @param id id искомого объекта
     */
    public Movie findMovie(int id) {
        for(Movie movie: movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    /**
     * @return возвращает команду, имя которой эквивалентно данному и
     * null если такой команды нет
     * @param name имя искомой команды
     */
    public Command findCommand(String name) {
        for (Command currentCommand: commands) {
            if (currentCommand.getName().equals(name)) {
                return currentCommand;
            }
        }
        return null;
    }

    /**
     * функция возвращает элемент коллекции, id котрого равен данному и
     * null если такого элемента в коллекции нет
     */
    public void sortMovies() {
        List<Movie> list = new ArrayList<>(movies);
        list.sort(movieComparator);
        movies = new HashSet<>(list);

    }

    /**
     * функция прекращает работу асистента
     */
    public void stop() {
        this.execution = false;
        System.out.println("Завершение...");
        System.exit(0);
    }
}
