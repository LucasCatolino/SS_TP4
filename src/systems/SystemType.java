package systems;

import algorithms.Algorithm;
import models.Particle;
import models.Vector;

import java.util.List;

public interface SystemType {
    Vector getForce(Particle particle);
    List<Vector> getRDerivatives(int order, Particle particle);
    List<Particle> simulate(Algorithm algorithm, double ts, double tmax);
}
