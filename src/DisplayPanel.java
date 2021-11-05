import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DisplayPanel extends JPanel implements Runnable {

	Birb[] birbs;
	int h, w;
	double birbFOV;

	public DisplayPanel(Birb[] b, int h, int w, double birbFOV) {
		birbs = b;
		this.h = h;
		this.w = w;
		this.birbFOV = birbFOV;
	}

	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());

		g.setColor(new Color(0, 0, 0));
		g.drawRect(0, 0, w + 85, h - 85);
		// Draw birbs

		int a = 10;
		for (Birb b : birbs) {
			g.setColor(new Color(0, 0, 0));
			int[] x = new int[3];
			int[] y = new int[3];
			x[0] = (int) (b.pos.x);
			y[0] = (int) (b.pos.y);
			SphereVector s = b.vel.dirVect();
			s.mult(a);
			s.rotate(0, 5 * Math.PI / 6);
			RectVector r = (s).toRect();
			r.add(b.pos);
			x[1] = (int) (r.x);
			y[1] = (int) (r.y);

			s = b.vel.dirVect();
			s.mult(a);
			s.rotate(0, -5 * Math.PI / 6);
			r = (s).toRect();
			r.add(b.pos);
			x[2] = (int) (r.x);
			y[2] = (int) (r.y);
			g.fillPolygon(x, y, 3);

			// Draw the vel vector
			g.setColor(new Color(255, 0, 0));
			s = b.vel.copy();
			s.mult(2 * a);
			r = b.pos.copy();
			r.add(s.toRect());
			g.drawLine((int) (b.pos.x), (int) (b.pos.y), (int) (r.x), (int) (r.y));

			g.setColor(new Color(255, 255, 0, 30));
			g.fillOval((int) (b.pos.x - 75), (int) (b.pos.y - 75), 150, 150);

			g.setColor(new Color(255, 0, 0, 30));
			g.fillArc((int) (b.pos.x - 25), (int) (b.pos.y - 25), 50, 50,
					(int) (Math.toDegrees(b.vel.phi + birbFOV / 2)), 360 - (int) (Math.toDegrees(birbFOV)));

			g.setColor(new Color(0, 0, 255, 30));
			g.fillArc((int) (b.pos.x - 50), (int) (b.pos.y - 50), 100, 100,
					(int) (Math.toDegrees(b.vel.phi - birbFOV / 2)), (int) (Math.toDegrees(birbFOV)));
		}
	}

	public void run() {
		repaint();
	}

}
