package util.swing;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import util.Window;
import util.handelers.ImageHandeler;
import util.swing.gride.BoxObject;

public class ImageList extends JList<ImageIcon> implements BoxObject {
	private static final long serialVersionUID = 1L;

	JScrollPane sp = new JScrollPane();
	
	String code = "";
	
	private DefaultListModel<ImageIcon> listModel;
	
	private List<BufferedImage> images = new ArrayList<BufferedImage>();
	private Dimension imageSize = new Dimension(50, 50);

	
	public ImageList(Window window, Rectangle rec, String code) {
		setModel(listModel = new DefaultListModel<ImageIcon>());
		
		this.code = code;
		
		setFixedCellHeight(60);
		setFixedCellWidth(60);
		
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		
		setVisibleRowCount(-1);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayoutOrientation( JList.HORIZONTAL_WRAP);
		
		if(rec != null)
			setBounds(rec);

		sp.setViewportView(this);
		window.panel.add(sp);
	}

	public ImageList(Window window, String code) {
		this(window, new Rectangle(), code);
	}
	
	public void removeImage(int index){
		listModel.removeElementAt(index);
	}
	
	public void updateImage(int index, BufferedImage image, boolean scale) {
		try {
			if(image == null)
				return;
			if(scale)
				image = ImageHandeler.getScaledImage(image, imageSize.width, imageSize.height);
			listModel.getElementAt(index).setImage(image);
			this.repaint();
		} catch (IOException e) {
		}
	}

	public void addImage(BufferedImage image, boolean scale) {
		try {
			if(image == null)
				return;
			if(scale)
				image = ImageHandeler.getScaledImage(image, imageSize.width, imageSize.height);
			listModel.addElement(new ImageIcon(image));
			images.add(image);
		} catch (IOException e) {
		}
	}

	public void addImage(int index, BufferedImage image, boolean scale) {
		try {
			if(scale)
				image = ImageHandeler.getScaledImage(image, imageSize.width, imageSize.height);
			if(image == null)
				return;
			listModel.add(index, new ImageIcon(image));
			images.add(image);
		} catch (IOException e) {
		}
	}
	
	public void addImages(int index, BufferedImage[] im, boolean scale) {
		for (BufferedImage image1 : im) {
			addImage(index, image1, scale);
		}
	}
	
	public BufferedImage getImage(int i) {
		return images.get(i);
	}

	public void addAllFromAFile(int index, String path) throws IOException {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			System.out.println("check path" + listOfFiles[i]);
			addImage(index, ImageIO.read(listOfFiles[i]), true);
		}
	}
	
	public int getElementCount(){
		return listModel.getSize();
	}
	
	public void setVisible(boolean aFlag) {
		sp.setVisible(aFlag);
//		super.setVisible(aFlag);
	}
	
	public void setBounds(Rectangle r) {
		sp.setBounds(r);
//		super.setBounds(r);
	}

	@Override
	public void setPane(JPanel pane) {
		pane.add(this);
	}

}