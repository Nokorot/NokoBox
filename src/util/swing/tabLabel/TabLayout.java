package util.swing.tabLabel;

import java.awt.Color;
import java.awt.Graphics;

import util.swing.NBButton;

public abstract class TabLayout {
	
	protected int buttonHeight = 0;

	protected abstract void paintButton(NBButton button, Graphics g, boolean Active);
	protected abstract void paintLabel(TabLabel label, Graphics g);
	
	public void setButtonHeight(int x){
		this.buttonHeight = x;
	}
	
	public static class RoundRecTabLayout extends TabLayout {

		public Color selectedButtonColor = Color.white;
		public Color unselectedButtonColor = Color.lightGray;
		
		private Color lightColor = Color.white;
		private Color darkColor = new Color(0x222222);

		private int ovalWidth = 6, ovalHeight = 6;
		
		public RoundRecTabLayout(Color lightColor, Color darkColor, int ovalWidth, int ovalHeight){
			this.lightColor = lightColor;
			this.darkColor = darkColor;
			this.ovalWidth = ovalWidth;
			this.ovalHeight = ovalHeight;
		}
		
		public RoundRecTabLayout(){
		}

		protected void paintButton(NBButton button, Graphics g, boolean Active) {
			int width = button.getWidth() - 1;
			int height = button.getHeight() -1 ;
			
			g.setColor(Active ? selectedButtonColor : unselectedButtonColor);
			g.fillRect(ovalWidth, 0, width - ovalWidth * 2, ovalHeight);
			g.fillRect(0, ovalHeight, width, height - ovalHeight);
			g.fillArc(0, 0, 2 * ovalWidth, 2 * ovalHeight, 180, -90);
			g.fillArc(width - 2 * ovalWidth, 0, 2 * ovalWidth, 2 * ovalHeight, 90, -90);
			
			g.setColor(lightColor);
			g.drawArc(0, 0, 2 * ovalWidth, 2 * ovalHeight, 180, -90);
			g.drawArc(0 + width - 2 * ovalWidth, 0, 2 * ovalWidth, 2 * ovalHeight, 90, -90);
			g.drawLine(0 + ovalWidth, 0, 0 + width - ovalWidth, 0);
			g.drawLine(0, 0 + ovalHeight, 0, 0 + height);

			g.setColor(darkColor);
			g.drawLine(0 + width, 0 + ovalHeight, 0 + width, 0 + height);
		}
		
		protected void paintLabel(TabLabel label, Graphics g) {
				int width = label.getWidth() - 2;
				int height = label.getHeight() - 2;

				g.setColor(lightColor);
				g.drawArc(width - 2 * ovalWidth, buttonHeight, 2 * ovalWidth, 2 * ovalHeight, 90, -90);
				g.drawLine(label.getXOffset() - 1, buttonHeight, width - ovalWidth, buttonHeight);
				g.drawLine(0, buttonHeight, 0, height - ovalHeight);

				g.setColor(darkColor);
				g.drawLine(width, buttonHeight + ovalHeight, width, height - ovalHeight);
				g.drawLine(ovalWidth, height, width - ovalWidth, height);
				g.drawArc(0, height - ovalHeight * 2, ovalWidth * 2, ovalHeight * 2, 180, 90);
				g.drawArc(width - ovalWidth * 2, height - ovalHeight * 2, ovalWidth * 2, ovalHeight * 2, -90, 90);					
		}
	}
	
	
	
	@SuppressWarnings("unused")
	public static class NunTabLayout extends TabLayout {

		public Color selectedButtonColor = Color.white;
		public Color unselectedButtonColor = Color.lightGray;
		
		private Color lightColor = Color.white;
		private Color darkColor = new Color(0x222222);
		
		private int ovalWidth = 6, ovalHeight = 6;

		public NunTabLayout(){
		}

		protected void paintButton(NBButton button, Graphics g, boolean Active) {
//			int width = button.getWidth() - 1;
//			int height = button.getHeight() -1 ;
//
//			if(Active) {
//			g.setColor(lightColor);
//			g.drawArc(0, 0, 2 * ovalWidth, 2 * ovalHeight, 180, -90);
//			g.drawArc(0 + width - 2 * ovalWidth, 0, 2 * ovalWidth, 2 * ovalHeight, 90, -90);
//			g.drawLine(0 + ovalWidth, 0, 0 + width - ovalWidth, 0);
//			g.drawLine(0, 0 + ovalHeight, 0, 0 + height);
//
//			g.setColor(darkColor);
//			g.drawLine(0 + width, 0 + ovalHeight, 0 + width, 0 + height);
//			}
		}
		
		protected void paintLabel(TabLabel label, Graphics g) {
//				int width = label.getWidth() - 2;
//				int height = label.getHeight() - 2;
//				
//				Tab active = label.getActiveTab();
//				
//				if(active == null)
//					return;
//
//				g.setColor(lightColor);
//				g.drawArc(width - 2 * ovalWidth, buttonHeight, 2 * ovalWidth, 2 * ovalHeight, 90, -90);
//				g.drawLine(label.getXOffset() - 1, buttonHeight, width - ovalWidth, buttonHeight);
//				g.drawLine(0, buttonHeight, 0, height - ovalHeight);
//
//				g.drawLine(0, buttonHeight, active.getButton().getX() - ovalWidth - 2, buttonHeight);
//				g.drawLine(active.getButton().getX() + active.getButtonWidth() - ovalWidth - 2, buttonHeight, width - ovalWidth, buttonHeight);
//
//				g.setColor(darkColor);
//				g.drawLine(width, buttonHeight + ovalHeight, width, height - ovalHeight);
//				g.drawLine(ovalWidth, height, width - ovalWidth, height);
//				g.drawArc(0, height - ovalHeight * 2, ovalWidth * 2, ovalHeight * 2, 180, 90);
//				g.drawArc(width - ovalWidth * 2, height - ovalHeight * 2, ovalWidth * 2, ovalHeight * 2, -90, 90);	
				
		}
		
	}
}