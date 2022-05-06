package systems;

import algorithms.Algorithm;
import models.Particle;
import models.Vector;

import java.util.ArrayList;
import java.util.List;

public class Oscillatory implements SystemType{
    private static final double K = 10000, M = 70, GAMMA = 100;
    private static final Vector INIT_COORDS = new Vector(1,0), INIT_V = new Vector(-GAMMA/(2*M),0);

    @Override
    public Vector getForce(Particle particle) {
        return new Vector(-K * particle.getPosition().getX() - GAMMA*particle.getVelocity().getX(),0);
    }

    @Override
    public List<Vector> getRDerivatives(int order, Particle p) {
        List<Vector> toRet = new ArrayList<>(order+1);
        if(order >= 0){
            toRet.add(p.getPosition());
            if(order > 0){
                toRet.add(p.getVelocity());
            }
        }

        for(int i = 2; i <= order; i++){
            double x = (-K * toRet.get(i - 2).getX() - GAMMA * toRet.get(i-1).getX())/p.getMass();
            double y = (-K * toRet.get(i - 2).getY() - GAMMA * toRet.get(i-1).getY())/p.getMass();
            toRet.add(new Vector(x,y));
        }
        return toRet;
    }

    @Override
    public List<Particle> simulate(Algorithm algorithm, double ts, double tmax) {
        Particle particle = new Particle(M, INIT_COORDS,INIT_V,0);
        Particle prevP = new Particle(M,null,null,0);
        List<Particle> toRet = new ArrayList<>();
        toRet.add(particle);
        for(double t = 0; t < tmax; t+=ts){
            particle = algorithm.next(particle,prevP,this,ts);
            prevP = toRet.get(toRet.size()-1);
            toRet.add(new Particle(particle));
        }
        return toRet;
    }

    public static List<Particle> solveAnalytically(double ts, double tmax){
        List<Particle> toRet = new ArrayList<>();
        toRet.add(new Particle(M,INIT_COORDS,INIT_V,0));
        for(double t = 0; t < tmax; t+= ts){
            Vector pos = new Vector(
                    Math.exp(-(GAMMA/(2*M))*t) *
                            Math.cos(Math.sqrt(K/M- GAMMA * GAMMA / (4 * M * M)) * t), 0);
            toRet.add(new Particle(M,pos,null,0));
        }
        return toRet;
    }




}
