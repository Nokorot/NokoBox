package util.jly;

import java.awt.Rectangle;

public class contField {
	public final String name;
	public final String data;

	public contField(String name, String data) {
		this.name = name;
		this.data = data;
	}

	public String toString() {
		return "<" + name + ", " + data + ">";
	}

	public int asInt() {
		return Integer.parseInt(remove(" "));
	}
	
	public int[] asInts() {
		String[] s = remove(" ").split(",");
		int[] i = new int[s.length];
		for (int j = 0; j < s.length; j++)
		try {
				i[j] = Integer.parseInt(s[j]); 
		} catch (Exception e) {
			System.err.println(s[j]);
		}
		return i;
	}

	public double asDouble() {
		return Double.parseDouble(remove(" "));
	}

	public boolean asBoolean() {
		return Boolean.parseBoolean(remove(" "));
	}

	public String asString() {
		return data;
	}
	
	public String remove(String s){
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < data.length(); i++) 
			if(!s.contains(data.charAt(i) + ""))
				builder.append(data.charAt(i));
		return builder.toString();

	}
	
}
