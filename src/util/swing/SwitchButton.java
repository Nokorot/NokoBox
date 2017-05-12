package util.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import util.Window;
import util.handelers.ImageHandeler.ScaleType;
import util.math.Maths;

public class SwitchButton extends NBButton {
	private static final long serialVersionUID = 1L;

	public static final int NON = 0;
	public static final int STRING = 1;
	public static final int COLOR = 2;
	public static final int IMAGE = 4;

	private static String[] defaultStrings = {"Off", "On"};
	private static Color[] defaultColors = {Color.LIGHT_GRAY, Color.CYAN};
//	private static BufferedImage defaultIcons;
	
	private int switchType = STRING;
	private List<String> strings = new ArrayList<String>();
	private List<Color> colors = new ArrayList<Color>();
	private List<BufferedImage> images = new ArrayList<BufferedImage>();
	private ScaleType scaleType = ScaleType.NOTSCALE;

	private int size = 0;
	private int active = 0;

	public SwitchButton(Window window, int type, Object... objects) {
		super(window);
		this.switchType = type;

		if (objects.length < 1)
			addDefaultObjects();
		else
			addObjects(objects);
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setActiveIndex(active + 1);
				System.out.println(active);
			}
		});
		
		setActiveIndex(0);
	}
	
	private void addDefaultObjects() {
		if ((this.switchType & STRING) > 0)
			for (String s : defaultStrings)
				strings.add(s);
		if ((this.switchType & COLOR) > 0)
			for (Color c : defaultColors)
				colors.add(c);
		updateSize();
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		update();
	}
	
	public void setSwitchType(int switchType) {
		if(switchType < 0 || this.switchType == switchType)
			return;
		clearButton();
		this.switchType = switchType;
		
		updateSize();
		setActiveIndex(0);
	}

	public void setImageScaleing(ScaleType scaleType) {
		this.scaleType = scaleType;
		update();
	}

	public void setActiveIndex(int active){
		if(size == 0)
			return;
		this.active = active % size;
		update();
	}
	
	public int getActiveIndex() {
		return active;
	}

//	public String getSelectedObject() {
//		return null;
//	}

	private void addObjects(Object... objects) {
		for (Object object : objects) {
			if(object instanceof String)
				strings.add((String) object);
			if(object instanceof Color)
				colors.add((Color) object);
			if(object instanceof BufferedImage)
				images.add((BufferedImage) object);
		}
		updateSize();
		update();
	}
	
	private void updateSize() {
		size = Maths.LCM(strings.size(), colors.size(), images.size());
	}
	
	private void clearButton(){
		setText("");
		setBackground(window.buttonSets.bColor);
		setIcon(null);
	}
	
	private void update(){
		if ((switchType & STRING) > 0)
			setText(strings.get(active % strings.size()));
		if ((switchType & COLOR) > 0)
			setBackground(colors.get(active % colors.size()));
		if ((switchType & IMAGE) > 0)
			setIcon(images.get(active % images.size()), scaleType);
	}
}