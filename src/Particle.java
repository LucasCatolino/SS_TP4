import javafx.geometry.Point2D;

import java.util.Objects;

public class Particle{
    private final double mass;
    private Point2D position;
    private Velocity velocity;
    private final double electricCharge;

    public Particle(Particle particle){
        mass = particle.mass;
        position = new Point2D(particle.position.getX(), particle.position.getY());
        velocity = new Velocity(particle.velocity.getVelocityX(), particle.velocity.getVelocityY());
        electricCharge = particle.electricCharge;
    }
    public Particle(double mass, Point2D position, Velocity velocity, double electricCharge) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.electricCharge = electricCharge;
    }

    public double getMass() {
        return mass;
    }

    public Point2D getPosition() {
        return position;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double velocityX, double velocityY){
        velocity.setVelocityX(velocityX);
        velocity.setVelocityY(velocityY);
    }

    public Force getVersor(Particle particle){
        double deltaX = particle.position.getX() - position.getX();
        double deltaY = particle.position.getY() - position.getY();
        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return new Force(deltaX/magnitude, deltaY/magnitude);
    }

    public double getElectricCharge() {
        return electricCharge;
    }
}
