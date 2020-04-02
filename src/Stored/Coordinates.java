package Stored;

public class Coordinates {
    private Float x; //Максимальное значение поля: 961, Поле не может быть null
    private Double y; //Поле не может быть null

    public Coordinates() {
        this.x = new Float(0);
        this.y = new Double(0);
    }

    public Coordinates(Float x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }
}