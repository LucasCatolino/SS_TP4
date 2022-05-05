import java.io.FileWriter;
import java.io.IOException;

public class EjercicioUno {
    private static final double TS = 0.001, TMAX = 10;
    private static final int FRAME_RATE = 50;

    public void createFiles() throws IOException{
        FileWriter verletStates = new FileWriter("resources/verlet.csv");
        FileWriter beemanStates = new FileWriter("resources/beeman.csv");
        FileWriter gearStates = new FileWriter("resources/gear.csv");

        

    }
}
