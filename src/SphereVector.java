public class SphereVector {

	double r, theta, phi;

	public SphereVector(double r, double theta, double phi) {
		this.r = r;
		this.theta = theta;
		this.phi = phi;
	}

	public double getMag() {
		return r;
	}

	public RectVector toRect() {
		return new RectVector(r*Math.sin(theta)*Math.cos(-1*phi), r*Math.sin(theta)*Math.sin(-1*phi), r*Math.cos(theta));
	}

	public void mult (double r) {
		this.r *= r;
	}

	public void rotate (double theta, double phi) {
		this.theta += theta;
		this.phi += phi;
	}

	public SphereVector copy () {
		return new SphereVector(r, theta, phi);
	}

	public SphereVector dirVect () {
		return new SphereVector(1, theta, phi);
	}

	@Override 
	public String toString() {
		return String.format("< %.2f, %.2f, %.2f >", r, theta, phi);
	}

}