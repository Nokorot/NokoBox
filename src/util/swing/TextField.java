package util.swing;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import util.Window;
import util.swing.gride.BoxObject;

public class TextField extends JTextField implements BoxObject {
	private static final long serialVersionUID = 1L;
	
	public boolean activated = false;
	public String code = "-1";

	public Window window;

	public TextField(Window window, String text, Rectangle rec, String code, boolean editable) {

		this.window = window;
		this.code = code;

		if(rec != null)
			setBounds(rec);
		if(text != null)
			setText(text);

		TextFieldSetings s = window.textfeldSets;

		setBackground(s.bColor);
		setForeground(s.tColor);
		setFont(s.font);
		setBorder(s.border);
		setEditable(editable);
		setHorizontalAlignment(s.horizontal);

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() instanceof TextField){
					onAction();
					window.TextFieldAction((TextField) e.getSource());
				}
			}
		});

		window.panel.add(this);
	}

	public TextField(Window window, String text, Rectangle rec, String code) {
		this(window, text, rec, code, true);
	}
	
	public TextField(Window window, String text, Rectangle rec){
		this(window, text, rec, null);
	}
	
	public TextField(Window window, String text){
		this(window, text, null, null);
	}
	
	public TextField(Window window, String text, String code){
		this(window, text, null, code);
	}

	public TextField(Window window, Rectangle rec, String code, boolean editable) {
		this(window, null, rec, code, editable);
	}

	public TextField(Window window, Rectangle rec, String code) {
		this(window, null, rec, code, true);
	}

	public TextField(Window window, Rectangle rec) {
		this(window, null, rec, null, true);
	}
	
	public TextField(Window window){
		this(window, null, null, true);
	}
	
	public void setPane(JPanel pane) {
		pane.add(this);
	}
	
	

	protected void onAction(){};
	
	/*
	
	public void Hide() {
		setLocation(-100, -100);
		setSize(0, 0);
		this.panel.repaint();
	}

	public void Visible() {
		setVisible(true);
		
//		setLocation(this.rec.x, this.rec.y);
//		setSize(this.rec.width, this.rec.height);
//		this.panel.repaint();
	}*/

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setFonrSize(int size) {
		Font f = getFont();
		setFont(new Font(f.getName(), f.getStyle(), size));
	}
}