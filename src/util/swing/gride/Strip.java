package util.swing.gride;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

import util.swing.gride.BoxObject.PreferdBoxObject;

public abstract class Strip implements PreferdBoxObject, Iterable<Box> {

	protected boolean visible = true;
	protected Rectangle bounds;
	
	protected double ATot, ETot;
	protected Vector<Double> A = new Vector<>();
	protected Vector<Double> E = new Vector<>();
	protected Vector<Box> boxes = new Vector<>();
	
	protected Strip() {
	}
	
	public void setPane(JPanel pane) {
		for (Box b : boxes){
			b.setPane(pane);
		}
	}
	
	public static double[] newD(int s) {
		double[] r = new double[s];
		for (int i = 0; i < s; i++)
			r[i] = 1.0D;
		return r;
	}
	
	/*public void set(Vector<Double> A){
		this.A.clear();
		for (double d : A)
			this.A.add(d);
		reset();
	}
	
	public void set(double[] A) {
		this.A.clear();
		for (double d : A)
			this.A.add(d);
		reset();
	}

	public void setExp(Vector<Double> E) {
		this.ETot = Maths.sum(E);
		this.E.clear();
		for (double d : E)
			this.E.add(d);
	}

	public void setExp(double[] E) {
		this.ETot = Maths.sum(E);
		this.E.clear();
		for (double d : E)
			this.E.add(d);
	}*/
	
	public void append(Box box, double a, double e){
		if (box == null)
			this.append((BoxObject) null, a, e);
		this.A.add(a); ATot += a;
		this.E.add(e); ETot += e;
		this.boxes.add(box);
	}
	
	public void append(Box box, double a){
		this.append(box, a, a);
	}
	
	public void append(Box box) {
		this.append(box, 1, 1);	
	}
	
	public Box append(BoxObject comp, double a, double e){
		Box box = new Box(comp);
		this.append(box, a, e);
		return box;
	}
	
	public Box append(BoxObject comp, double a){
		return this.append(comp, a, a);
	}
	
	public Box append(BoxObject comp) {
		return this.append(comp, 1, 1);
	}
	
	protected abstract void fixbounds(double x, double y, double width, double height);
		
	protected abstract void rebounds(double dX, double dY, double dWidth, double dHeight);

	public void setBounds(Rectangle b, boolean fix) {
		if (b == null) return; // TODO: Maby somthing I will change
		if (fix || bounds == null){
			fixbounds(b.getX(), b.getY(), b.getWidth(), b.getHeight());
			bounds = b;
		}else if (!bounds.equals(b)){
			rebounds(b.x - bounds.x, b.y - bounds.y, b.getWidth() - bounds.width, b.getHeight() - bounds.height);
			bounds.setBounds(b);
		}
	}
	
	@Override
	public void setBounds(Rectangle b) {
		setBounds(b, false);
	}

	@Override
	public void setVisible(boolean visable) {
		for (Box box : boxes)
			box.setVisible(visable);
	}

	@Override
	public void makePreferdBox(Box box) {
		box.setInsets(0);
	}
	
	public Box getBox(int i) {
		if (i >= this.size())
			System.exit(1);
		return boxes.get(i);
	}
	
 	public int size() {
		return boxes.size();
	}

	public Iterator<Box> iterator() {
		return new Iterator<Box>() {
			
			int index = 0;
			
			public boolean hasNext() {
				return index < A.size();
			}

			public Box next() {
				return getBox(index++);
			}
		};
	} 
	
}
