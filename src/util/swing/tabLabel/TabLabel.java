package util.swing.tabLabel;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import util.Window;
import util.swing.Label;

public class TabLabel extends Label {
	private static final long serialVersionUID = 1L;
	
	private Tab active;

	private List<Tab> tabs = new ArrayList<Tab>();

	private int xOffset = 0;
	private int buttonHeight = 25;

	private TabLayout tabLayout;
	
	public TabLabel(Window w, TabLayout layout) {
		super(w);
		this.tabLayout = layout;
		tabLayout.setButtonHeight(buttonHeight);
	}

	public void paint(Graphics g) {
		tabLayout.paintLabel(this, g);
	}

	public void setBounds(Rectangle rec) {
		super.setBounds(rec);
		calculateButtonPos();
	}

	public void addTab(Tab tab) {
		tabs.add(tab);
		tab.setButtonPos(xOffset);
		tab.getButton().setVisible(true);
		setActiveTab(tab);
		xOffset += tab.getButtonWidth();
	}

	public Tab getTab(String text) {
		for (Tab tab : tabs) {
			if (tab.getTitle().equals(text))
				return tab;
		}
		System.err.println("the tab '" + text + "' does not exsist!");
		return null;
	}

	public int getButtonHeight() {
		return buttonHeight;
	}

	public void removeTab(Tab tab) {
		tabs.remove(tab);
		calculateButtonPos();
	}

	private void calculateButtonPos() {
		xOffset = 0;
		for (Tab tabs : tabs) {
			int width = tabs.getButtonWidth();
			
			xOffset += width;
		}
	}

	public void setActiveTab(Tab tab) {
		if(tab == null)
			return;
		if (active != null){
			active.getButton().repaint();
			active.setVisible(false);
		}
		tab.setVisible(true);
		active = tab;
		repaint();
	}
	
	public boolean isActive(Tab b){
		return b == active;
	}

	public void clear() {
		xOffset = 0;
		for (Tab tab : tabs) {
			tab.getButton().setVisible(false);
		}
		tabs.clear();
	}

	public TabLayout getTabLayout() {
		return tabLayout;
	}

	public int getXOffset() {
		return xOffset;
	}

	public Tab getActiveTab() {
		return active;
	}
}
