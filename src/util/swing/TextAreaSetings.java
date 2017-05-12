package util.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.border.Border;

import util.Window;

public class TextAreaSetings {
	
	public static Color s_bColor 			= new JTextArea().getBackground();
	public static Color s_tColor 			= new JTextArea().getForeground();
	public static Font s_font 				= new JTextArea().getFont();
	public static Border s_border 			= new JTextArea().getBorder();

	protected Color bColor 				= s_bColor;
	protected Color tColor 				= s_tColor;
	protected Font font 				= s_font;
	protected Border border 			= s_border;
	
	public TextAreaSetings(Window window) {
	}
	
	public void setBColor(Color bColor) {
		this.bColor = bColor;
	}

	public void setTColor(Color tColor) {
		this.tColor = tColor;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public void setFontSize(int i) {
		this.font = new Font(this.font.getName(), this.font.getStyle(), i);
	}
}