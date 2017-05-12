package util.jly.objects;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import util.Window;
import util.jly.contField;
import util.jly.contObject;

public class Head {

	public static void Build(Window window, contObject content) {
		boolean visible = true;
		String title = "JLYWindow";
		int width = 500, height = 300;

		for (contField field : content.fields) {
			switch (field.name) {
			case "title":
				title = field.data; break;
			case "width":
				width = field.asInt(); break;
			case "height":
				height = field.asInt(); break;
			case "size":
				int[] i = field.asInts();
				width = i[0];
				height = i[1];
				break;
			case "visible":
				visible = field.asBoolean(); break;
			case "resizeable":
				window.setResizable(field.asBoolean()); break;
			case "background":
				window.panel.setBackground(asColor(field)); break;
			}
		}
		
		window.setTitle(title);
		window.setSize(width, height);
		window.setLocationRelativeTo(null);
		window.setVisible(visible);
	}
	
	private static Map<String, Color> colorMap = new HashMap<String, Color>();
	
	static{
		colorMap.put("black", 		Color.BLACK);
		colorMap.put("white", 		Color.WHITE);
		colorMap.put("grey", 		Color.GRAY);
		colorMap.put("dark_grey", 	Color.DARK_GRAY);
		colorMap.put("light_grey", 	Color.LIGHT_GRAY);
		colorMap.put("red", 		Color.RED);
		colorMap.put("green", 		Color.GREEN);
		colorMap.put("blue", 		Color.BLUE);
		colorMap.put("yellow", 		Color.YELLOW);
		colorMap.put("orange", 		Color.ORANGE);
		colorMap.put("clay", 		Color.CYAN);
		colorMap.put("pink", 		Color.PINK);
		colorMap.put("magenta", 	Color.MAGENTA);
	}
	
	public static Color asColor(contField field) {
		String s = field.remove(" \t");
		Color c = colorMap.get(s); 
		if (c == null && s.startsWith("0x"))
			c = Color.decode(s);
		return c;
	}
	
}
