package models;

public class Particle{
    private final double mass;
    private Vector position;
    private Vector velocity;
    private final double charge;
    private double PotentialEnergy;

    public double getPotentialEnergy() {
        return PotentialEnergy;
    }

    public void setPotentialEnergy(double potentialEnergy) {
        PotentialEnergy = potentialEnergy;
    }

    public Particle(Particle particle){
        mass = particle.mass;
        position = new Vector(particle.position.getX(), particle.position.getY());
        velocity = new Vector(particle.velocity.getX(), particle.velocity.getY());
        charge = particle.charge;
        PotentialEnergy = particle.PotentialEnergy;
    }
    public Particle(double mass, Vector position, Vector velocity, double electricCharge) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        this.charge = electricCharge;
    }

    public double getMass() {
        return mass;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public double getKineticEnergy(){
        return (mass/2)*velocity.getModule()*velocity.getModule();
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double velocityX, double velocityY){
        velocity.setX(velocityX);
        velocity.setY(velocityY);
    }
    public double getCharge() {
        return charge;
    }
}
