package util.swing.gride;

import java.awt.Rectangle;

public class AspectRatioBox extends Box {
		
		private double aspectRatio;

		public AspectRatioBox() {
			super();
		}
		
		public AspectRatioBox(BoxObject object) {
			super(object);
		}
		
		public void setAspectRatio(double aspectRatio) {
			this.aspectRatio = aspectRatio;
		}

		public Rectangle getRectangle() {
			Rectangle bounds = super.getRectangle();
			
			bounds.x += bounds.width / 2d; 
			bounds.y += bounds.height / 2d;
			
			if (bounds.getWidth() / bounds.getHeight() > aspectRatio){
				bounds.width = (int) (bounds.height * aspectRatio);
			}else{
				bounds.height = (int) (bounds.width / aspectRatio);
			}

			bounds.x -= bounds.width / 2d;
			bounds.y -= bounds.height / 2d;
			
			return bounds;
		}
		
	}