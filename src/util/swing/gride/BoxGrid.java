package util.swing.gride;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;

import util.math.Maths;

public class BoxGrid implements BoxObject, Iterable<Box> {

	private Rectangle Bounds;

	private Map<Rectangle, Box> mBoxes = new HashMap<Rectangle, Box>();

	// columns-X and rows-Y
	/**
	 * @param columns
	 *            X
	 * @param rows
	 *            Y
	 */
	private double xETot, yETot;
	private Vector<Double> eX = new Vector<>();
	private Vector<Double> eY = new Vector<>();

	private double xTot, yTot;
	private Vector<Double> X = new Vector<>();
	private Vector<Double> Y = new Vector<>();

	public BoxGrid() {
		Bounds = new Rectangle();
	}

	public BoxGrid(Rectangle bounds) {
		if (bounds != null)
			Bounds = bounds;
		else if (Bounds == null)
			Bounds = new Rectangle();
	}

	/**
	 * 
	 * @param bounds
	 *            Key
	 * @param X
	 * @param Y
	 */
	public BoxGrid(Rectangle bounds, double[] X, double[] Y) {
		this(bounds);
		set(X, Y);
		setExp(X, Y);
	}

	public BoxGrid(Rectangle bounds, int xSize, int ySize) {
		this(bounds, newD(xSize), newD(ySize));
	}

	public BoxGrid(Rectangle bounds, double[] x, int ySize) {
		this(bounds, x, newD(ySize));
	}

	public BoxGrid(Rectangle bounds, int xSize, double[] y) {
		this(bounds, newD(xSize), y);
	}
	
	public void setPane(JPanel pane) {
		for (Box b : mBoxes.values()){
			b.setPane(pane);
		}
	}

	public static double[] newD(int s) {
		double[] r = new double[s];
		for (int i = 0; i < s; i++)
			r[i] = 1.0D;
		return r;
	}

	public void set(Vector<Double> X, Vector<Double> Y) {
		this.X.clear();
		for (double d : X)
			this.X.add(d);
		this.Y.clear();
		for (double d : Y)
			this.Y.add(d);
		reset();
	}

	public void set(double[] X, double[] Y) {
		this.X.clear();
		for (double d : X)
			this.X.add(d);
		this.Y.clear();
		for (double d : Y)
			this.Y.add(d);
		reset();
	}

	public void setExpV(Vector<Double> X, Vector<Double> Y) {
		setExpXV(X);
		setExpYV(Y);
	}

	public void setExpXV(Vector<Double> x) {
		this.eX.clear();

		for (int i = 0; i < X.size(); i++)
			if (x == null || i >= x.size())
				eX.add(1.0);
			else
				eX.add(x.get(i));

		this.xETot = Maths.sum(eX);
	}

	public void setExpYV(Vector<Double> y) {
		this.eY.clear();

		for (int i = 0; i < Y.size(); i++)
			if (y == null || i >= y.size())
				eY.add(1.0);
			else
				eY.add(y.get(i));

		this.yETot = Maths.sum(eY);
	}

	public void setExp(double[] X, double[] Y) {
		setExpX(X);
		setExpY(Y);
	}

	public void setExpX(double[] x) {
		this.eX.clear();

		for (int i = 0; i < X.size(); i++)
			if (x == null || i >= x.length)
				eX.add(1.0);
			else
				eX.add(x[i]);

		this.xETot = Maths.sum(eX);
	}

	public void setExpY(double[] y) {
		this.eY.clear();

		for (int i = 0; i < Y.size(); i++)
			if (y == null || i >= y.length)
				eY.add(1.0);
			else
				eY.add(y[i]);

		this.yETot = Maths.sum(eY);
	}

	public void appendX(double w, double e) {
		X.add(w);
		xTot += w;
		eX.add(w);
		xETot += w;
		reset();
	}

	public void appendY(double w, double e) {
		Y.add(w);
		yTot += w;
		eY.add(e);
		yETot += e;
		reset();
	}

	public void appendX(double w) {
		appendX(w, w);
	}

	public void appendY(double w) {
		appendY(w, w);
	}

