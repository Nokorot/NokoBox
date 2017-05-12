package util.swing;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.Window;
import util.handelers.ImageHandeler;
import util.handelers.ImageHandeler.ScaleType;
import util.swing.gride.BoxObject;

public class Label extends JLabel implements BoxObject {
	private static final long serialVersionUID = 1L;
	
	public boolean activated = false;
	public String code = "-1";

	public Window window;
	protected BufferedImage image;
	private ScaleType scale = ScaleType.TILLPASS;
	
//	protected double xScale, yScale;
	
	private CompPaint s_paint;

	public Label(Window window, String text, Rectangle rec, String code) {
		LabelSetings s = window.labelSets;

		if (rec != null)
			setBounds(rec);
		if (code == null)
			code = "-1";

		this.window = window;
		this.code = code;
		setText(text);
		setBackground(s.bColor);
		setForeground(s.tColor);
		setFont(s.font);
		setBorder(s.border);

		setHorizontalAlignment(s.horizontalAlignment);
		setVerticalAlignment(s.verticalAlignment);

		s_paint = s.paint;
		
		if (s.icon != null) {
			setIcon(s.icon, s.iconScale);
		}
		window.panel.add(this);
		
	}
	
	public Label(Window window, String text, Rectangle rec){
		this(window, text, rec, null);
	}
	
	public Label(Window window, String text, String code){
		this(window, text, null, code);
	}
	
	public Label(Window window, BufferedImage icon, ScaleType scale) {
		this(window, null, null, "");
		setIcon(icon, scale);
	}

	public Label(Window window, BufferedImage icon, ScaleType scale, Rectangle rec) {
		this(window, null, null, "");
		try {
			setIcon(ImageHandeler.getScaledImage(icon, rec.width, rec.height), scale);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Label(Window w, Rectangle rec){
		this(w, null, rec, null);
	}
	
	public Label(Window w, String text){
		this(w, text, null, null);
	}
	
	public Label(Window w){
		this(w, null, null, "");
	}
	
	public void setPane(JPanel pane) {
		pane.add(this);
	}

	public void setImageIcon(BufferedImage image){
		this.image = image;
		scaledIcon();
	}
	
	public void setIcon(BufferedImage icon, ScaleType scale) {
		image = icon;
		if(this.scale == scale)
			scaledIcon();
		else
			setIconScaling(scale);
	}
	
	public void setIconScaling(ScaleType scale){
		if(scale == null || this.scale == scale)
			return;
		this.scale = scale;
		scaledIcon();
	}
	
	public void setBounds(Rectangle r){
		super.setBounds(r);
		scaledIcon();
	}
	
	private void scaledIcon(){
		if(image == null)
			return;
		try {
			BufferedImage icon = ImageHandeler.getScaledImage(image, getWidth(), getHeight(), scale);
//			xScale = ((double) image.getWidth() / (double) icon.getWidth());
//			yScale = ((double) image.getHeight() / (double) icon.getHeight());
			if (icon != null) {
				setIcon(new ImageIcon(icon));
			}
		} catch (IOException e) {
		}
	}

	
	protected void paintComponent(Graphics g) {
		if(s_paint != null)
			s_paint.paintComponent(this, g);
		super.paintComponent(g);
	}

	public String toString() {
		return "Button [code=" + this.code + ", window=" + this.window + ", getText()=" + getText()
				+ ", getBounds()=" + getBounds() + ", toString()=" + super.toString() + "]";
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setFontSize(int size) {
		Font f = getFont();
		setFont(new Font(f.getName(), f.getStyle(), size));
	}

}