package Stored;

import Exceptions.CoordinateXException;
import Monitoring.Control;

public class Coordinates {
    private Float x; //Максимальное значение поля: 961, Поле не может быть null
    private Double y; //Поле не может быть null

    public Coordinates() {
        this.x = 0F;
        this.y = 0D;
    }

    public Coordinates(Float x, Double y) throws CoordinateXException {
        if (x > 961) {
            throw new CoordinateXException();
        }
        this.x = x;
        this.y = y;
    }

    public String toCSV() {
        return(
            Control.objToCSV(this.x) +
            Control.objToCSV(this.y)
        );
    }

    public void setX(Float x) throws CoordinateXException {
        if (x > 961) {
            throw new CoordinateXException();
        }
        this.x = x;
    }

    @Override
    public String toString() {
        return ("("+this.x + ";" + this.y+")");
    }

    public void setY(Double y) {
        this.y = y;
    }

}