package util.swing.gride;

public class XStrip extends Strip {

	public XStrip() {
	}

	@Override
	protected void fixbounds(double x, double y, double width, double height){
		if (ATot == 0) return;
		
		width /= ATot;
		for (int i = 0; i < this.size(); i++) {
			double w = width * A.get(i);
			boxes.get(i).setBounds(x, y, w, height);
			x += w;
		}
	}
	
	@Override
	protected void rebounds(double dX, double dY, double dWidth, double dHeight) {
		if (ETot != 0)
			dWidth /= ETot;
		double dx = dX;
		for (int i = 0; i < this.size(); i++) {
			double dw = dWidth * E.get(i);
			boxes.get(i).increaseBounds(dx, dY, dw, dHeight);
			dx += dw;
		}
	}

}
