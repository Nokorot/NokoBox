package util.swing.gride;

import java.awt.Rectangle;

import javax.swing.JPanel;

public interface BoxObject {
	
	// public void setBounds(double x, double y, double width, double height);
	
	public void setBounds(Rectangle rectangle);

	public void setVisible(boolean visable);
	
	public interface PreferdBoxObject extends BoxObject {
		
		public void makePreferdBox(Box box);
		
	}

	public void setPane(JPanel pane);

}
