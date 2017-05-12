package util.jly;

public class StringReader {

	public final String data;

	private int index = -1;
	private char curent = ' ';
	
	private static final String mathOperatores = "+-*/=^";
	private static final String lineEnders = ";\n";
	private static final String wordEnders = " >,.\\" + mathOperatores;	
	
	public StringReader(String string) {
		data = string;
	}
	
	private void eatSpace(){
		while (data.charAt(index) == ' ')
			index++;
	}
	
	public boolean next() {
		try {
			if (curent == ' ') {
				eatSpace();
				curent = data.charAt(index);
			}
			else
				curent = data.charAt(++index);
			
			if(curent == '/')
				if (data.charAt(index + 1) == '/') 
					readUntil("\n");
				else if(data.charAt(++index) == '*'){
					do
						readUntil("*"); 
					while (data.charAt(++index) != '/');
				}
				
			
		} catch (IndexOutOfBoundsException e) {
			index = data.length();
			return false;
		}
//		if(curent == '\t')
//			return next();
		return true;
	}
	
	public char curent() {
		return curent;
	}

	public int index() {
		return index;
	}
	
	public void GoTo(int index) {
		this.index = index;
		curent = data.charAt(index);
	}
	
//	public String readData(){ 
//		next();
//		int start = index;
//		while(next()){
//			if(curent == '{')
//				readData();
//			if (curent == '}')
//				break;
//		}
//		next();
//		return data.substring(start, index - 1);
//	}
	
	public String readData(){
		StringBuilder build = new StringBuilder();
		while(next()){
			if(curent == '{')
				build.append('{' + readData());
			else if (curent == '}')
				break;
			build.append(curent);
		}
		return build.toString();
	}
	
	public String readUntil(String stopper){
		int start = index;
		while(next() && !stopper.contains(curent + ""));
		return data.substring(start, index);
	}

	public void scip(String string) {
		while (string.contains(curent + "") && next());
	} 
	
	
}
