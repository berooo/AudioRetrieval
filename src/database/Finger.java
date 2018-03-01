package database;

public class Finger {

	private int finger_id;
	private short f1,f2;
	private float dt;

	public int getFinger_id() {
		return finger_id;
	}
	
	public void setFinger_id(int finger_id) {
		this.finger_id = finger_id;
	}
	
	public short getF1() {
		return f1;
	}

	public void setF1(short f1) {
		this.f1 = f1;
	}

	public short getF2() {
		return f2;
	}

	public void setF2(short f2) {
		this.f2 = f2;
	}
	
	public float getDt() {
		return dt;
	}
	
	public void setDt(float dt) {
		this.dt = dt;
	}
	
	@Override
	public String toString() {
		return "Finger [finger_id=" + finger_id + ", f1=" + f1 + ", f2=" + f2 + ", dt=" + dt + "]";
	}
	
}
