package Stored;

public class Location {
    private Long x; //Поле не может быть null
    private int y;
    private String name; //Строка не может быть пустой, Поле может быть null

    public Location() {
        this.x = 0L;
        this.y = 0;
        this.name = "UNKNOW";
    }

    public Location(Long x, int y) {
        this.x = x;
        this.y = y;
        this.name = "UNKNOW";
    }

    public Location(Long x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getY() {
        return y;
    }

    public Long getX() {
        return x;
    }
}