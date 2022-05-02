package algorithms;


import models.*;
import systems.SystemType;

public class Beeman implements Algorithm {

    public Particle calculateInitialEulerStep(Particle p, Vector force, double ts){
        double vX = p.getVelocity().getX() - ts * force.getX() / p.getMass();
        double vY = p.getVelocity().getY() - ts * force.getY() / p.getMass();

        double x = p.getPosition().getX() - ts * vX - ts * ts * force.getX()/ (2 * p.getMass());
        double y = p.getPosition().getY() - ts * vY - ts * ts * force.getY()/ (2 * p.getMass());

        return new Particle( p.getMass(),new Vector(x,y),new Vector(vX,vY),p.getCharge());
    }

    @Override
    public Particle next(Particle p, Particle prevP, SystemType system, double ts) {
        Vector f = system.getForce(p);
        double aX = f.getX()/p.getMass();
        double aY = f.getY()/p.getMass();
        if(prevP.getPosition() == null) {
            prevP = calculateInitialEulerStep(p, f, ts);
        }
        Vector prevF = system.getForce(prevP);
        double prevAX = prevF.getX()/p.getMass();
        double prevAY = prevF.getY()/p.getMass();

        double x = p.getPosition().getX() + p.getVelocity().getX() * ts + (2*aX/3 - prevAX/6) * ts * ts;
        double y = p.getPosition().getY() + p.getVelocity().getY() * ts + (2*aY/3 - prevAY/6) * ts * ts;

        double predictedVX = p.getVelocity().getX() + (3 * aX - prevAX) * ts / 2;
        double predictedVY = p.getVelocity().getY() + (3 * aY - prevAY) * ts / 2;

        Particle nextPredictedParticle = new Particle(p.getMass(), new Vector(x, y),
                new Vector(predictedVX, predictedVY), p.getCharge());

        Vector nextF = system.getForce(nextPredictedParticle);
        double nextAX = nextF.getX() / p.getMass();
        double nextAY = nextF.getY() / p.getMass();

        double exactVX = p.getVelocity().getX() + (nextAX/3 + 5*aX/6 - prevAX/6) * ts;
        double exactVY = p.getVelocity().getY() + (nextAY/3 + 5*aY/6 - prevAY/6) * ts;

        return new Particle(p.getMass(), new Vector(x, y), new Vector(exactVX, exactVY), p.getCharge());
    }
}
