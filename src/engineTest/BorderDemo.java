package engineTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;

/*
 * BorderDemo.java requires the following file:
 *    images/wavy.gif
 */
public class BorderDemo extends JPanel {
	public BorderDemo() {
		super(new GridLayout(1, 0));

		Border paneEdge = BorderFactory.createEmptyBorder(0, 10, 10, 10);

		// First pane: simple borders
		JPanel simpleBorders = new JPanel();
		simpleBorders.setBorder(paneEdge);
		simpleBorders.setLayout(new BoxLayout(simpleBorders, BoxLayout.Y_AXIS));


		// Second pane: matte borders
		JPanel matteBorders = new JPanel();
		matteBorders.setBorder(paneEdge);
		matteBorders.setLayout(new BoxLayout(matteBorders, BoxLayout.Y_AXIS));


		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Simple", null, simpleBorders, null);
		tabbedPane.addTab("Matte", null, matteBorders, null);
		tabbedPane.setSelectedIndex(0);
		String toolTip = new String(
				"<html>Blue Wavy Line border art crew:<br>&nbsp;&nbsp;&nbsp;Bill Pauley<br>&nbsp;&nbsp;&nbsp;Cris St. Aubyn<br>&nbsp;&nbsp;&nbsp;Ben Wronsky<br>&nbsp;&nbsp;&nbsp;Nathan Walrath<br>&nbsp;&nbsp;&nbsp;Tommy Adams, special consultant</html>");
		tabbedPane.setToolTipTextAt(1, toolTip);

		add(tabbedPane);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = BorderDemo.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("BorderDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		BorderDemo newContentPane = new BorderDemo();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
