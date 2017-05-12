package util.swing;

import java.awt.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;

import util.Window;
import util.swing.gride.BoxObject;

public class TextList extends JList<String> implements BoxObject {
	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> listModel;
	private JScrollPane sp;

	public TextList(Window window, Rectangle rec) {
		setModel(listModel = new DefaultListModel<String>());
		sp = new JScrollPane(this);
		sp.setBounds(rec);
		
		
		
		addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent event) {
				System.out.println(event);
			}
			public void ancestorMoved(AncestorEvent event) {
//				System.out.println(event);
				
			}
			public void ancestorAdded(AncestorEvent event) {
				System.out.println(event);
				
			}
		});
		
		addListSelectionListener((ListSelectionEvent e) -> window.TextListAction(this) );
		
		TextFieldSetings s = window.textfeldSets;


		if(rec == null)
			rec = new Rectangle();
		
		setBackground(s.bColor);
		setForeground(s.tColor);
		setFont(s.font);
		sp.setBorder(s.border);
		window.panel.add(sp);
		
	}

	public TextList(Window window) {
		this(window, new Rectangle());
	}
	
	public void setPane(JPanel pane) {
		pane.add(this);
	}

	public void addElement(String element) {
		listModel.addElement(element);
	}

	public void addElement(int index, String element) {
		listModel.add(index, element);
	}
	
	public void addelements(String... elements) {
		for (String s : elements)
			listModel.addElement(s);
	}
	
	public void addelements(int index, String... elements) {
		for (String s : elements)
			addElement(index, s);
	}
	
	public void setBounds(Rectangle r){
		sp.setBounds(r);
	}

	public void setFontSize(float f) {
		super.setFont(getFont().deriveFont(f));
	}

}
