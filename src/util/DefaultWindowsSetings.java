package util;

import java.awt.Color;
import java.awt.Image;
import java.awt.LayoutManager;

import util.swing.gride.Insets;

public class DefaultWindowsSetings {
	
	public static int defaultCloseOperation = 3;
	public static Image icon;
	public static Insets gridInset = new Insets(5, 5, 5, 5);
	
	public static LayoutManager panelLayout = null;
	
	public static Color background = Color.WHITE;
	
	
	protected static void setSetings(Window window){
		window.setDefaultCloseOperation(defaultCloseOperation);
		window.setLocationRelativeTo(null);
		window.getContentPane().setLayout(panelLayout);
		window.getContentPane().setBackground(background);
		if(icon != null)
			window.setIconImage(icon);
	}


}
