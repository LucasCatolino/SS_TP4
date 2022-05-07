import algorithms.Algorithm;
import algorithms.Beeman;
import models.Particle;
import models.Vector;
import systems.Radiation;
import systems.SystemType;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EjercicioDos {

    private static final double T_STEP = Math.pow(10, -14);
    private static final double T_MAX = 10;
    private static final Algorithm ALGORITHM = new Beeman();

    public static void main(String[] args) throws IOException, InterruptedException {
        simulation("pState");
    }

    public static void simulation(String fileName) throws IOException {
        FileWriter output = new FileWriter("resources/" +fileName + ".txt");

        SystemType system = new Radiation();
        List<Particle> particleStates = system.simulate(ALGORITHM, T_STEP, T_MAX);
        double t = 0;
        for (Particle p : particleStates) {
            output.write(t + "\t" + p.getPosition() + "\t" + p.getVelocity().getModule() + "\t"+ p.getPotentialEnergy()+"\n");
            t += T_STEP;
        }
        output.close();
    }

}
