package util.jly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class contObject {

	public final String name;
	public final String data;
	public final contField[] properties;

	public final contObject[] objects;
	public final contField[] fields;

	public contObject(String name, contField[] properties, contObject[] objects, contField[] fields) {
		this.name = name;
		this.properties = properties;
		this.objects = objects;
		this.fields = fields;
		data = null;
	}
	
	public contObject(String name, String data, contField[] properties, contObject[] objects, contField[] fields) {
		this.name = name;
		this.data = data;
		this.properties = properties;
		this.objects = objects;
		this.fields = fields;
	}

	private static contObject read(StringReader reader) {
		ArrayList<contField> properites = new ArrayList<contField>();

		reader.scip("< \t");
		String name = reader.readUntil(",;>");

		if (reader.curent() == ',') {
			reader.next();

			String f = reader.readUntil(">").replace(" ", "");
			String[] fields = f.split(";");
			
//			TODO: f.split("\n");
			
			for (String s : fields) {
				String field[] = s.split(":");
				if (field.length == 1)
					properites.add(new contField("", field[0]));
				else if (field.length == 2)
					properites.add(new contField(field[0], field[1]));
				else
					System.err.println(s);
			}
		}

		reader.scip("> \t");


		// ReadData:
		String data = "";
		if (reader.curent() == '{')
			data = reader.readData();
		else if (reader.curent() != '\n')
			data = reader.readUntil(";\n");

		reader.scip("> \t");
		
		return readData(name, properites.toArray(new contField[properites.size()]), data);

		// return new contObject(name, //
		// properites.toArray(new contField[properites.size()]), //
		// objects.toArray(new contObject[objects.size()]), //
		// fields.toArray(new contField[fields.size()])); //
	}

	protected static contObject readData(final String name, final contField[] properties, final String data) {
		ArrayList<contObject> objects = new ArrayList<contObject>();
		ArrayList<contField> fields = new ArrayList<contField>();

		StringReader reader = new StringReader(data);
		while (reader.next()) {
			
			reader.scip(" \t\n");
			if (reader.curent() == '<')
				objects.add(contObject.read(reader));
			else {
				String s0 = reader.readUntil(":{\n;");
				if(reader.curent() == ':'){
					reader.next();
					String s1 = reader.readUntil("\n;");
					fields.add(new contField(s0, s1));
				}else if(reader.curent() == '{'){
					String s1 = reader.readData();
					fields.add(new contField(s0, s1));
				}else {
					fields.add(new contField("", s0));
				}
					
			}
//			if(reader)
			
//			if ()
			
//			for (String s : fields) {
//				String field[] = s.split(":");
//				if (field.length == 1)
//					properites.add(new contField("", field[0]));
//				else if (field.length == 2)
//					properites.add(new contField(field[0], field[1]));
//				else
//					System.err.println(s);
//			}
			
//			System.out.print(reader.curent());
			
			// System.out.println(reader.curent());
			// break;
		}

		return new contObject(name, data, properties, //   
				objects.toArray(new contObject[objects.size()]), //        
				fields.toArray(new contField[fields.size()])); //          
	}

	public boolean containsO(String string) {
		for (contObject object : objects)
			if (object.name.equals(string))
				return true;
		return false;
	}

	public boolean containsF(String string) {
		for (contField contField : fields)
			if (contField.name.equals(string))
				return true;
		return false;
	}

	public contObject getObject(String string) {
		for (contObject object : objects)
			if (object.name.equals(string))
				return object;
		return null;
	}

	public contObject[] getObjects(String string) {
		List<contObject> list = new ArrayList<contObject>();
		for (contObject object : objects)
			if (object.name.equals(string))
				list.add(object);
		return list.toArray(new contObject[list.size()]);
	}
	
	public contField getF(String string) {
		for (contField contField : fields)
			if (contField.name.equals(string))
				return contField;
		return null;
	}

	public contField getP(String string) {
		for (contField contField : properties){
//			System.out.println("*" + contField.name + "* - *" + string + "*");
			if (contField.name.equals(string))
				return contField;}
		return null;
	}

	public String toString() {
		// TODO:
		return "<" + name + ">{\n\t" + Arrays.toString(objects) + "\n\t" + Arrays.toString(fields) + "\n}\n";
	}
}
