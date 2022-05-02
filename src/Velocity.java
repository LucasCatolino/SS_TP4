import javafx.geometry.Point2D;

public class Velocity {
    Point2D velocities;

    public Velocity(double velocityX, double velocityY) {
        velocities =  new Point2D(velocityX,velocityY);
    }

    public double getVelocityX() {
        return velocities.getX();
    }

    public void setVelocityX(double velocityX) {

    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getSpeed(){
        return Math.sqrt(velocityX * velocityX + velocityY * velocityY);
    }

    @Override
    public String toString() {
        return "(" + velocityX + ", " + velocityY + ")";
    }
}