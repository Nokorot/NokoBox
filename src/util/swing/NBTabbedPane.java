package util.swing;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import util.Window;
import util.swing.gride.Box;
import util.swing.gride.BoxObject;

public class NBTabbedPane extends JTabbedPane implements BoxObject {
	private static final long serialVersionUID = 1L;

	
//	private Window window;

	public NBTabbedPane(Window window) {
		window.add(this);
	}

	public void addTab(String title, BoxObject tab) {
		this.addTab(title, new Pane(tab));
	}

	public void setPane(JPanel pane) {
		pane.add(this);
	}
	
	class Pane extends JPanel {
		private static final long serialVersionUID = 1L;

		private Box box;

		public Pane(BoxObject object) {
			box = new Box(object);
			setLayout(null);
			object.setPane(this);
		}

		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y, width, height);
			box.setBounds(0, 0, width, height);
		}

	}
}