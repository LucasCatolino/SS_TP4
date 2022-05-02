package algorithms;
import models.*;
import systems.SystemType;
public interface Algorithm {
    Particle next(Particle p, Particle prevP, SystemType system, double ts);

}
