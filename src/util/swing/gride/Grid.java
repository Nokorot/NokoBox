package util.swing.gride;

import java.awt.Rectangle;

import util.math.Maths;

public class Grid {
	private Rectangle bounds;
	
	public Rectangle[] Grid;

	private int space;
	private double totX;
	private double totY;
	private double[] X;
	private double[] Y;

	private Grid(){}
	
	public Grid(Rectangle rec, double[] X, double[] Y, int space) {
		
		this.bounds = rec;
		this.Grid = new Rectangle[X.length * Y.length];
		this.X = X;
		this.Y = Y;
		this.space = space;

		totX = Maths.sum(X);
		totY = Maths.sum(Y);
		
		setRecs();
		
	}
	
	public Grid(Rectangle rec, int xSize, int ySize, int space) {
		this(rec, genDoubles(xSize), genDoubles(ySize), space);
	}
	
	public Grid(Rectangle rec, int xSize, double[] y, int space){
		this(rec, genDoubles(xSize), y, space);
	}
	
	public Grid(Rectangle rec, double[] x, int ySize,  int space){
		this(rec, x, genDoubles(ySize), space);
	}
	
	public static Grid Static(Rectangle rec, int xSize, int ySize, int space){
		Grid grid = new Grid();
		grid.Grid = new Rectangle[xSize * ySize];
		for (int i = 0; i < grid.Grid.length; i++) 
			grid.Grid[i] = new Rectangle(rec.x + i % xSize * (rec.width + space), rec.y + i / xSize * (rec.width + space), rec.width, rec.width);
		return grid;
	}
	
	public static Grid split(String type, Rectangle rec, double[] d) {
		if (type.equals("X"))
			return new Grid(rec, d, new double[] { 1.0D }, 1);
		if (type.equals("Y")) {
			return new Grid(rec, new double[] { 1.0D }, d, 1);
		}
		return new Grid(rec, new double[] { 1.0D }, new double[] { 1.0D }, 0);
	}
	
	public static Grid split(String type, Rectangle rec, int i){
		return split(type, rec, genDoubles(i));
	}
	
	private static double[] genDoubles(int size){
		double[] result = new double[size];
		for (int i = 0; i < size; i++)
			result[i] = 1.0D;
		return result;
	}
	

	private void setRecs() {
		if(bounds == null){
			for (int i = 0; i < Grid.length; i++)
				Grid[i] = new Rectangle();
			return;
		}
		
		double width1 = (bounds.width + space) / this.totX;
		double height1 = (bounds.height + space) / this.totY;

		double xp = 0.0D;
		double yp = 0.0D;
		for (int y1 = 0; y1 < Y.length; y1++) {
			for (int x1 = 0; x1 < X.length; x1++) {
				this.Grid[(x1 + y1 * X.length)] = new Rectangle((int) (xp + bounds.x), (int) (yp + bounds.y), (int) (this.X[x1] * width1 - this.space),
						(int) (this.Y[y1] * height1 - this.space));
				xp += this.X[x1] * width1;
			}
			yp += this.Y[y1] * height1;
			xp = 0.0D;
		}
	}

	public Rectangle getRec(int x, int y) {
		return this.Grid[(x + y * X.length)];
	}

	public Rectangle getRec(int index) {
		return this.Grid[index];
	}

	public Rectangle getRec(int x1, int y1, int x2, int y2) {
		Rectangle rec1 = this.Grid[(x1 + y1 * X.length)];

		int xDif = x2 - x1;
		int yDif = y2 - y1;

		int widtha = this.space * xDif;
		for (int i = 0; i <= xDif; i++) {
			widtha += this.Grid[(x1 + i + y1 * X.length)].width;
		}
		int heighta = this.space * yDif;
		for (int i = 0; i <= yDif; i++) {
			heighta += this.Grid[(x1 + (y1 + i) * X.length)].height;
		}

		return new Rectangle(rec1.x, rec1.y, widtha, heighta);
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		setRecs();
	}
	
	public void setBounds(int x, int y, int width, int height){
		bounds.setBounds(x, y, width, height);
		setRecs();
	}
	
	public void setSize(int width, int height) {
		bounds.setSize(width, height);
		setRecs();
	}

	public void setWidth(int width) {
		bounds.width = width;
		setRecs();
	}
	
	public void setHeight(int height) {
		bounds.height = height;
		setRecs();
	}
	
	public void setLocation(int x, int y) {
		bounds.setLocation(x, y);
		setRecs();
	}
	
	public void setX(int x){
		bounds.x = x;
		setRecs();
	}
	
	public void setY(int y) {
		bounds.y = y;
		setRecs();
	}

	public int size() {
		return this.Grid.length;
	}
}