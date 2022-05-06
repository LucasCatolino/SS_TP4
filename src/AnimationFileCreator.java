import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class AnimationFileCreator {

    private static final double D = Math.pow(10, -8);
    private static final int N = 16*16+3;
    private static final int L = 16;
    private static final double R = D/5;

    public static void main(String[] args) throws IOException, InterruptedException {
        FilesCreator("pState");
    }

    private static void FilesCreator(String File) throws IOException {
        FileWriter output = new FileWriter("resources/simulation.xyz");
        InputStream FileStream = EjercicioDos.class.getClassLoader().getResourceAsStream(File + ".txt");
        assert FileStream != null;
        Scanner FileScanner = new Scanner(FileStream);

        String staticParticle = staticParticle(); //siempre es igual


        while (FileScanner.hasNext()) {
            output.write(N + "\n"); //cantidad total de particulas
            output.write("t=" + FileScanner.next() + "\n"); //tiempo
            output.write(FileScanner.next() + "\t" + FileScanner.next() + "\t" + R + "\t1\t0\n");
            FileScanner.next(); //tiro la velocidad
            FileScanner.next(); //tiro la energia potencia;
            output.write(staticParticle);
        }
        output.close();
        FileScanner.close();
        FileStream.close();
    }

    private static String staticParticle() {
        StringBuilder toReturn = new StringBuilder();
        boolean isPositiveCharge = true;
        for (int x = 1; x < L + 1; x++) {
            for (int y = 0; y < L; y++) {
                toReturn.append(x * D).append("\t").append(y * D).append("\t").append(R).append("\t");
                if (isPositiveCharge) {
                    toReturn.append("1\t0\n");
                } else {
                    toReturn.append("0\t1\n");
                }
                isPositiveCharge = !isPositiveCharge;
            }
            isPositiveCharge = !isPositiveCharge;
        }
        toReturn.append("0\t0\t").append(R).append("\t0\t0\n");
        toReturn.append("0\t").append((L-1)*D).append("\t").append(R).append("\t0\t0\n");
        return String.valueOf(toReturn);
    }

}