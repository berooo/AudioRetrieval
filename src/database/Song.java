package database;

public class Song {

	private int song_id;
	private String song_name;
	
	public int getSong_id() {
		return song_id;
	}
	
	public void setSong_id(int song_id) {
		this.song_id = song_id;
	}
	
	public String getSong_name() {
		return song_name;
	}
	
	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}
	
	@Override
	public String toString() {
		return "Song [song_name=" + song_name + ", song_id=" + song_id + "]";
	}
	
}
