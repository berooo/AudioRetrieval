package database;

public class Songfinger {

	private int song_id;
	private int finger_id;
	private int offset;
	
	public int getSong_id() {
		return song_id;
	}
	
	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}
	
	public int getFinger_id() {
		return finger_id;
	}
	
	public void setFinger_id(int finger_id) {
		this.finger_id = finger_id;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	@Override
	public String toString() {
		return "Songfinger [song_id=" + song_id + ", finger_id=" + finger_id + ", offset=" + offset + "]";
	}
}
