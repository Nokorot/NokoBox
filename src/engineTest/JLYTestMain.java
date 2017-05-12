package engineTest;

import java.io.File;

import util.Platform;
import util.handelers.FileHandler;
import util.jly.JLY;

public class JLYTestMain {

	private static String test() {
		return "<team, blue>{" + "\n	Panel: grey" + "\n	TFeald{ colour: light_grey; Font: Areal }"
				+ "\n	Button{ colour: light_grey; Font: Areal }" + "\n	G{ space: 5 }" + "\n}"
				+ "\n<head>{" + "\n	title: \"PointSystem np\"" + "\n	resizeable: true"
				+ "\n	size: 600, 350"
				// + "\n width: 800 "
				// + "\n height: 600 "
				+ "\n	background: grey" + "\n}" + "\n<body>{" + "\n	<G>{" + "\n		<Y>{"
				+ "\n		<TFeald, Name1; w:2>" + "\n		<TFeald, Points1>"
				+ "\n		<Button, Fonts> Fonts" + "\n		}" + "\n		<Y>{"
				+ "\n		<TFeald, Name2; w:2>" + "\n		<TFeald, Points2>"
				+ "\n		<Button, Team> Team" + "\n		}" + "\n		<Y, w:1>{"
				+ "\n		<Button, Play; w:3> Play" + "\n		<Button, Settings> Settings" + "\n		}"
				+ "\n	}" + "\n}";
	}

	private static String read() {
		return FileHandler.readFile("/home/noko/Programing/jLayout/MainMenu.jly");
	}

	private static void testJLY() {
		// JLY.Use(JLY.USE_ALPHA);
		Platform.staticInstance.start();

		new JLY().BuildWindowFormData(read());
	}

	public static void main(String[] args) {
		File folder = new File("/home/noko/Programing/jLayout");
		File[] listOfFiles = folder.listFiles();

		
		
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
	}

}
