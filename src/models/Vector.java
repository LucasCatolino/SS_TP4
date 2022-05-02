package models;

import java.util.Objects;
public class Vector {
    private double x;
    private double y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistanceTo(Vector v){
        return Math.sqrt(Math.pow((x - v.x),2) + Math.pow((y - v.y),2));
    }

    public Vector getVersor(){
        return new Vector(getX()/getModule(), getY()/getModule());
    }

    public double getModule(){
        return Math.sqrt(Math.pow((x),2) + Math.pow((y),2));
    }


    public void sum(Vector v){
        this.x += v.x;
        this.y += v.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
