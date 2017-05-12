package util;

public class prints {
	public static final int COM = 0;
	
	public static void print_(int i) {
		if (!util.Platform.print)
			return;
		System.out.print(i + ",\t");
	}

	public static void print(int i) {
		if (!util.Platform.print)
			return;
		System.out.println(i);
	}

	public static void print(boolean b) {
		if (!util.Platform.print)
			return;
		System.out.println(b);
	}

	public static void print_(boolean b) {
		if (!util.Platform.print)
			return;
		System.out.print(b + ",\t");
	}

	public static void print(float f) {
		if (!util.Platform.print)
			return;
		System.out.println(f);
	}

	public static void print_(float f) {
		if (!util.Platform.print)
			return;
		System.out.print(f + ",\t");
	}

	public static void print_(Object o) {
		if (!util.Platform.print)
			return;
		if (o == null) {
			print_("Null");
			return;
		}
		System.out.print(o.toString() + ",\t");
	}

	public static void print(String... s) {
		if (!util.Platform.print)
			return;
		for (String s1 : s) {
			System.out.println(s1 + ", ");
		}
		System.out.print("\n");
	}

	public static void print(Integer... i) {
		if (!util.Platform.print)
			return;
		Integer[] arrayOfInteger;
		int j = (arrayOfInteger = i).length;
		for (int i1 = 0; i1 < j; i1++) {
			int i2 = arrayOfInteger[i1].intValue();
			System.out.print(i2 + ", ");
		}
		System.out.print("\n");
	}

	public static void print(Object... o) {
		if (!util.Platform.print)
			return;
		for (Object o1 : o) {
			if (o1 == null) {
				print(new String[] { "Null" });
				return;
			}
			System.out.print(o1.getClass().getSimpleName() + ": " + o1.toString() + ", \t");
		}
		System.out.print("\n");
	}

}