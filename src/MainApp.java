import javax.swing.*;

public class MainApp {

	static DisplayPanel dp;
	static Birb[] birbs;
	static int t = 0;
	static double birbFOV = 4.0 * Math.PI / 3;

	public static void setupGUI(int frameX, int frameY) {
		JFrame frame = new JFrame("Birbs");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameX, frameY);

		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
		g.setSize(frameX, frameY);

		dp = new DisplayPanel(birbs, frameX, frameY, birbFOV);
		dp.setSize(frameX, frameY);
		g.add(dp);

		frame.setLocationRelativeTo(null);
		frame.add(g);
		frame.setContentPane(g);
		frame.setVisible(true);
	}

	public static void steerUniform() {
		(new Thread(new Runnable() {
			@Override
			public void run() {
				double bbSize = 75.0;
				for (Birb b : birbs) {
					double avg_theta = 0;
					int birb_in_bb = 0;
					double avg_phi = 0;
					for (Birb b_diff : birbs) {
						if (b.pos.distTo(b_diff.pos) < bbSize) {
							birb_in_bb++;
							avg_theta += b_diff.vel.theta;
							avg_phi += b_diff.vel.phi;
						}
					}
					avg_theta /= birb_in_bb;
					avg_phi /= birb_in_bb;
					b.pursuing = new SphereVector(b.vel.getMag(), avg_theta, avg_phi);
				}
			}
		})).start();
	}

	public static void checkCollision() {
		double bbSize = 50;
		for (Birb b : birbs) {
			// Make rays from vel-vision/2 to vel+vision/2
			(new Thread(new Runnable() {
				@Override
				public void run() {
					for (Birb b_diff : birbs) {
						for (int i = 0; i < 5; i++) {
							SphereVector s = b.vel.copy();
							s.rotate(0, Math.pow(-1, i) * i * birbFOV / 10);
							Ray r = new Ray(b.pos, s);
							double dist = r.untilCollision(b_diff.pos, bbSize);
							if (dist != 0) {
								if (i == 0) {
									i = 5;
								}
								s = b.vel.copy();
								s.rotate(0, Math.exp(-1 * dist) * Math.pow(-1, i + 1) * i * birbFOV / 20);
								b.avoidance = s;
								break;
							}

						}
					}

				}
			})).start();
		}
	}

	public static void flyToCentre() {
		double bbSize = 150.0;
		for (Birb b : birbs) {
			double avg_x = 0;
			int birb_in_bb = 0;
			double avg_y = 0;
			for (Birb b_diff : birbs) {
				if (b.pos.distTo(b_diff.pos) < bbSize) {
					birb_in_bb++;
					avg_x += b_diff.pos.x;
					avg_y += b_diff.pos.y;
				}
			}
			if (birb_in_bb != 1) {
				avg_x /= birb_in_bb;
				avg_y /= birb_in_bb;
				RectVector temp = new RectVector(avg_x, avg_y, 0);
				RectVector relativePos = b.pos.copy();
				relativePos.sub(temp);
				SphereVector s = relativePos.toSphere();
				b.force = new SphereVector(1, 0, -1 * (s.phi - b.vel.phi) / s.getMag());
			} else if (birb_in_bb == 1) {
				b.force = new SphereVector(0, 0, 0);
			}
		}
	}

	public static void main(String[] args) {
		int h = 900;
		int w = 1000;
		// Init birbs:
		int noBirbs = 5;
		birbs = new Birb[noBirbs];
		for (int i = 0; i < noBirbs; i++) {
			birbs[i] = new Birb(new RectVector(Math.random() * w, Math.random() * h, 0),
					new SphereVector(Math.random() * 1 + 1.5, Math.PI / 2, 2 * Math.random() * Math.PI), h, w);
		}

		setupGUI(w, h);
		(new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					dp.run();
					for (Birb b : birbs) {
						b.updatePos();
						steerUniform();
						checkCollision();
						flyToCentre();
					}
					try {
						int dt = 10;
						t += dt;
						Thread.sleep(dt);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		})).start();

	}

}