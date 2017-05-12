package util.math;

public class Vec3f {
	public float x;
	public float y;
	public float z;
	public static Vec3f empty = new Vec3f(0.0F, 0.0F, 0.0F);

	public Vec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

}