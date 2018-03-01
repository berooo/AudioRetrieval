package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SongfingerJDBC {
    
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Songfinger", "root", "");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
    
    //测试: 查询并输出当前时间
    public void getNowTime(Connection conn) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("select now();");
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            System.out.println("现在时间:"+rs.getString("now()"));
        }
    }
	
	//对song表的CRUD操作
	
	public int insertSongData(Connection conn, Song song) throws SQLException {
		String insertSql = "INSERT INTO song (song_name) VALUES (?)";
		PreparedStatement ps = conn.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, song.getSong_name());
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();  
		int song_id = 0;    //保存生成的ID  
		if (rs != null&&rs.next()) {  
			song_id = rs.getInt(1);  
		} 
		return song_id;
	}
	
	public void updateSongData(Connection conn, int song_id, Song song) throws SQLException {
		String updateSql = "UPDATE song SET song_name = ? WHERE song_id = ?"; 
		PreparedStatement ps = conn.prepareStatement(updateSql);
		ps.setString(1, song.getSong_name());
		ps.setInt(2, song.getSong_id());
		ps.execute();
	}
	
	public void deleteSongData(Connection conn, Song song) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM song WHERE song_id = ?");
		ps.setInt(1, song.getSong_id());
        ps.execute();
	}
	
	public void searchSongData(Connection conn, Song song) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM song WHERE song_name = ?");
		ps.setString(1, song.getSong_name());
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			System.out.print(rs.getInt("song_id")+" ");
			System.out.print(rs.getString("song_name")+" ");
			System.out.println();
		}
	}
	
	//对finger表的CRUD操作
	
	public int insertFingerData(Connection conn, Finger finger) throws SQLException {
		String insertSql = "INSERT INTO finger(f1,f2,dt) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
		ps.setShort(1, finger.getF1());
		ps.setShort(2, finger.getF2());
		ps.setFloat(3, finger.getDt());
		ps.execute();
		ResultSet rs = ps.getGeneratedKeys();  
		int finger_id = 0;    //保存生成的ID  
		if (rs != null&&rs.next()) {  
			finger_id = rs.getInt(1);  
		} 
		return finger_id;
	}
	
	public void updateFingerData(Connection conn, int finger_id, Finger finger) throws SQLException {
		String updateSql = "UPDATE finger SET f1 = ?,f2 = ?,dt = ? WHERE finger_id = ?"; 
		PreparedStatement ps = conn.prepareStatement(updateSql);
		ps.setShort(1, finger.getF1());
		ps.setShort(2, finger.getF2());
		ps.setFloat(3, finger.getDt());
		ps.setInt(4, finger.getFinger_id());
		ps.execute();
	}
	
	public void deleteFingerData(Connection conn, Finger finger) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM finger WHERE finger_id = ?");
		ps.setInt(1, finger.getFinger_id());
        ps.execute();
	}
	
	public void searchFingerData(Connection conn, Finger finger) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM finger WHERE f1 = ? AND f2 = ? AND dt = ?");
		ps.setShort(1, finger.getF1());
		ps.setShort(2, finger.getF2());
		ps.setFloat(3, finger.getDt());
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			System.out.print(rs.getInt("finger_id")+" ");
			System.out.print(rs.getShort("f1")+" ");
			System.out.print(rs.getShort("f2")+" ");
			System.out.print(rs.getFloat("dt")+" ");
			System.out.println();
		}
	}
	
	//对songfinger表的CRUD操作
	
	public void insertSongfingerData(Connection conn, Songfinger songfinger) throws SQLException {
		String insertSql = "INSERT INTO songfinger(song_id,finger_id,offset) VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(insertSql);
		ps.setInt(1, songfinger.getSong_id());
		ps.setInt(2, songfinger.getFinger_id());
		ps.setInt(3, songfinger.getOffset());
		ps.execute();
		
	}
	
	public void updateSongfingerData(Connection conn, int song_id, int finger_id, Songfinger songfinger) throws SQLException {
		String updateSql = "UPDATE songfinger SET offset = ? WHERE song_id = ? AND finger_id = ?"; 
		PreparedStatement ps = conn.prepareStatement(updateSql);
		ps.setInt(1, songfinger.getOffset());
		ps.setInt(2, songfinger.getSong_id());
		ps.setInt(3, songfinger.getFinger_id());
		ps.execute();
	}
	
	public void deleteSongfingerData(Connection conn, Songfinger songfinger) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM songfinger WHERE song_id = ? AND finger_id = ?");
		ps.setInt(1, songfinger.getSong_id());
		ps.setInt(2, songfinger.getFinger_id());
        ps.execute();
	}
	
	public void searchSongfingerData(Connection conn, Songfinger songfinger) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM songfinger WHERE song_id = ? AND finger_id = ?");
		ps.setInt(1, songfinger.getSong_id());
		ps.setInt(2, songfinger.getFinger_id());
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			System.out.print(rs.getInt("song_id")+" ");
			System.out.print(rs.getInt("finger_id")+" ");
			System.out.print(rs.getInt("offset")+" ");
			System.out.println();
		}
	}
	
}
