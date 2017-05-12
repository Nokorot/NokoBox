package util;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

import util.adds.UpdateAdd;
import util.swing.NBButton;
import util.swing.ButtonSetings;
import util.swing.LabelSetings;
import util.swing.PopDownTextField;
import util.swing.TextArea;
import util.swing.TextAreaSetings;
import util.swing.TextField;
import util.swing.TextFieldSetings;
import util.swing.TextList;
import util.swing.gride.Box;
import util.swing.gride.BoxGrid;
import util.swing.listeners.CloseListener;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final int TOP = 2 << 0;
	public static final int BOTTOM = 2 << 1;
	public static final int LEFT = 2 << 2;
	public static final int RIGHT = 2 << 3;
	
	private UpdateAdd upAdd;
	private List<UpdateAdd> upAdds = new ArrayList<UpdateAdd>();

	private List<CloseListener> closeListeners = new ArrayList<CloseListener>();

	public final Platform platform;
	public final JPanel panel;

	public ButtonSetings buttonSets;
	public TextFieldSetings textfeldSets;
	public LabelSetings labelSets;
	public TextAreaSetings textareaSets;
	
	protected boolean running;
	public boolean[] keys = new boolean[68300];
	protected Point wMouse;
	protected Point sMouse;

	private Box frameBox;
	
	public Window() {
		platform = Platform.staticInstance;
		if (platform == null) {
			System.err.println("Sorry, but your platform does not exist!");
			System.exit(1);
		}
		panel = (JPanel) getContentPane();

		DefaultWindowsSetings.setSetings(this);
		
		setSetings();
		addListaners();
		addUpdaters();
	}
	
	/**
	 * 
	 * @param platform
	 * @param title
	 * @param width
	 * @param height
	 */
	public Window(String title, int width, int height) {
		platform = Platform.staticInstance;
		if (platform == null) {
			System.err.println("Sorry, but your platform does not exist!");
			System.exit(1);
		}
		First();

		setTitle(title);
		setSize(width, height);
		DefaultWindowsSetings.setSetings(this);

		panel = (JPanel) getContentPane();
		setSetings();
		addListaners();
		addUpdaters();
		
//		setLocation(10000, 10000);
//		setVisible(true);
		setLocationRelativeTo(null);
		Init();
//		setVisible(visible);
	}
	
	private void setSetings() {
		this.buttonSets = new ButtonSetings(this);
		this.textfeldSets = new TextFieldSetings(this);
		this.labelSets = new LabelSetings(this);
		this.textareaSets = new TextAreaSetings(this);
	}
	
	private void addListaners() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window.this.onCloseing();
			}

			public void windowOpened(WindowEvent paramWindowEvent) {
				Window.this.onOpened();
			}

		});
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				Window.this.MouseWheelRotation(e.getWheelRotation());
			}

		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				Window.this.wMouse = e.getPoint();
				Window.this.sMouse = e.getLocationOnScreen();
			}

			public void mouseMoved(MouseEvent e) {
				Window.this.wMouse = e.getPoint();
				Window.this.sMouse = e.getLocationOnScreen();
			}

		});
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				Window.this.MousePresed = true;
				Window.this.MouseClick(e.getButton());
			}

			public void mouseReleased(MouseEvent e) {
				Window.this.MousePresed = false;
				Window.this.MouseReleas(e.getButton());
			}

			public void mouseEntered(MouseEvent e) {
				Window.this.MouseEntered();
				Window.this.MouseInside = true;
			}

			public void mouseExited(MouseEvent e) {
				Window.this.MouseExited();
				Window.this.MouseInside = false;
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				Window.this.keys[e.getKeyCode()] = true;
				Window.this.KeyPressed(e.getKeyCode());
			}

			public void keyReleased(KeyEvent e) {
				Window.this.keys[e.getKeyCode()] = false;
				Window.this.KeyReleased(e.getKeyCode());
			}

			public void keyTyped(KeyEvent e) {
			}
		});
	}

	private void addUpdaters() {
		platform.addWindow(this, this.upAdd = new UpdateAdd() {
			public void mUpdate() {
				Window.this.Update();
				Window.this.update();
				try {
					for (UpdateAdd u : Window.this.upAdds) {
						u.mUpdate();
					}
				} catch (Exception e) {
				}
			}

			public void mUpdate1() {
				Window.this.Update1();
				try {
					for (UpdateAdd u : Window.this.upAdds) {
						u.mUpdate1();
					}
				} catch (Exception e) {
				}
			}

		});
	}
	
	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
	}
	
	public Box getFrameBox() {
		if(frameBox != null)
			return frameBox;
		frameBox = new Box(0, 0, getPlaneWidth(), getPlaneHeight());
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				frameBox.setBounds(0, 0, getPlaneWidth(), getPlaneHeight());
			}
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				super.componentShown(e);
			}
		});
		return frameBox;
	}
	
	public BoxGrid getGrid(){
		return getFrameBox().getInsideGrid();
	}

	public BoxGrid getGrid(double[] X, double[] Y) {
		BoxGrid grid = getGrid();

		grid.set(X, Y);
		grid.setExp(X, Y);
		
		return grid;
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

	protected void First() {
	}

	public void Init() {
		BoxGrid grid = this.getGrid(1, new double[]{1, 1, 3});

		NBButton b;
		TextField tf;
		TextArea ta;
		
		b = new NBButton(this, "Button");
		tf = new TextField(this);
		tf.setHorizontalAlignment(0);
		tf.setText("TextField");
		ta = new TextArea(this, true);
		ta.setText("TextArea");
		
		grid.getBox(0).setComponent(b);
		grid.getBox(1).setComponent(tf);
		grid.getBox(2).setComponent(ta);
		
	/*
		int x = getWidth() / 10;
		int y = getHeight() / 10;
		Grid g1 = new Grid(new Rectangle(x, y, getWidth() - 2 * x, getHeight() - 2 * y), 1, 5, x / 2, false);

		new Button(this, "Button", g1.getRec(0));
		TextField tf = new TextField(this, g1.getRec(1));
		tf.setHorizontalAlignment(0);
		tf.setText("TextField");
		TextArea ta = new TextArea(this, g1.getRec(0, 2, 0, 4), true);
		ta.setText("TextArea");*/
	}

	protected boolean MousePresed;
	protected boolean MouseInside;
	long lastTime = System.nanoTime();

	private void update() {
		if (!this.MouseInside) {
			this.wMouse = new Point(-100, -100);
		}
	}

	public void Update() {}
	public void Update1() {}

	protected void onCloseing() {}
	protected void onOpened() {}
	
	protected void MouseClick(int b) {}
	protected void MouseReleas(int b) {}
	protected void MouseWheelRotation(int r) {}
	
	protected void KeyPressed(int key) {}
	protected void KeyReleased(int key) {}

	public void ButtonAction(NBButton button) { System.out.println(button); }
	public void TextFieldAction(TextField field) {}
	public void PopDownTextFieldAction(PopDownTextField source) {}
	public void TextListAction(TextList source) {}
	public void SliderChange(JSlider slider) { }

	
	public void MouseExited() {}
	public void MouseEntered() {}

	public Point getLocation() {
		Point p =  super.getLocation();
		p.translate(getContentPane().getX(), getContentPane().getY());
		return p;
	}

	public void setWidth(int width) {
		setSize(width, getHeight());
	}

	public void setHeight(int height) {
		setSize(getWidth(), height);
	}

	public int getPlaneWidth() {
		return getContentPane().getWidth();
	}

	public int getPlaneHeight() {
		return panel.getHeight();
	}

	public UpdateAdd getUpAdd() {
		return this.upAdd;
	}

	public List<UpdateAdd> getUpAdds() {
		return this.upAdds;
	}

	public void dispose() {
//		onCloseing();
		for (CloseListener closeListener : closeListeners)
			closeListener.onClose();
		platform.removeWindows(this);
		this.upAdds.retainAll(this.upAdds);
		super.dispose();
	}

	public void packCanv() {
		int width = getWidth();
		int height = getHeight();

		super.pack();
		setSize(width, height);
	}

	public void addUpdate(UpdateAdd upAdd) {
		this.upAdds.add(upAdd);
	}
	
	public void setLocationRelativeTo(Window window, int side) {
		int xOff = 0, yOff = 0;
		
		if((side & LEFT) > 0)
			xOff = -this.getWidth();
		if((side & RIGHT) > 0)
			xOff = window.getWidth();
		if ((side & TOP) > 0)
			yOff = -this.getHeight();
		if((side & BOTTOM) > 0)
			yOff = window.getHeight();
		
		setLocation(window.getX() + xOff, window.getY() + yOff);
	}

	public Image[] ListToArrayImage(List<Image> list) {
		Image[] arr = new Image[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = ((Image) list.get(i));
		}
		return arr;
	}

}