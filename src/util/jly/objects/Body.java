package util.jly.objects;

import util.Window;
import util.jly.contField;
import util.jly.contObject;
import util.swing.NBButton;
import util.swing.Label;
import util.swing.TextField;
import util.swing.gride.Box;
import util.swing.gride.XStrip;
import util.swing.gride.YStrip;

public class Body {

	private static Window lWindow;
	
	public static void Build(Window window, contObject o) {
		lWindow = window;
		
		Box frameBox = window.getFrameBox();

		
		contObject[] X = o.getObjects("X");
//		TODO: contObject[] Y = o.getObjects("Y");

		YStrip yS = new YStrip();
		frameBox.setBoxObject(yS);
		
		for (contObject contObject : o.objects) {
			
			switch (contObject.name) {
			case "X": 
				Box box = new Box();
				yS.append(box);
				buildX(contObject, box);
				break;
			
			default:
				break;
			}

//			contField wP = contObject.getP("w"); 
//			double w = wP == null ? 1 : wP.asDouble();
//			contField ewP = contObject.getP("ew"); 
//			double ew = ewP == null ? w : ewP.asDouble();
//			
//			Box xB = xS.getBox(xS.append(w, ew));
//			
//			buildComponent(contObject, xB);
		}
		
		
		lWindow = null;
	}
	
	private static void buildComponent(contObject object, Box box){
		switch (object.name) {
		case "Y": 			buildY(object, box); break;
		case "X": 			buildX(object, box); break;
		case "Button": 		buildButton(object, box); break;
		case "TextField": 	buildTextField(object, box); break;
		case "Label": 		buildLabel(object, box); break;
		}
	}

	private static void buildY(contObject object, Box box) {
		YStrip yS = new YStrip();
		box.setBoxObject(yS);
		for (contObject contObject : object.objects) {
			contField hP = contObject.getP("h"); 
			double h = hP == null ? 1 : hP.asDouble();
			contField ehP = contObject.getP("eh"); 
			double eh = ehP == null ? h : ehP.asDouble();
			
			Box yB = new Box();
			yS.append(yB, h, eh);
			
			buildComponent(contObject, yB);
		}
	}

	private static void buildX(contObject object, Box box) {
		XStrip x = new XStrip();
		
		for (contObject contObject : object.objects) {
			contField wP = contObject.getP("w"); 
			double w = wP == null ? 1 : wP.asDouble();
			contField ewP = contObject.getP("ew"); 
			double ew = ewP == null ? w : ewP.asDouble();
			
			Box xB = new Box();
			x.append(xB, w, ew);
			buildComponent(contObject, xB);
		}
		box.setBoxObject(x);
	}

	private static void buildButton(contObject object, Box box) {
		NBButton b = new NBButton(lWindow, object.data, object.getP("").data);
		box.setComponent(b);
	}

	private static void buildLabel(contObject object, Box box) {
		Label b = new Label(lWindow);
		b.setText(object.data);

		for (contField p : object.properties) 
			switch (p.name) {
			case "": b.setCode(p.asString()); break;
			case "tc": b.setForeground(Head.asColor(p)); break;
			}
		box.setComponent(b);
	}

	private static void buildTextField(contObject object, Box box) {
		TextField b = new TextField(lWindow);
		b.setText(object.data);
		
		for (contField p : object.properties) 
			switch (p.name) {
			case "": b.setCode(p.asString()); break;
			case "enabled": b.setEditable(p.asBoolean()); break;
			case "tc": b.setForeground(Head.asColor(p)); break;
			case "bc": b.setBackground(Head.asColor(p)); break;
			}
		box.setComponent(b);
	}
	

}
