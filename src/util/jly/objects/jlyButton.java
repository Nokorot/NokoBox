package util.jly.objects;

import util.Window;
import util.jly.contObject;
import util.swing.NBButton;

public class jlyButton {

	public static NBButton Build(Window window, contObject o) {
		
		NBButton b = new NBButton(window);
		b.setText(o.getF("").data);
		
		return b;
	}

}
