public class Birb {

	RectVector pos;
	SphereVector vel;
	int h;
	int w;
	SphereVector pursuing;
	SphereVector force;
	SphereVector avoidance;

	public Birb() {
		pos = new RectVector(Math.random() * 400, Math.random() * 400, 0);
		vel = new SphereVector(0.1, 0, 0);
	}

	public Birb(RectVector p, SphereVector v, int h, int w) {
		pos = p;
		vel = v;
		this.h = h;
		this.w = w;
		pursuing = v;
		force = new SphereVector(0, 0, 0);
		avoidance = v;
	}

	public void updatePos() {
		pos.add(vel.toRect());
		pursue();
		avoid();
		replyToForce();
		pos.x = pos.x % w;
		pos.y = pos.y % h;
		if (pos.x < 0) {
			pos.x = w + pos.x;
		}
		if (pos.y < 0) {
			pos.y = h + pos.y;
		}
	}

	public void pursue() {
		vel.rotate((pursuing.theta - vel.theta) / 10, (pursuing.phi - vel.phi) / 10);
	}

	public void avoid() {
		vel.rotate((avoidance.theta - vel.theta) / 10, (avoidance.phi - vel.phi) / 10);
	}

	public void replyToForce() {
		vel.rotate(force.theta / 10, force.phi / 10);
	}

	@Override
	public String toString() {
		return "Position: " + pos.toString() + "\tVel: " + vel.toString();
	}
}