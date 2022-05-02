package algorithms;
import models.*;
import systems.SystemType;
public class Verlet implements Algorithm {

    public Vector calculateInitialEulerStep(Particle p, Vector force, double ts){
        double vX = p.getVelocity().getX() - ts * force.getX() / p.getMass();
        double vY = p.getVelocity().getY() - ts * force.getY() / p.getMass();

        double x = p.getPosition().getX() - ts * vX - ts * ts * force.getX()/ (2 * p.getMass());
        double y = p.getPosition().getY() - ts * vY - ts * ts * force.getY()/ (2 * p.getMass());

        return new Vector(x,y);
    }

    @Override
    public Particle next(Particle p, Particle prevP, SystemType system, double ts) {

        Vector f = system.getForce(p);
        Vector prevPos = prevP.getPosition();
        if(prevPos == null){
            prevPos = calculateInitialEulerStep(p,f,ts);
        }
        double nextX = 2*p.getPosition().getX() - prevPos.getX() + (ts * ts / p.getMass()) * f.getX();
        double nextY = 2*p.getPosition().getY() - prevPos.getY() + (ts * ts / p.getMass()) * f.getY();

        Vector nextPos = new Vector(nextX, nextY);

        double nextVx = (nextPos.getX() - prevPos.getX())/(2*ts);
        double nextVy = (nextPos.getY() - prevPos.getY())/(2*ts);
        Vector nextV = new Vector(nextVx, nextVy);

        return new Particle(p.getMass(),nextPos,nextV,p.getCharge());
    }
}
