package util.swing;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.Window;
import util.swing.gride.BoxObject;

public class TextArea extends JTextArea implements BoxObject {
	private static final long serialVersionUID = 1L;

	public String code;
	public boolean activated = false;

	private JScrollPane scrol = new JScrollPane();

	public TextArea(Window window, String code, Rectangle rec, String text, boolean editable) {
		this.code = code;
		
		if(rec != null){
			setBounds(rec);
			scrol.setBounds(rec);
		}
		if(text != null)
			setText(text);
		
		TextAreaSetings s = window.textareaSets;
		
		setBackground(s.bColor);
		setForeground(s.tColor);
		setFont(s.font);
		setBorder(s.border);
		setEditable(editable);

		scrol.setViewportView(this);
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				KeyPresed.keys[e.getKeyCode()] = true;
				KeyPress(e.getKeyCode());
			}

			public void keyReleased(KeyEvent e) {
				KeyPresed.keys[e.getKeyCode()] = false;
			}

			public void keyTyped(KeyEvent e) {
			}
		});

		window.panel.add(this.scrol);
	}

	public TextArea(Window window, Rectangle rec, String text, boolean editeable) {
		this(window, null, rec, text, editeable);
	}

	public TextArea(Window window, Rectangle rec, boolean editeable) {
		this(window, null, rec, null, editeable);
	}

	public TextArea(Window window, boolean editable) {
		this(window, null, null, null, editable);
	}

	
	
	
	protected void KeyPress(int key) {
		System.out.println(KeyEvent.getKeyText(key));
	}
	
	protected void KeyReleas(int key) {
	}

	
	
	public void setFonrSize(int size) {
		Font f = getFont();
		setFont(new Font(f.getName(), f.getStyle(), size));
	}

	@Override
	public void setBounds(Rectangle r) {
		scrol.setBounds(r);
		super.setBounds(r);
	}

	@Override
	public void setPane(JPanel pane) {
		pane.add(this);
	}
	
	
	/*
	public static void setWidth(int w) {
		Width = w;
	}

	public static void setHeight(int h) {
		Height = h;
	}

	public static void setX(int xa) {
		x = xa;
	}

	public static void setY(int ya) {
		y = ya;
	}

	public static void setColor(Color col) {
		SColor = col;
	}

	public static void setSFont(Font font) {
		SFont = font;
	}

	public static void setSBorder(Border border) {
		SBorder = border;
	}

	public static void setStandardSize(int width, int height) {
		Width = width;
		Height = height;
	}

	public static void setPanel(JPanel panel) {
		SPanel = panel;
	}
	*/
}