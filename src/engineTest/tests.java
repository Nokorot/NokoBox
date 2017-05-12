package engineTest;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFileChooser;

public class tests {
	
	public tests() {
//		test1();
		
//		JFileChooser chooser = new JFileChooser();
//		new ImagePreview(chooser);
//		chooser.showOpenDialog(null);
		
		test2();
		
	}
	
	private void test1() {
		FileDialog dialog = new FileDialog((Frame) null){
			
		};
		dialog.setFilenameFilter(new FilenameFilter() {
			
			private String[] accsepted = {".png", ".jpg"};
			
			@Override
			public boolean accept(File dir, String name) {
				for (String sufix : accsepted)
					if (name.endsWith(sufix))
						return true;
				return false;
			}
		});
		dialog.setDirectory("/home/nokorot/Pictures/");
		dialog.setLocationRelativeTo(null);
//		dialog.
		dialog.setVisible(true);
	}
	
	private void test2() {
		ImagePreviewFileChooser fileChooser = new ImagePreviewFileChooser();
		fileChooser.setPreferredSize(new Dimension(800, 500));
		fileChooser.setPreviewPanelWidth(500);
		fileChooser.showOpenDialog(null);
		File file = fileChooser.getSelectedFile();
//		if (file != null && file.exists()){
//			ImageElement.addImage(file, true, true);
//			lastLocation = file.getParent();
//		}
	}
	
	public static void main(String[] args) {
		new tests();
	}

}
