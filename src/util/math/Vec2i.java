package util.math;

public class Vec2i implements Comparable<Vec2i> {
	private static boolean sortY = false;

	public int x;
	public int y;
	public static Vec2i empty = new Vec2i(0, 0);

	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vec2i() {
		this(0, 0);
	}

	public String toString() {
		return "[X = " + this.x + ", Y = " + this.y + "]";
	}

	public int compareTo(Vec2i o) {
		if (sortY) {
			return (int) (o.y - this.y);
		}
		return (int) (o.x - this.x);
	}

	public static void sortX() {
		sortY = false;
	}

	public static void sortY() {
		sortY = true;
	}

	public Vec2i times(int k) {
		return new Vec2i(this.x * k, this.y * k);
	}

}