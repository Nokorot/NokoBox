package util.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.border.Border;

import util.Window;
import util.handelers.ImageHandeler.ScaleType;

public class LabelSetings {
	public static Color s_bColor = new JLabel().getBackground();
	public static Color s_tColor = new JLabel().getForeground();
	public static Font s_font = new JLabel().getFont();
	public static Border s_border = new JLabel().getBorder();
	public static int s_horizontalAlignment = 0;
	public static int s_verticalAlignment = 0;
	public static CompPaint s_paint;
	private static BufferedImage s_icon = (BufferedImage) new JLabel().getIcon();
	private static ScaleType s_iconScale = ScaleType.TILLPASS;


	protected Color bColor = s_bColor;
	protected Color tColor = s_tColor;
	protected BufferedImage icon = s_icon;
	protected ScaleType iconScale = s_iconScale;
	protected Font font = s_font;
	protected Border border = s_border;
	protected int horizontalAlignment = s_horizontalAlignment;
	protected int verticalAlignment = s_verticalAlignment;
	protected CompPaint paint = s_paint;

	public LabelSetings(final Window window) {
	}

	public void setBColor(Color bColor) {
		this.bColor = bColor;
	}

	public void setTColor(Color tColor) {
		this.tColor = tColor;
	}

	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setFontSize(float size) {
		this.font = this.font.deriveFont(size);
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public void setIcon(BufferedImage icon, ScaleType scale){
		this.icon = icon;
		this.iconScale = scale;
	}
	
	public static void setStandardIcon(BufferedImage icon, ScaleType scale){
		s_icon = icon;
		s_iconScale = scale;
	}
}