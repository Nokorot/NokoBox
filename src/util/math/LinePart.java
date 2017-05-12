package util.math;

public class LinePart {
	private Vec2f v0;
	private Vec2f v1;
	private float a;
	private float b;

	public LinePart(Vec2f l_v0, Vec2f l_v1) {
		this.v0 = (l_v0.x < l_v1.x ? l_v0 : l_v1);
		this.v1 = (l_v0.x > l_v1.x ? l_v0 : l_v1);

		this.a = ((this.v1.y - this.v0.y) / (this.v1.x - this.v0.x));
		this.b = (this.v0.y - this.a * this.v0.x);
	}

	public float getY(float x) {
		return this.a * x + this.b;
	}

	public Vec2f getV0() {
		return this.v0;
	}

	public Vec2f getV1() {
		return this.v1;
	}

	public double getA() {
		return this.a;
	}

	public double getB() {
		return this.b;
	}

	public void setV0(Vec2f l_v0) {
		update(l_v0, this.v1);
	}

	public void setV1(Vec2f l_v1) {
		update(this.v0, l_v1);
	}

	public void setA(float a) {
		this.a = a;
		this.v0.y = getY(this.v0.x);
		this.v1.y = getY(this.v1.x);
	}

	public void setB(float b) {
		this.b = b;
		this.v0.y = getY(this.v0.x);
		this.v1.y = getY(this.v1.x);
	}

	private void update(Vec2f l_v0, Vec2f l_v1) {
		this.v0 = (l_v0.x < l_v1.x ? l_v0 : l_v1);
		this.v1 = (l_v0.x > l_v1.x ? l_v0 : l_v1);

		this.a = ((this.v1.y - this.v0.y) / (this.v1.x - this.v0.x));
		this.b = (this.v0.y - this.a * this.v0.x);
	}

	public String toString() {
		return "[v0=" + this.v0 + ", v1=" + this.v1 + ", a=" + this.a + ", b=" + this.b + "]";
	}

	public boolean isVertical() {
		return this.v0.x == this.v1.x;
	}

}