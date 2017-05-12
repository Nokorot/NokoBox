package util.math;

public class Interval {

	public final int begin;
	public final int end;

	public Interval(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + begin;
        result = prime * result + end;
        return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Interval other = (Interval) obj;
		return begin == other.begin && end == other.end;
	}

	public boolean contains(int position) {
		return begin <= position && position < end;
	}

	public int size() {
		return end - begin;
	}
	
	@Override
	public String toString() {
		return "[" + begin + ", " + end + "]";
	}

}
