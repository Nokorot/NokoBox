package util.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import util.Window;
import util.handelers.ImageHandeler;
import util.handelers.ImageHandeler.ScaleType;
import util.swing.gride.BoxObject;

public class NBButton extends JButton implements BoxObject {
	private static final long serialVersionUID = 1L;
	
	public boolean activated = false;
	public String code = "-1";

	private BufferedImage image;
	private ScaleType scale = ScaleType.TILLPASS;
	
	public Window window;

	public NBButton(Window window, String text, Rectangle rec, String code) {
		
		this.window = window;
		this.code = code;

		if(rec != null)
			setBounds(rec);
		if(text != null)
			setText(text);
		
		ButtonSetings s = window.buttonSets;
		
		System.out.println(s.bColor);
		
		setBackground(s.bColor);
		setForeground(s.tColor);
		setFont(s.font);
		setBorder(s.border);

		setOpaque(true);
		
		if(s.icon != null)
			setIcon(s.icon);

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((e.getSource() instanceof NBButton)) {
					NBButton b = (NBButton) e.getSource();
					if(b == null)
						return;
					window.ButtonAction(b);
					b.onAction();
				}
			}
		});

		window.panel.add(this);
	}

	public NBButton(Window window, String text, Rectangle rec) {
		this(window, text, rec, null);
	}

	public NBButton(Window window, Rectangle rec, String code) {
		this(window, null, rec, code);
	}

	public NBButton(Window window, String text, String code) {
		this(window, text, null, code);
	}

	public NBButton(Window window, String text) {
		this(window, text, null, null);
	}

	public NBButton(Window window){
		this(window, null, null, null);
	}
	
	protected void onAction(){
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
	
	public void setBColor(Color c) {
		if (c == null)
			c = window.buttonSets.bColor;
		setBackground(c);
	}

	public void setFonrSize(int size) {
		Font f = getFont();
		setFont(new Font(f.getName(), f.getStyle(), size));
	}

	public String toString() {
		return "Button [code=" + this.code + ", window=" + this.window + ", getText()=" + getText()
				+ ", getBounds()=" + getBounds() + ", toString()=" + super.toString() + "]";
	}
}
