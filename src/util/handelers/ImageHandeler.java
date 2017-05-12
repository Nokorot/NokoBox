package util.handelers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageHandeler {
	
	public static boolean Silent = true;

	public static enum ScaleType {

		NOTSCALE, FYLL, TILLPASS, STREKK;

		public static ScaleType parse(String in) {
			if(in == null)
				return NOTSCALE;
			return valueOf(in);
		}
		
	}
	
	public static BufferedImage[] loadSheet(String path, int X, int Y) {
		BufferedImage image = load(path);
		if (image == null)
			return null;

		int w = image.getWidth() / X;
		int h = image.getHeight() / Y;
		
		BufferedImage[] result = new BufferedImage[X * Y];
		for (int y = 0; y < Y; y++){
			for (int x = 0; x < X; x++){
				result[x + y * X] = image.getSubimage(x * w, y * h , w, h);
			}
		}
		return result;
		
	}
	
	public static BufferedImage load(String path) {
		BufferedImage image = null;
		try {
			if(!Silent)
				System.out.print("Loding local Image : (" + path + ") .... ");
			image = ImageIO.read(ImageHandeler.class.getResource(path));
			if(!Silent)
			System.out.println("Sucsecd!!");
		} catch (Exception e) {
			System.err.println("Failed!! loding: " + path);
		}
		return image;
	}

	public static BufferedImage loadGloab(String path) {
		return loadGloab(new File(path));
	}
	
	public static BufferedImage loadGloab(File file) {
		if(file == null || !file.exists()){
			System.err.println("File does not exist: \"" + file.getPath() + "\"");
			return null;
		}
		try {
			if(!Silent)
				System.out.print("Loding Image : (" +  file.getPath() + ") .... ");
			BufferedImage i = ImageIO.read(file);
			if(!Silent)
				System.out.println("Sucsecd!!");
			return i;
		} catch (Exception e) {
			System.err.println("\nException! wenn loading: \"" + file.getPath() + "\"");
		}

		return null;
	}

	public static void save(String path, BufferedImage image) {
		save(new File(path), image);
	}
	
	public static void save(File file, BufferedImage image) {
//		if(file == null || !file.exists()){
//			System.err.println("File does not exist: \"" + file.getPath() + "\"");
//			return null;
//		}
		
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try {
//			if(!Silent)
//				System.out.print("Loding Image : (" +  file.getPath() + ") .... ");
//			ImageIO.read(file);
//			if(!Silent)
//				System.out.println("Sucsecd!!");
//			return i;
//		} catch (Exception e) {
//			System.err.println("\nException! wenn loading: \"" + file.getPath() + "\"");
//		}

	}
	

	public static BufferedImage cut(BufferedImage img, Rectangle rec) {
		try {
			img = img.getSubimage(rec.x, rec.y, rec.width, rec.height);
		} catch (Exception e) {
			System.err.println(img.getHeight() + ", " + img.getWidth() + ", " + rec);
		}

		return img;
	}

	public static BufferedImage cut(BufferedImage img, int x, int y, int size) {
		return cut(img, new Rectangle(x * size, y * size, size, size));
	}

	public static BufferedImage getScaledImage(BufferedImage image, int width, int height)
			throws java.io.IOException {
		if (image == null || width <= 0 || height <= 0)
			return null;
		double imageWidth = image.getWidth();
		double imageHeight = image.getHeight();

		double scaleX = width / imageWidth;
		double scaleY = height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, 2);

		return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
	}

	public static BufferedImage getScaledImage(BufferedImage icon, int width, int height, ScaleType scale) throws IOException {
		if(icon == null)
			return null;
		if (scale == ScaleType.STREKK)
			return getScaledImage(icon, width, height);
		else if (scale == ScaleType.TILLPASS) {
			double widthS = (double) width / (double) icon.getWidth();
			double heightS = (double) height / (double) icon.getHeight();
			width = (int) (Math.min(widthS, heightS) * icon.getWidth());
			height = (int) (Math.min(widthS, heightS) * icon.getHeight());
			return getScaledImage(icon, width, height);
		} else if (scale == ScaleType.FYLL) {
			double widthS = (double) width / (double) icon.getWidth();
			double heightS = (double) height / (double) icon.getHeight();
			return getScaledImage(icon, (int) ((double) icon.getWidth() * Math.max(widthS, heightS)),
					(int) ((double) icon.getHeight() * Math.max(widthS, heightS)));
		} else
			return icon;
	}

	public static BufferedImage createImage_Cercel(int width, int height, Color color, Color back) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics g = img.getGraphics();
		if (back != null) {
			g.setColor(back);
			g.fillRect(0, 0, width, height);
		}
		g.setColor(color);
		g.fillOval(0, 0, width, height);
		g.dispose();

		return img;
	}

	public static BufferedImage createImage_Random(int width, int height) {
		int[] pixels = new int[width * height];

		BufferedImage img = new BufferedImage(width, height, 1);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		Random rand = new Random();
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = rand.nextInt(0xffffff);

		return img;
	}

	public static BufferedImage createImage_Color(int width, int height, Color red) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics g = img.getGraphics();
		g.fillRect(0, 0, width, height);
		g.dispose();

		return img;
	}

}