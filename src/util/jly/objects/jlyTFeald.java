package util.jly.objects;

import util.Window;
import util.jly.contObject;
import util.swing.TextField;

public class jlyTFeald {

	public static TextField Build(Window window, contObject o) {

		TextField tf = new TextField(window);
		if(o.getF("") != null)
			tf.setText(o.getF("").data);
		
		return tf;
	}

}
