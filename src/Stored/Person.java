package Stored;

import Enums.Color;
import Monitoring.Control;

import java.time.LocalDateTime;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле может быть null

    public Person() {
        this.name = "UNKNOW";
        this.hairColor = Color.BLACK;
        this.location = new Location();
    }

    public Person(String name, LocalDateTime birthday, Color hairColor, Location location) {
        this.name = name;
        this.birthday = birthday;
        this.hairColor = hairColor;
        this.location = location;
        this.hairColor = Color.BLACK;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * функция инициализирует поле name с учетом того что оно не может быть пустой строкой
     * @param name обрабатываемое имя
     */
    public void setName(String name) {
        if (!name.equals("")) {
            this.name = name;
        } else {
            this.name = "UNKNOW";
        }
    }

    /**
     * @return объект в строков представлении в формате CSV
     */
    public String toCSV() {
        return (
                Control.objToCSV(this.name) +
                Control.localDateTimeToCSV(this.birthday) +
                Control.objToCSV(this.hairColor) +
                this.location.toCSV());
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}