	private void reset() {
		double width1 = Bounds.getWidth() / Maths.sum(X);
		double height1 = Bounds.getHeight() / Maths.sum(Y);

		for (Rectangle rec : mBoxes.keySet()) {
			double xp = 0;
			for (int i = 0; i < rec.x; i++)
				xp += X.get(i);

			double yp = 0;
			for (int i = 0; i < rec.y; i++)
				yp += Y.get(i);

			double w = 0;
			for (int i = 0; i < rec.width; i++)
				w += X.get(rec.x + i);

			double h = 0;
			for (int i = 0; i < rec.height; i++)
				h += Y.get(rec.y + i);

			mBoxes.get(rec).setBounds(xp * width1 + Bounds.x, yp * height1 + Bounds.y, w * width1,
					h * height1);
		}
	}

	public void setBounds(Rectangle bounds) {
		if (bounds == null)
			return;
		if (Bounds == null)
			Bounds = new Rectangle();
		if (!Bounds.equals(bounds)) {
			try {
				resize(bounds.x - Bounds.x, bounds.y - Bounds.y, bounds.getWidth() - Bounds.width,
						bounds.getHeight() - Bounds.height);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Bounds.setBounds(bounds);
			
		}
	}

	private void resize(double dX, double dY, double dWidth, double dHeight) {
		if (xETot != 0)
			dWidth /= xETot;
		if (yETot != 0)
			dHeight /= yETot;

		for (Rectangle rec : mBoxes.keySet()) {
			double xp = 0;
			for (int i = 0; i < rec.x && i < eX.size(); i++)
				xp += eX.get(i);

			double yp = 0;
			for (int i = 0; i < rec.y && i < eY.size(); i++)
				yp += eY.get(i);

			double w = 0;
			for (int i = 0; i < rec.width && i < eX.size(); i++)
				w += eX.get(rec.x + i);

			double h = 0;
			for (int i = 0; i < rec.height && i < eY.size(); i++)
				h += eY.get(rec.y + i);

			mBoxes.get(rec).increaseBounds(xp * dWidth + dX, yp * dHeight + dY, w * dWidth, h * dHeight);
		}
		
	}

	/*
	 * public void setAllInsets(int inset) { setAllInsets(new Insets(inset,
	 * inset, inset, inset)); }
	 * 
	 * public void setAllInsets(int top, int left, int bottom, int right) {
	 * setAllInsets(new Insets(top, left, bottom, right)); }
	 * 
	 * public void setAllInsets(Insets insets) { for (Box box : mBoxes) {
	 * box.setInsets(insets); } }
	 * 
	 * public void setBox(int index, Box box) { Box b = boxes[index];
	 * box.setBounds(b.x, b.y, b.width, b.height); boxes[index] = box; }
	 * 
	 * public void setBox(int x, int y, Box box) { Box b = boxes[x + y *
	 * columns]; box.setBounds(b.x, b.y, b.width, b.height); boxes[x + y *
	 * columns] = box; }
	 */

	public Box getBox(Rectangle rec) {
		Box b = mBoxes.get(rec);
		if (b == null) {
			mBoxes.put(rec, b = new Box());
			double xp = 0;
			for (int i = 0; i < rec.x; i++)
				xp += X.get(i);

			double yp = 0;
			for (int i = 0; i < rec.y; i++)

				yp += Y.get(i);
			double w = 0;
			for (int i = 0; i < rec.width; i++)
				w += X.get(rec.x + i);

			double h = 0;
			for (int i = 0; i < rec.height; i++)
				h += Y.get(rec.y + i);

			double width1 = Bounds.getWidth() / Maths.sum(X);
			double height1 = Bounds.getHeight() / Maths.sum(Y);

			b.setBounds(xp * width1 + Bounds.x, yp * height1 + Bounds.y, w * width1, h * height1);
		}
		return b;
	}

	public Box getBox(int index) {
		return getBox(new Rectangle(index % X.size(), index / X.size(), 1, 1));
	}

	public Box getBox(int x, int y) {
		return getBox(new Rectangle(x, y, 1, 1));
	}

	public void setComponent(Component component, int i) {
		getBox(i).setComponent(component);
	}

	public void setComponent(Component component, int x, int y) {
		getBox(x, y).setComponent(component);
	}

	public int size() {
		return mBoxes.size();
	}

	public void setVisible(boolean b) {
		for (Box box : mBoxes.values()) {
			box.setVisible(b);
		}
	}

	public Iterator<Box> iterator() {
		return new Iterator<Box>() {

			int index = 0;

			public boolean hasNext() {
				return index < X.size() * Y.size();
			}

			public Box next() {
				return getBox(index++);
			}
		};
	}

}