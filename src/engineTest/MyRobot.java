package engineTest;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.swing.Label;

public class MyRobot {

	private static Robot robot;

	public static void init() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		Point mouse = MouseInfo.getPointerInfo().getLocation();

		Color c = robot.getPixelColor(mouse.x, mouse.y);
		System.out.println(c);
	}

	private static void press(String s) {
		for (int i = 0; i < s.length(); i++)
			press(s.toUpperCase().charAt(i));

	}

	private static void press(int... i) {
		for (int c : i)
			robot.keyPress(c);
		for (int c : i)
			robot.keyRelease(c);
	}

	// public static getMousePosition

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("DialogDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(150, 150);

		ColorPicker picker = new ColorPicker(frame);

		JButton b = new JButton("Pick");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// picker.setVisible(true);
				picker.pickColor();
				b.setBackground(picker.color);
			}
		});

		frame.add(b);
 
		// Display the window.
		frame.setVisible(true);

	}

	static class ColorPicker extends JDialog {

		private boolean read = true;

		private Robot robot;

		private Color color = Color.RED;

		public Color getColor() {
			return color;
		}

		public ColorPicker(Frame aFrame) {
			super(aFrame, true);
			
			try { robot = new Robot();
			} catch (AWTException e1) {
				e1.printStackTrace();
			}

			setSize(250, 200);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			
			setLayout(null);
			
			JLabel info = new JLabel("Sellect color by bresing \"Space\"");
			info.setHorizontalAlignment(JLabel.CENTER);
			info.setBounds(0, 50, 250, 50);
			add(info);
			
//			getContentPane()

			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						updateColor();
						setVisible(false);
					}
				}
			});
		}

		private void updateColor() {
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			color = robot.getPixelColor(mouse.x, mouse.y);
			getContentPane().setBackground(color);
		}

		public Color pickColor() {

			new Thread(() -> {
				while (read) {
					updateColor();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

			setVisible(true);
			return color;
		}

	}

}
