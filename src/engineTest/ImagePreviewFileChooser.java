package engineTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

import util.handelers.ImageHandeler;

public class ImagePreviewFileChooser extends JFileChooser implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;

	private static final Dimension MinSize = new Dimension(250, 250);

	private static final int TextBoxHeight = 20;
	private static final Font TextFont = new Font("Areal", Font.BOLD, 15);

	private ImagePreviewPanel previewPanel = new ImagePreviewPanel(this);
	private ImageFileFilter fileFilter = new ImageFileFilter();
	private ImageFileView fileView = new ImageFileView();

	public ImagePreviewFileChooser() {
		super();
		init();
	}

	public ImagePreviewFileChooser(String currentDirectoryPath) {
		super(currentDirectoryPath);
		init();
	}

	private void init() {
		addPropertyChangeListener((PropertyChangeEvent evt) -> propertyChange(evt));
		setFileFilter(fileFilter);
		setAccessory(previewPanel);
		setFileView(fileView);
		setMinimumSize(MinSize);
	}

	public void setPreviewPanelSize(int width, int height) {
//		previewPanel.setSize(new Dimension(width, height));
//		System.out.println(width);
//		previewPanel.setW
		previewPanel.setPreferredSize(new Dimension(width, height));
	}

	public void setPreviewPanelWidth(int width) {
		setPreviewPanelSize(width, 100);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		File file = this.getSelectedFile();

		if (file == null)
			return;

		previewPanel.updateImage(file);
	}

	@SuppressWarnings("serial")
	private static class ImagePreviewPanel extends JPanel {
		private BufferedImage img;

		private Point mouse = new Point(0, 0);
		private boolean changeSize = false;
		private int width = 500;
		
		public ImagePreviewPanel(ImagePreviewFileChooser parent) {
			super();
			setPreferredSize(new Dimension(200, 200));
			
			addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					changeSize = false;
				}
				
				public void mousePressed(MouseEvent e) {
//					System.out.println(mouse);
					if (mouse.x < 5)
						changeSize = true;
						
				}
				
			});
			addMouseMotionListener(new MouseMotionListener() {
				public void mouseMoved(MouseEvent e) {
					mouse.setLocation(e.getPoint());
				}
				
				public void mouseDragged(MouseEvent e) {
					mouse.setLocation(e.getPoint());
					if (changeSize){
//						System.out.println(mouse.x);
//						parent.setPreviewPanelWidth(width - mouse.x);
//						setSize(500, width - mouse.x);
//						setMinimumSize(new Dimension(width - mouse.x, 100));
					}
						
				}
			});
		}

		private void updateImage(File file) {
			try {
				img = ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			repaint();
		}

		public void paintComponent(Graphics g) {

			int width = getWidth();
			int height = getHeight() - TextBoxHeight;

			// filling background
			g.setColor(Color.gray);
			g.fillRect(0, 0, width, height);

			boolean red;
			String text = "";

			if (img != null) {
				int w = img.getWidth(null);
				int h = img.getHeight(null);

				// drawing image
				if ((float) w / h > (float) width / height) {
					int ha = width * h / w;
					g.drawImage(img, 0, (int) (height / 2.0 - ha / 2.0), width, ha, null);
				} else {
					int wa = height * w / h;
					g.drawImage(img, (int) (width / 2.0 - wa / 2.0), 0, wa, height, null);
				}

				text = w + " x " + h;
			} else {
				red = true;
				text = "No image";
			}

			// drawing TextBox;
			int xp = 10;
			int yp = getHeight() - 5;

			g.fillRect(0, height, getWidth(), TextBoxHeight);

			g.setFont(TextFont);
			g.setColor(Color.black);
			g.drawString(text, xp + 1, yp + 1);
			g.setColor(Color.white);
			g.drawString(text, xp, yp);

		}
	};

	private static class ImageFileFilter extends FileFilter {

		public String getDescription() {
			return "ImageFiles";
		}

		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;

			String extension = FileUtils.getExtension(f);
			if (extension != null) {
				if (extension.equals(FileUtils.tiff) //
						|| extension.equals(FileUtils.tif) //
						|| extension.equals(FileUtils.gif) //
						|| extension.equals(FileUtils.jpeg) //
						|| extension.equals(FileUtils.jpg) //
						|| extension.equals(FileUtils.png)) {
					return true;
				} else {
					return false;
				}
			}

			return false;
		}
	};

	private static class ImageFileView extends FileView {
		
		private static final int iconSize = 50;
		
		public static BufferedImage jpgIcon = ImageHandeler.createImage_Cercel((int) (iconSize * 1.5), iconSize, Color.CYAN, null);
		public static BufferedImage gifIcon = ImageHandeler.createImage_Cercel((int) (iconSize * 1.5), iconSize, Color.RED, null);
		public static BufferedImage tifIcon = ImageHandeler.createImage_Cercel((int) (iconSize * 1.5), iconSize, Color.BLUE, null);
		public static BufferedImage pngIcon = ImageHandeler.createImage_Cercel((int) (iconSize * 1.5), iconSize, Color.GREEN, null);

		private static Map<String, Image> icons = new HashMap<>();

		private BufferedImage defaultIcon(File f) {
			switch (FileUtils.getExtension(f)) {
			case FileUtils.jpeg:
			case FileUtils.jpg: return jpgIcon;
			case FileUtils.gif: return gifIcon;
			case FileUtils.tiff:
			case FileUtils.tif: return tifIcon;
			case FileUtils.png: return pngIcon;
			}
			return new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		}
		
		public Icon getIcon(File f) {

			if (f.isDirectory())
				return null;

			String path = f.getAbsolutePath();

			if (icons.containsKey(path)) {
//				System.out.println(path);
				return new ImageIcon(icons.get(path));
			}

			BufferedImage image = new BufferedImage((int) (iconSize * 1.5), iconSize, BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();

			int wB = image.getWidth();
			int hB = image.getHeight();
			g.drawImage(defaultIcon(f), 0, 0, wB, hB, null);

			new Thread(() -> {

				// TODO: make sure that not all icons are loaded simultaneously.
				// TODO: and remove them wenn the file is closed

				try {
					Image img = new ImageIcon(f.getPath()).getImage();

					int wA = img.getWidth(null);
					int hA = img.getHeight(null);

					float aspA = (float) wA / hA;
					float aspB = (float) wB / hB;
					
					int x, y, width, height;
					if (aspA > aspB) {
						width = wB;
						height = (int) (width / aspA);
						x = 0;
						y = (hB - height) / 2; 
					}else{
						height = hB;
						width = (int) (height * aspA);
						x = (wB - width) / 2;
						y = 0;
					}

					//defaultIcon(f);
					
					g.drawImage(img, x, y, width, height, null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();

			icons.put(path, image);
			return new ImageIcon(image);

		}

		public String getTypeDescription(File f) {
			String extension = FileUtils.getExtension(f);
			String type = null;

			if (extension != null) {
				if (extension.equals(FileUtils.jpeg) || extension.equals(FileUtils.jpg)) {
					type = "JPEG Image";
				} else if (extension.equals(FileUtils.gif)) {
					type = "GIF Image";
				} else if (extension.equals(FileUtils.tiff) || extension.equals(FileUtils.tif)) {
					type = "TIFF Image";
				} else if (extension.equals(FileUtils.png)) {
					type = "PNG Image";
				}
			}
			return type;
		}
		
		public String getName(File f) {
			return super.getName(f);
		}
		
	}

	public static class FileUtils {

		public final static String jpeg = "jpeg";
		public final static String jpg = "jpg";
		public final static String gif = "gif";
		public final static String tiff = "tiff";
		public final static String tif = "tif";
		public final static String png = "png";

		/*
		 * Get the extension of a file.
		 */
		public static String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 && i < s.length() - 1) {
				ext = s.substring(i + 1).toLowerCase();
			}
			return ext;
		}

		public static enum type {

			jpeg(new String[] { "jpeg" }), jpg(new String[] { "jpg" }), gif(new String[] { "gif" }), tiff(
					new String[] { "tiff" }), tif(new String[] { "tif" }), png(new String[] { "png" });

			private String[] ectension;

			private type(String[] extension) {
				ectension = extension;
			}
		}

	}
}
