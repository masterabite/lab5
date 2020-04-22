package IO;

import Enums.Color;
import Enums.MovieGenre;
import Exceptions.*;
import Monitoring.Assistant;
import Monitoring.Control;
import Stored.Coordinates;
import Stored.Location;
import Stored.Movie;
import Stored.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * класс осуществляющий ввод из файла/скрипта/консоли
 */
public class Reader  {
    /**
     * Сканнер для считывания из файла
     */
    private Scanner fileScan;

    /**
     * Сканнер для считывания из консоли/скрипта
     */
    private Scanner consoleScan;

    /**
     * асистент, которому принадлежит этот объект
     */
    private Assistant parent;

    /**
     * Массив который хранит элементы таблицы (файл из которого считывается коллекция)
     */
    private ArrayList<String> fields;

    /**
     * индекс текущего элемента из массива fields
     */
    private int currentIndex;


    /**
     * Конструктор со считываемым файлом и асистентом
     * @param file имя файла для считывания
     * @param assistant наш ассистент
     */
    public Reader(File file, Assistant assistant) {
        this.parent = assistant;
        this.consoleScan = new Scanner(System.in);
        try {
            this.fileScan = new Scanner(new FileReader(file));
        } catch (NullPointerException | FileNotFoundException e) {
            System.out.println("файл не указан или не найден");
        }
    }

    /**
     * функция проверяет если в массиве fields еще элементы
     * @return есть ли еще поля для считывания в строке таблицы
     */
    public boolean hasNextField() {
        return(this.currentIndex + 1 < this.fields.size());
    }

