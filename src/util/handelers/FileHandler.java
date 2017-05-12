package util.handelers;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
	public static Scanner scan;
	private static List<Scanner> scans = new ArrayList<Scanner>();
	public static Formatter x;

	public FileHandler() {
	}

	public static Formatter createFile(File file) {
		try {
			return FileHandler.x = new Formatter(file);
		} catch (Exception e) {
			System.out.println("can't create the file");
		}
		return null;
	}
	
	public static void createPath(String path){
		createPath(Paths.get(path));
	}
	
	public static void createPath(Path p){
		try{
			if (Files.exists(p)) {
				return;
			}
			if (!Files.exists(p.getParent())) {
				createPath(p.getParent());
			}
			System.out.println("creates dir: \"" + p + "\"");
			Files.createDirectory(p);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void closeFile() {
		x.close();
	}

	public static int loadFile(File file) throws Exception {
		Scanner scan = new Scanner(file);
		scans.add(scan);

		return scans.size() - 1;
	}

	public static Scanner scan(int i) {
		return (Scanner) scans.get(i);
	}

	
	public static String readLocalFile(String path){
		ClassLoader classLoader = FileHandler.class.getClassLoader();
		File file = new File(classLoader.getResource(path).getFile());
		return readFile(file);
	}

	public static String readFile(String path) {
		if(path == null)
			return null;
		return readFile(new File(path));
	}
	
	@SuppressWarnings("resource")
	public static String readFile(File file) {
		if(file == null || !file.exists())
			return null;
		
		Scanner scan;
		try {
			scan = new Scanner(file);
		
			StringBuffer builder = new StringBuffer();
			while (scan.hasNextLine()) 
				builder.append(scan.nextLine() + "\n");
			return builder.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}