package util.jly;

import util.Window;
import util.handelers.FileHandler;
import util.jly.objects.Body;
import util.jly.objects.Head;

public class JLY {

//	public static final jlyObject[] USE_ALPHA = {new Team(), new Head(), new Body(), new G()};

//	public static void Use(jlyObject[] useobject){
//		jlyObject.object = useobject;
//	}
	
	public static Window BuildWindow(String path){
		return BuildWindowFormData(FileHandler.readFile(path));
	}
	
	public static Window BuildWindowFormData(String jlyData) {
		contObject content = contObject.readData("main", new contField[0], jlyData);
		
		Window window = new Window();
		
		Head.Build(window, content.getObject("head"));
		Body.Build(window, content.getObject("body"));
		
		
		return null;
	}

	public void Build(String properties, String data) {
		// TODO Auto-generated method stub
		
	}

	

}
