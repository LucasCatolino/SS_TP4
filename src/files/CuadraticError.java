package files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CuadraticError {
	
	private final static String TS1= "0.1_";
	private final static String TS2= "0.01_";
	private final static String TS3= "0.001_";
	private final static String TS4= "1.0E-4_";
	private final static String TS5= "1.0E-5_";
	private final static String TS6= "1.0E-6_";
	private final static double[] TIMES= {0.1, 0.01, 0.001, 0.0001, 0.00001, 0.000001};
	
	private ArrayList<Double> cuadraticBeeman;
	private ArrayList<Double> cuadraticGear;
	private ArrayList<Double> cuadraticVerlet;
		
	public CuadraticError() {
		cuadraticBeeman= new ArrayList<>();
		cuadraticGear= new ArrayList<>();
		cuadraticVerlet= new ArrayList<>();
		
		calculateError(TS1);
		calculateError(TS2);
		calculateError(TS3);
		calculateError(TS4);
		calculateError(TS5);
		calculateError(TS6);
		
        createFile("beeman.csv", cuadraticBeeman);
        createFile("gear.csv", cuadraticGear);
        createFile("verlet.csv", cuadraticVerlet);
	}
	
    private void calculateError(String ts) {
    	
    	System.out.println(ts.replace("_", ""));
    	
    	String analyticFileString= ts + "analytic.csv";
    	String beemanFileString= ts + "beeman.csv";
    	String gearFileString= ts + "gear.csv";
    	String verletFileString= ts + "verlet.csv";    	
    	
    	//open analytic file
        InputStream analyticStream = CuadraticError.class.getClassLoader().getResourceAsStream(analyticFileString);
        assert analyticStream != null;
        Scanner analyticScanner = new Scanner(analyticStream);
        analyticScanner.useDelimiter("[,\n]");

		//open beeman file
        InputStream beemanStream = CuadraticError.class.getClassLoader().getResourceAsStream(beemanFileString);
        assert beemanStream != null;
        Scanner beemanScanner = new Scanner(beemanStream);
        beemanScanner.useDelimiter("[,\n]");
        
		//open gear file
        InputStream gearStream = CuadraticError.class.getClassLoader().getResourceAsStream(gearFileString);
        assert gearStream != null;
        Scanner gearScanner = new Scanner(gearStream);
        gearScanner.useDelimiter("[,\n]");
        
		//open verlet file
        InputStream verletStream = CuadraticError.class.getClassLoader().getResourceAsStream(verletFileString);
        assert verletStream != null;
        Scanner verletScanner = new Scanner(verletStream);
        verletScanner.useDelimiter("[,\n]");
        
        double beemanCuadratic= 0;
        double gearCuadratic= 0;
        double verletCuadratic= 0;
        int total= 0;
        
        while (analyticScanner.hasNext() && beemanScanner.hasNext() && gearScanner.hasNext() && verletScanner.hasNext()) {
        	
        	analyticScanner.next(); //skip time
        	double analyticX= Double.parseDouble(analyticScanner.next());
        	
        	beemanScanner.next(); //skip time
        	double beemanX= Double.parseDouble(beemanScanner.next());
        	
        	gearScanner.next(); //skip time
        	double gearX= Double.parseDouble(gearScanner.next());

        	verletScanner.next(); //skip time
        	double verletX= Double.parseDouble(verletScanner.next());
        	
        	beemanCuadratic+= Math.pow((analyticX - beemanX), 2);
        	gearCuadratic+= Math.pow((analyticX - gearX), 2);
        	verletCuadratic+= Math.pow((analyticX - verletX), 2);
        	
        	total ++;
        }
        
        analyticScanner.close();
        beemanScanner.close();
        gearScanner.close();
        verletScanner.close();
        
        cuadraticBeeman.add(beemanCuadratic / total);
        cuadraticGear.add(gearCuadratic / total);
        cuadraticVerlet.add(verletCuadratic / total);
	}

	private void createFile(String fileName, ArrayList<Double> list) {
		try {
            File file = new File("resources/Error_" + fileName);
            FileWriter myWriter = new FileWriter("resources/Error_" + fileName);
            
            int end= list.size();
            for (int i = 0; i < end; i++) {
            	try {
            		myWriter.write("" + TIMES[i] + "," + list.get(i) + "\n");
            	} catch (Exception e) {
            		System.err.println("IOException: " + fileName + ", " + i);
            	}
			}
            myWriter.close();
        } catch (IOException e) {
            System.out.println("IOException ocurred: " + fileName);
            e.printStackTrace();
        }
		
	}

	public static void main(String[] args) throws IOException {
    	CuadraticError fileCuadraticError= new CuadraticError();
    }

}
