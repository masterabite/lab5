package Stored;

import Enums.MovieGenre;

import java.time.ZonedDateTime;

public class Movie {
    private static int staticID = 1; //статичное поле определяющее уникальный id
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer oscarsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private int totalBoxOffice; //Значение поля должно быть больше 0
    private int usaBoxOffice; //Значение поля должно быть больше 0
    private MovieGenre genre; //Поле не может быть null
    private Person operator; //Поле не может быть null

    static private int getID() { // статичный метод для получения уникального ID
        return staticID++;
    }

    public Movie(
            String name,
            Coordinates coordinates,
            Integer oscarsCount,
            int totalBoxOffice,
            int usaBoxOffice,
            MovieGenre genre,
            Person operator
    ) {
        this.id = Movie.getID();
        this.name = name;
        this.coordinates = coordinates;
        this. creationDate = ZonedDateTime.now();
        this.oscarsCount = oscarsCount;
        this.totalBoxOffice = totalBoxOffice;
        this.usaBoxOffice = usaBoxOffice;
        this.genre = genre;
        this.operator = operator;
    }

    public String printInformation() {
        System.out.print(this.id);
        System.out.println(": '" + this.name + "' operator: " + this.operator.getName());
        return("ok");
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static int getStaticID() {
        return staticID++;
    }

    public int getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public Integer getOscarsCount() {
        return oscarsCount;
    }

    public int getUsaBoxOffice() {
        return usaBoxOffice;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public Person getOperator() {
        return operator;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    public void setOscarsCount(Integer oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public void setTotalBoxOffice(int totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
    }

    public void setUsaBoxOffice(int usaBoxOffice) {
        this.usaBoxOffice = usaBoxOffice;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }
}
