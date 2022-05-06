import algorithms.Algorithm;
import algorithms.Beeman;
import algorithms.GearPredictorCorrector;
import algorithms.Verlet;
import models.Particle;
import systems.Oscillatory;
import systems.SystemType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EjercicioUno {
    private static final double TS = 0.001, TMAX = 10;
    private static final int FRAMES = 50;

    public static void createSimulation(Algorithm algorithm, String filename) throws IOException{
        FileWriter output = new FileWriter("resources/"+filename + ".csv");

        SystemType oscillatorySystem = new Oscillatory();
        List<Particle> particleStates = oscillatorySystem.simulate(algorithm,TS,TMAX);
        double t = 0, frame = 0;
        for(Particle p : particleStates){
            if(frame % FRAMES == 0){
                output.write(
                        t + "," + p.getPosition().getX() + "," + p.getPosition().getY() + "\n");
            }
            frame++;
            t += TS;
        }
        output.close();
    }

    public static void main(String[] args) throws IOException {
        Algorithm verlet = new Verlet();
        Algorithm beeman = new Beeman();
        Algorithm gear = new GearPredictorCorrector();

       createSimulation(verlet,"verlet");
       createSimulation(beeman,"beeman");
       createSimulation(gear,"gear");

    }
}
