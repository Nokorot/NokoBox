package util.swing.gride;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JPanel;

import util.swing.gride.BoxObject.PreferdBoxObject;

public class Box implements BoxObject {

	private boolean visible = true; 
	
	public double x, y, width, height;
	public Insets insets = new Insets(3, 3, 3, 3);

	protected Component component;
	protected BoxObject object;

	public Box(){
	}
	
	public Box(BoxObject comp){
		if (comp instanceof PreferdBoxObject)
			((PreferdBoxObject) comp).makePreferdBox(this);
		this.setBoxObject(comp);
	}
	
	public Box(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void increaseBounds(double x, double y, double width, double height) {
		setBounds(this.x + x, this.y + y, this.width + width, this.height + height);
	}

	protected void update(){
		if (component != null){
			component.setBounds(getRectangle());
		}

		if (object != null){
			object.setBounds(getRectangle());
		}
	}
	
	public void setBounds(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		update();
	}
	
	public void setPane(JPanel pane) {
		if (component != null){
			pane.add(component);
		}

		if (object != null){
			object.setPane(pane);
		}
	}

	public void setTopInset(int inset) {
		if (this.insets == null)
			this.insets = new Insets(inset, 0, 0, 0);
		else
			this.insets.top = inset;
		update();
	}

	public void setLeftInset(int inset) {
		if (this.insets == null)
			this.insets = new Insets(0, inset, 0, 0);
		else
			this.insets.left = inset;
		update();
	}

	public void setBottomInset(int inset) {
		if (this.insets == null)
			this.insets = new Insets(0, 0, inset, 0);
		else
			this.insets.bottom = inset;
		update();
	}

	public void setRightInset(int inset) {
		if (this.insets == null)
			this.insets = new Insets(0, 0, 0, inset);
		else
			this.insets.right = inset;
		update();
	}

	public void setSideInsets(int inset) {
		if (this.insets == null)
			this.insets = new Insets(0, inset, 0, inset);
		else{
			this.insets.left = inset;
			this.insets.right = inset;
		}
		update();
	}

	public void setTopBottomInsets(int inset) {
		if (this.insets == null)
			this.insets = new Insets( inset, 0, inset, 0);
		else{
			this.insets.top = inset;
			this.insets.bottom = inset;
		}
		update();
	}

	public void setInsets(int inset) {
		setInsets(inset, inset, inset, inset);
		update();
	}

	public void setInsets(int top, int left, int bottom, int right) {
		if (insets == null)
			this.insets = new Insets(top, left, bottom, right);
		else
			this.insets.set(top, left, bottom, right);
		update();
		
	}

	public void setInsets(Insets insets) {
		setInsets(insets.top, insets.left, insets.bottom, insets.right);
	}

	public void setComponent(Component component) {
		if(this.component != null)
			this.component.setVisible(false);
		this.component = component;
		if(this.component != null){
			component.setVisible(visible);
			component.setBounds(getRectangle());
			component.repaint();
		}
	}

	public void setBoxObject(BoxObject object) {
		if (this.component != null){
			this.component.setVisible(false);
			this.component = null;
		}
			
		this.object = object;
//		update();
	}

	public BoxGrid getInsideGrid() {
		setInsets(0);
		return (BoxGrid) (object = new BoxGrid(getRectangle()));
	}

	public BoxGrid getInsideGrid(double[] X, double[] Y) {
		BoxGrid grid = getInsideGrid();
		grid.set(X, Y);
		return grid;
	}

	public BoxGrid getInsideGrid(int x, double[] Y) {
		return getInsideGrid(BoxGrid.newD(x), Y);
	}

	public BoxGrid getInsideGrid(double[] X, int y) {
		return getInsideGrid(X, BoxGrid.newD(y));
	}

	public BoxGrid getInsideGrid(int x, int y) {
		return getInsideGrid(BoxGrid.newD(x), BoxGrid.newD(y));
	}
	
	/*public XStrip getXStrip() {
		setComponent(null);
		setInsets(0);
		return (XStrip) (object = new XStrip(getRectangle()));
	}
	
	public YStrip getYStrip() {
		setInsets(0);
		return (YStrip) (object = new YStrip(getRectangle()));
	}*/

	public Rectangle getRectangle() {
		return new Rectangle((int) x + insets.left, (int) y + insets.top, (int) width - insets.left
				- insets.right, (int) height - insets.top - insets.bottom);
	}

	public String toString() {
		return "[x = " + x + ", y = " + y + ", width = " + width + ", height = " + height + "]";
	}

	public void setVisible(boolean visable) {
		if (component != null)
			component.setVisible(visable);

		if (object != null)
			object.setVisible(visable);
	}

	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	
	
}