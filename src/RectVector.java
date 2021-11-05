public class RectVector {

	double x, y, z;

	public RectVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getMag() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public SphereVector toSphere() {
		return new SphereVector(getMag(), 0, Math.atan(y / x));
	}

	public void add(RectVector r) {
		x += r.x;
		y += r.y;
		z += r.z;
	}

	public void sub(RectVector r) {
		x -= r.x;
		y -= r.y;
		z -= r.z;
	}

	public double distTo(RectVector r) {
		return Math.sqrt((x - r.x) * (x - r.x) + (y - r.y) * (y - r.y) + (z - r.z) * (z - r.z));
	}

	public RectVector copy() {
		return new RectVector(x, y, z);
	}

	@Override
	public String toString() {
		return String.format("< %.2f, %.2f, %.2f >", x, y, z);
	}

}