package systems;

import java.util.ArrayList;
import java.util.List;

import algorithms.Algorithm;
import models.Particle;
import models.Vector;

public class Radiation implements SystemType {

	private static final double K = Math.pow(10, 10);  //k = 1e10 Nm^2/C^2
	private static final double Q = Math.pow(10, -19);  // Q = 1e19 C
	private static final double M = Math.pow(10, -27);

	private static final double D = Math.pow(10, -8);
	private static final int L = 16;
	private static final double MIN_DISTANCE = D*0.01; //distancia minima entre particulas

	private static final double MAX_V = 5 * Math.pow(10, 4);
	private static final double MIN_V = 5 * Math.pow(10, 3);
	private static final double MAX_P = ((double)L/2) + D; //TODO: esta mal
	private static final double MIN_P = ((double) L/2) - D; //TODO esta mal

	List<Particle> space = initSpace();
	//Particle particle = initParticle();

	private Particle initParticle() {
		Vector initialPosition = new Vector(0, random(MIN_P, MAX_P));
		Vector initialVelocity = new Vector(random(MIN_V, MAX_V),0);
		return new Particle(M, initialPosition, initialVelocity, Q);
	}

	private double random(double min, double max){
		return Math.random() * (max - min) + max;
	}
	
	private List<Particle> initSpace(){
		List<Particle> toReturn = new ArrayList<>();
		boolean isPositiveCharge = true;
		for (int x = 1; x < L+1; x++) { //el x empieza en uno para dejar lugar a la particula inicial
			for (int y = 0; y < L; y++) {
				Vector position = new Vector(y*D, x*D);
				Vector velocity = new Vector(0,0);
				toReturn.add(new Particle(M, position, velocity, isPositiveCharge? Q : -Q));
				isPositiveCharge = !isPositiveCharge;
			}
		}
		return toReturn;
	}
	
	private boolean endCondition(Particle particle){
		double x = particle.getPosition().getX();
		double y = particle.getPosition().getY();

		if( x<0 || y<0 || x>L*D || y>L*D )
			return true;

		for (Particle p : space) {
			if(p.getPosition().getDistanceTo(x,y) < MIN_DISTANCE )
				return true;
		}
		return false;
	}

	@Override
	public Vector getForce(Particle particle) {
		Vector force = new Vector(0,0);
		for (Particle p : space) {
			double distance = particle.getPosition().getDistanceTo(p.getPosition());
			Vector versor = (particle.getPosition().sub(p.getPosition())).getVersor();
			versor.multi((p.getCharge())/(Math.pow(distance,2)) );
			force.add(versor);
		}
		force.multi(K*particle.getCharge());
		return force;
	}

	public double getPotentialEnergy(Particle particle){
		double toReturn = 0;
		for (Particle p : space ) {
			toReturn += p.getCharge()/particle.getPosition().getDistanceTo(p.getPosition());
		}
		toReturn *= K*particle.getCharge();
		return toReturn;
	}

	@Override
	public List<Vector> getRDerivatives(int order, Particle particle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Particle> simulate(Algorithm algorithm, double ts, double tmax) {
		List<Particle> ToReturn = new ArrayList<>();
		Particle prevParticle = new Particle(M, null, null, Q);
		Particle currentParticle = initParticle();

		for (int t = 0; t < tmax && !endCondition(currentParticle); t += ts) {
			currentParticle = algorithm.next(currentParticle, prevParticle, this, ts);
			prevParticle = ToReturn.get(ToReturn.size()-1); //todo: esta mal
			ToReturn.add(new Particle(currentParticle));
		}
		return ToReturn;
	}

}
