package util.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import util.Window;

public class ButtonSetings {
	public static Color s_bColor = new JButton().getBackground();
	public static Color s_tColor = new JButton().getForeground();
	public static Icon s_icon = new JButton().getIcon();
	public static Font s_font = new JButton().getFont();
	public static Border s_border = new JButton().getBorder();
	public static boolean s_enable = true;
	public static JPanel s_panel = new JPanel();

	public Color bColor = s_bColor;
	public Color tColor = s_tColor;
	public Icon icon = s_icon;
	public Font font = s_font;
	public Border border = s_border;
	public boolean enable = s_enable;
	public JPanel panel = s_panel;
	public ActionListener listener = s_listener;
	public static ActionListener s_listener;

	public ButtonSetings(final Window window) {
	}

	public void setBColor(Color bColor) {
		this.bColor = bColor;
	}

	public void setTColor(Color tColor) {
		this.tColor = tColor;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public void setActionListener(ActionListener listener) {
		this.listener = listener;
	}
}