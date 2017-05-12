package util;

import java.util.ArrayList;

public abstract class Stream<T> {

	private ArrayList<T> data = new ArrayList<T>();
	
	protected abstract T next();
	
	public T get(int index){
		while (data.size() <= index)
			data.add(next());
		return data.get(index);
	}
	
	protected ArrayList<T> getData(){
		return data;
	}
	
	public int currentSize(){
		return data.size();
	}
	
	public void clear(){
		data.clear();
	}
	
}