    /**
     * функция возвращает строку содержащую следующее поле массива fields
     * @return Возвращает следующий элемнт строки таблицы
     * @throws NoSuchElementException если элементы для считывания закончились но ввод ожидается
     */
    private String nextField() throws NoSuchElementException {
        if (hasNextField()) {
            return this.fields.get(++this.currentIndex);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * функция возвращает следующее число Integer
     * @return число соответствующего типа
     */
    private Integer nextInt() {
        return Integer.parseInt(nextField());
    }

    /**
     * функция возвращает следующее число Double
     * @return число соответствующего типа
     */
    private Double nextDouble() {
        return Double.parseDouble(nextField());
    }

    /**
     * функция возвращает следующее число Float
     * @return число соответствующего типа
     */
    private Float nextFloat() {
        return Float.parseFloat(nextField());
    }

    /**
     * функция возвращает следующее число Long
     * @return число соответствующего типа
     */
    private Long nextLong() {
        return Long.parseLong(nextField());
    }

    /**
     * функция возвращает следующие координаты
     * @return Координаты
     * @throws NumberFormatException Неправильный формат числа
     * @throws CoordinateXException максимальное значение координаты x- 961
     */
    public Coordinates nextCoordinates() throws NumberFormatException, CoordinateXException {
        return new Coordinates(nextFloat(), nextDouble());
    }

    /**
     * функция возвращает следующую локацию
     * @param location Локация которую мы заполняем
     * @return возвращает считанную локацию
     * @throws NumberFormatException Неправильный формат числа
     */
    public Location nextLocation(Location location) throws NumberFormatException {
        location.setX(nextLong());
        location.setY(nextInt());
        location.setName(nextField());
        return location;
    }

    /**
     * функция берет следующую персону
     * @return возвращает считанную персону
     * @throws NumberFormatException Неправильный формат числа
     * @throws ColorException Неправильный формат цвета
     */
    public Person nextPerson() throws NumberFormatException, ColorException {
        return new Person(
                nextField(),
                LocalDateTime.parse(nextField(), Control.getDateTimeFormatter()),
                nextColor(),
                nextLocation(new Location())
        );
    }

    /**
     * @return возвращает цвет в зависимости от считанной строки
     * @throws ColorException при неправильном вводе цвета
     */
    public Color nextColor() throws ColorException {
        switch (nextField()) {
            case "BLACK":
                return Color.BLACK;
            case "GREEN":
                return Color.GREEN;
            case "RED":
                return Color.RED;
            case "YELLOW":
                return Color.YELLOW;
            default:
                throw new ColorException();
        }
    }

    /**
     * @return возвращает жанр в зависимости от считанной строки
     * @throws MovieGenreException при неправильном вводе
     */
    public MovieGenre nextMovieGenre() throws MovieGenreException {
            switch (nextField()) {
                case "DRAMA":
                    return MovieGenre.DRAMA;
                case "TRAGEDY":
                    return MovieGenre.TRAGEDY;
                case "COMEDY":
                    return MovieGenre.COMEDY;
                default:
                    throw new MovieGenreException();
            }
    }

    public Scanner getScan() {
        return fileScan;
    }

    public Scanner getConsoleScan() {
        return consoleScan;
    }

    public void setConsoleScan(Scanner currentScanner) {
        this.consoleScan = currentScanner;
    }

    /**
     * процедура преобразует строку таблицы из файла и заполняет в массив строк fields
     */
    public void getNextLine() {
        fields = Control.parseLineFromCSV(this.fileScan.nextLine(), this.fileScan);
        currentIndex = -1;
    }

    /**
     * @param movie объект который нужно заполнить
     * @return возвращает считанный из файла объект
     * @throws CoordinateXException при неправильном вводе координат
     * @throws MovieGenreException при неправильном вводе жанра
     * @throws NumberFormatException при не корректном вводе чисел
     * @throws DateTimeParseException при вводе даты в неправильном формате
     * @throws ColorException при вводе цвета в неправильном формате
     * @throws NotUniqueIdException при вводе не уникального id
     * @throws LessThanZeroException при неправильном вводе координат
     */
    public Movie nextMovie(Movie movie) throws CoordinateXException, MovieGenreException,
            NumberFormatException, DateTimeParseException, ColorException,
            NotUniqueIdException, LessThanZeroException {
        getNextLine();
        Movie.setStaticID(Math.max(movie.getId(), Movie.getStaticID()));
        int id = nextInt();
        if (id <= 0) {
            throw new LessThanZeroException();
        }
        if (parent.findMovie(id) != null) {
            throw new NotUniqueIdException("Считан не уникальный id");
        } else {
            movie.setId(id);
        }
        movie.setName(nextField());
        movie.setCoordinates(nextCoordinates());
        movie.setCreationDate(ZonedDateTime.parse(nextField(), Control.getZoneDateTimeFormat()));
        movie.setOscarsCount(nextInt());
        movie.setTotalBoxOffice(nextInt());
        movie.setUsaBoxOffice(nextInt());
        movie.setGenre(nextMovieGenre());
        movie.setOperator(nextPerson());
        return movie;
    }

    /**
     * функция считывает из консоли/скрипта строку
     * @param title  Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает строку считанную из консоли/скрипта
     */
    public String readLine(String title, boolean visible) {
        while(true) {
            System.out.print(title);
            try {
                String res = consoleScan.nextLine();
                if (visible) {
                    System.out.println(res);
                }
                if (res.indexOf('\"') != -1) {
                    throw new ReadReservedCharException();
                }
                return res;
            } catch (ReadReservedCharException e) {
                System.out.println("Строка содержить недопустимые символы");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта число Integer
     * @param title Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает число считанное из консоли/скрипта
     */
    public Integer readInteger(String title, boolean visible) {
        while (true) {
            try {
                return Integer.parseInt(readLine(title, visible));
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Неверный формат ввода");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта число Long
     * @param title Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает число считанное из консоли/скрипта
     */
    public Long readLong(String title, boolean visible) {
        while (true) {
            try {
                return Long.parseLong(readLine(title, visible));
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Неверный формат ввода");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта число Double
     * @param title Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает число считанное из консоли/скрипта
     */
    public Double readDouble(String title, boolean visible) {
        while (true) {
            try {
                return Double.parseDouble(readLine(title, visible));
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Неверный формат ввода");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта число Float
     * @param title Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает число считанное из консоли/скрипта
     */
    public Float readFloat(String title, boolean visible) {
        while (true) {
            try {
                return Float.parseFloat(readLine(title, visible));
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Неверный формат ввода");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта Координаты
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает считанные координаты
     */
    public Coordinates readCoordinates(boolean visible) {
        Coordinates res = new Coordinates();
        while (true) {
            try {
                res.setX(readFloat("Введите x: ", visible));
                break;
            } catch (CoordinateXException e) {
                System.out.println("Максимальное значение поля: 961");
            }
        }
        res.setY(readDouble("Введите y: ", visible));
        return res;
    }

    /**
     * функция считывает из консоли/скрипта жанра Movie
     * @param title Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает считанный жанр фильма
     */
    public MovieGenre readMovieGenre(String title, boolean visible) {
        while (true) {
            switch (readLine(title, visible)) {
                case "DRAMA":
                    return MovieGenre.DRAMA;
                case "TRAGEDY":
                    return MovieGenre.TRAGEDY;
                case "COMEDY":
                    return MovieGenre.COMEDY;
                default:
                    System.out.println("Такого жанра нет! Выберите жанр из списка");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта объект класса LocalDateTime
     * @param title Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает считанную дату в соответствующем формате
     */
    public LocalDateTime readLocalDateTime(String title, boolean visible) {
        while (true) {
            try {
                return LocalDateTime.parse(readLine(title, visible), Control.getDateTimeFormatter());
            } catch (DateTimeParseException e) {
                System.out.println("Не удалось распознать строку");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта цвет
     * @param title Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает считанный цвет
     */
    public Color readColor(String title, boolean visible) {
        while (true) {
            switch (readLine(title, visible)) {
                case "BLACK":
                    return Color.BLACK;
                case "YELLOW":
                    return Color.YELLOW;
                case "RED":
                    return Color.RED;
                case "GREEN":
                    return Color.GREEN;
                default:
                    System.out.println("Такого цвета нет! Выберите цвет из списка");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта объект класса Location
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает считанную локацию
     */
    public Location readLocation(boolean visible) {
        Location location = new Location();
        location.setX(readLong("Введите x локации: ", visible));
        location.setY(readInteger("Введите y локации: ", visible));
        location.setName(readLine("Введите название локации: ", visible));
        return location;
    }

    /**
     * функция считывает из консоли/скрипта объект класса person
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return возвращает считанную персону
     */
    public Person readPerson(boolean visible) {
        Person person = new Person();
        person.setName(readLine("Введите имя оператора: ", visible));
        person.setBirthday(readLocalDateTime("Введите дату рождения оператора [дд/ММ/гггг чч:мм:сс]: ", visible));
        person.setHairColor(readColor("Введите цвет волос оператора [BLACK, YELLOW, RED, GREEN]: ", visible));
        person.setLocation(readLocation(visible));
        return person;
    }

    /**
     * функция считывает из консоли/скрипта только положительное число Integer
     * @param title Строка с которой начинается запрос на ввод поля по типу "Введите ...: "
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @return положительное считанное число
     */
    public Integer readPositiveInteger(String title, boolean visible) {
        while (true) {
            try {
                Integer integer = readInteger(title, visible);
                if (integer <= 0) {
                    throw new LessThanZeroException();
                }
                return integer;
            } catch (DateTimeParseException e) {
                System.out.println("Не удалось распознать строку");
            } catch (LessThanZeroException e) {
                System.out.println("Значение должно быть положительно");
            }
        }
    }

    /**
     * функция считывает из консоли/скрипта объект класса Movie
     * @param visible true если мы считываем из скрипта, чтобы выводить в консоль что мы считываем
     * @param movie заполняемый объект
     * @return возвращает считанный фильм
     */
    public Movie readMovie(Movie movie, boolean visible) {
        movie.setId(Movie.updateStaticID());
        movie.setName(readLine("Введите название фильма: ", visible));
        movie.setCoordinates(readCoordinates(visible));
        movie.setCreationDate(ZonedDateTime.now());
        try {
            movie.setOscarsCount(readPositiveInteger("Введите кол-во оскаров: ", visible));
            movie.setTotalBoxOffice(readPositiveInteger("Введите сборы в мире: ", visible));
            movie.setUsaBoxOffice(readPositiveInteger("Введите сборы в США: ", visible));
        } catch(LessThanZeroException e) {
            System.out.println("Не удалось считать положительное число");
        }
        movie.setGenre(readMovieGenre("Введите жанр [DRAMA, COMEDY, TRAGEDY]: ", visible));
        movie.setOperator(readPerson(visible));
        return movie;
    }

}
