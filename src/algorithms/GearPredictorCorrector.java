package algorithms;

import models.*;
import systems.SystemType;

import java.util.ArrayList;
import java.util.List;

public class GearPredictorCorrector implements Algorithm{

    private static final double[] COEFS = {3.0/16, 251.0/360, 1, 11.0/18, 1.0/6, 1.0/60};
    private static final int ORDER = 5;


    @Override
    public Particle next(Particle p, Particle prevP, SystemType system, double ts) {

        Vector[] predictedParticles = predict(system.getRDerivatives(ORDER, p), ts);
        Particle predictedP = new Particle(p.getMass(), predictedParticles[0], predictedParticles[1], 0);
        Vector relativeR2 = evaluate(predictedParticles, predictedP, system, ts);
        double[][] correctedCoefs = correct(relativeR2, p.getMass(), predictedParticles, ts);

        double x = correctedCoefs[0][0];
        double y = correctedCoefs[1][0];
        double vX = correctedCoefs[0][1];
        double vY = correctedCoefs[1][1];

        return  new Particle(p.getMass(), new Vector(x, y), new Vector(vX, vY), 0);
    }

    // Step 1
    private Vector[] predict(List<Vector> positionDerivatives, double ts){
        if (positionDerivatives.size() != ORDER + 1)
            throw new IllegalArgumentException("Se deben incluir hasta la derivada " + ORDER + " de la posición");

        Vector[] predictedParticles = new Vector[ORDER + 1];
        for (int j = 0; j <= ORDER; j++){
            double x = 0, y = 0;
            for (int i = j; i <= ORDER; i++) {
                int term = i - j;
                x += positionDerivatives.get(i).getX() * Math.pow(ts, term) / factorial(term);
                y += positionDerivatives.get(i).getY() * Math.pow(ts, term) / factorial(term);
            }
            predictedParticles[j] = new Vector(x,y);
        }
        return predictedParticles;
    }


    // Step 2
    private Vector evaluate(Vector[] predictedParticles, Particle predictedP, SystemType system, double ts){
        Vector f = system.getForce(predictedP);
        double aX = f.getX() / predictedP.getMass();
        double aY = f.getY() / predictedP.getMass();

        double aDeltaX =  (aX - predictedParticles[2].getX()) * (ts * ts) / 2;
        double aDeltaY= (aY - predictedParticles[2].getY()) * (ts * ts) / 2;

        return new Vector(aDeltaX , aDeltaY);
    }

    // Step 3
    private double[][] correct(Vector deltaR2Force, double mass, Vector[] prediction, double ts){
        double deltaR2X = deltaR2Force.getX() / mass;
        double deltaR2Y = deltaR2Force.getY() / mass;

        double[][] corrected = new double[2][ORDER+1];

        for (int i = 0; i<=ORDER; i++){
            corrected[0][i] = prediction[i].getX() + COEFS[i] * deltaR2X*factorial(i)/Math.pow(ts, i);
            corrected[1][i] = prediction[i].getY() + COEFS[i] * deltaR2Y*factorial(i)/Math.pow(ts, i);
        }

        return corrected;
    }


    public long factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }
}
