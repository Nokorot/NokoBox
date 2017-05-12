package util.swing.tabLabel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import util.adds.UpdateAdd;
import util.handelers.ImageHandeler;
import util.handelers.ImageHandeler.ScaleType;
import util.swing.NBButton;
import util.swing.gride.BoxGrid;

public class Tab {

	private String code;
//	private BufferedImage icon;
	
	private TabLabel label;
	
	private NBButton button;
	private BoxGrid boxGrid;

	public Tab(TabLabel label, String title) {
		this.code = title;
		this.label = label;
		
		Tab tab = this;

		button = new NBButton(label.window, title){
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g){
				label.getTabLayout().paintButton(this, g, label.isActive(tab));
//				this.s_paint = null;
				super.paintComponent(g);
			}
		};
		button.setBorder(null);
		button.setForeground(Color.WHITE);
		button.setSize(title.length() * 10, label.getButtonHeight());

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.setActiveTab(tab);
			}
		});
	}

	public Tab(TabLabel label, BufferedImage icon, String code) {
		this(label, "");
		this.code = code;
//		this.icon = icon;
		button.setSize(label.getButtonHeight() + 5, label.getButtonHeight());
		try {
			button.setIcon(new ImageIcon(ImageHandeler.getScaledImage(icon, button.getWidth(), button.getHeight(), ScaleType.TILLPASS)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public NBButton getButton(){
		return button;
	}
	
	public void setButtonPos(int x) {
		button.setLocation(x + label.getX(), label.getY());
	}
	
	public Insets i = new Insets(5, 5, 5, 5);

	public BoxGrid getGrid(double[] X, double[] Y) {

		boxGrid = new BoxGrid(new Rectangle(label.getX() + i.left, label.getY() + label.getButtonHeight()
				+ i.top, getWidth() - i.left - i.right, getHeight() - i.top - i.bottom), X, Y);
		label.window.addUpdate(new UpdateAdd() {
			public void mUpdate1() {
			}

			public void mUpdate() {
				boxGrid.setBounds(new Rectangle(label.getX() + i.left, label.getY()
						+ label.getButtonHeight() + i.top, getWidth() - i.left - i.right, getHeight() - i.top
						- i.bottom));
			}
		});
		return boxGrid;
	}

	public BoxGrid getGrid(int x, double[] Y) {
		return getGrid(BoxGrid.newD(x), Y);
	}

	public BoxGrid getGrid(double[] X, int y) {
		return getGrid(X, BoxGrid.newD(y));
	}

	public BoxGrid getGrid(int x, int y) {
		return getGrid(BoxGrid.newD(x), BoxGrid.newD(y));
	}

	public String getTitle() {
		return code;
	}

	public int getHeight() {
		return label.getHeight() - label.getButtonHeight();
	}

	public int getWidth() {
		return label.getWidth();
	}

	public void setVisible(boolean b) {
		if (boxGrid != null)
			boxGrid.setVisible(b);
	}

	public int getButtonWidth() {
		return button.getWidth();
	}
}
