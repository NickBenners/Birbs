public class Ray {

	SphereVector dir;
	RectVector origin;

	public Ray(RectVector o, SphereVector s) {
		origin = o;
		dir = s;
	}

	public double untilCollision(RectVector v, double r) {
		double threshold = 50;
		for (double i = 0; i < threshold; i += 1) {
			dir.r = i;
			RectVector temp = origin.copy();
			temp.add(dir.toRect());
			if (temp.distTo(v) < r) {
				return i;
			}
		}
		return 0;
	}

}