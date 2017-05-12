package util.swing;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import util.Window;
import util.swing.gride.BoxObject;

public class PopDownTextField extends JComboBox<String> implements BoxObject {
	private static final long serialVersionUID = 1L;

	public String code = "-1";

	public Window window;

	private NokoComboBoxModel<String> model;

	public PopDownTextField(Window window, Rectangle rec, String code) {

		this.window = window;
		if (code != null)
			this.code = code;

		if (rec != null)
			setBounds(rec);

		model = new NokoComboBoxModel<String>();
		super.setModel(model);

		TextFieldSetings s = window.textfeldSets;
		setBackground(s.bColor);
		setForeground(s.tColor);
		setFont(s.font);
		setBorder(s.border);

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof PopDownTextField) {
					onAction();
					window.PopDownTextFieldAction((PopDownTextField) e.getSource());
				}
			}
		});

		window.panel.add(this);
	}

	public PopDownTextField(Window window, Rectangle rec) {
		this(window, rec, null);
	}

	public PopDownTextField(Window window, String code) {
		this(window, null, code);
	}

	public PopDownTextField(Window window) {
		this(window, null, null);
	}
	
	public void setPane(JPanel pane) {
		pane.add(this);
	}

	protected void onAction() {
	};

	public void setData(Vector<String> elements){
		model.setElements(elements);
	}
	
	public Vector<String> getData() {
		return model.getElements();
	}
	
	public void setModel(NokoComboBoxModel<String> aModel) {
		super.setModel(aModel);
		model = aModel;
	}

	public void setFontSize(int size) {
		setFont(getFont().deriveFont(size));
	}
	
	public void addElement(String element) {
		model.addElement(element);
	}

	public void addElements(String... elements) {
		for (String e : elements)
			model.addElement(e);
	}

	public void addElements(List<String> elements) {
		for (String e : elements)
			model.addElement(e);
	}

	public void insertElementAt(String element, int index) {
		model.insertElementAt(element, index);
	}

	public boolean isSelected(String s) {
		if (getSelectedItem().equals(s))
			return true;
		return false;
	}

	public String getSelectedItem() {
		return (String) super.getSelectedItem();
	}

	public int getElementCount() {
		return model.getSize();
	}
	
	public int getIndexOf(String fontname) {
		return model.getIndexOf(fontname);
	}

	public void clear() {
		model.removeAllElements();
	}

}