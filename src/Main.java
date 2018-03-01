import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import Read.Read;
import database.Finger;
import database.Song;
import database.Songfinger;
import database.SongfingerJDBC;
import finprintext.FingerPrint;
import finprintext.ShazamHash;

public class Main {

    public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		System.out.println("请输入要插入数据的音乐所在文件夹路径：");
		String folderPath = reader.nextLine();
		File folder_path = new File(folderPath);
		
		class FileNameFilter1 implements FilenameFilter{
			private Pattern pattern;
			
			public FileNameFilter1(String regex) {
				pattern = Pattern.compile(regex);
			}
			
			@Override
			public boolean accept(File dir, String name) {
				boolean result = pattern.matcher(name).matches();
				return result;
			}
		}
		
		String[] songList = folder_path.list(new FileNameFilter1(".+\\.wav"));    //list数组储存文件夹下所有文件的名字
		
		for(String itemName : songList){
			
	        Read test = new Read();
	        String readpath = folderPath + itemName;

	        String song_name = itemName.replaceAll(".wav","");
	        System.out.println("现在读取："+song_name);
	        
	        double[][] data = test.read(readpath);
	        double[] timeDomain = data[0];
	        
	        FingerPrint fingerprint = new FingerPrint();
	        fingerprint.getFreqDomain(timeDomain);
	        ArrayList<ShazamHash> hashes = new ArrayList<ShazamHash>();
	        hashes = fingerprint.combineHash();
	        
	        SongfingerJDBC jdbc = new SongfingerJDBC();
	        Connection conn = null;
	        
	        try{
	        	conn = jdbc.getConnection();
	        	conn.setAutoCommit(false);
	        	
		        Song song = new Song();
		        song.setSong_name(song_name);
		        
		        int song_id = jdbc.insertSongData(conn, song);
	
		        for(ShazamHash shazamhash : hashes){
		        	Finger finger = new Finger();
		        	finger.setF1(shazamhash.f1);
		        	finger.setF2(shazamhash.f2);
		        	finger.setDt(shazamhash.dt);
		        	int finger_id = jdbc.insertFingerData(conn, finger);
		        	
		        	Songfinger songfinger = new Songfinger();
		        	songfinger.setSong_id(song_id);
		        	songfinger.setFinger_id(finger_id);
		        	songfinger.setOffset(shazamhash.offset);
		        	jdbc.insertSongfingerData(conn, songfinger);
		        }
		        
		        conn.commit();
		        System.out.println("-----数据插入成功！-----");
		        
	        }catch(Exception e){
	        	try{
	        		conn.rollback();
	        		System.out.println("数据回滚");
	        		
	        	}catch(Exception e2){
	        		e2.printStackTrace();
	        	}
	        }
		}
        
		try{
        	 reader.close();
         }catch(Exception e){
        	 e.printStackTrace();
         }
    }
}
