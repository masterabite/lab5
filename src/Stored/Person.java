package Stored;

import Enums.Color;

import java.time.LocalDateTime;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле может быть null

    public Person() {
        this.name = new String("unknown");
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, LocalDateTime birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public Person(String name, LocalDateTime birthday, Color hairColor, Location location) {
        this.name = name;
        this.birthday = birthday;
        this.hairColor = hairColor;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }
}