package MatchAndScore;

import database.SongfingerJDBC;
import finprintext.ShazamHash;

import java.io.DataOutput;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Match {

    public void test(){
        SongfingerJDBC songfingerJDBC = new SongfingerJDBC();
        String sql = "SELECT NOW() FROM song";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = songfingerJDBC.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Timestamp d = resultSet.getTimestamp("NOW()");
            System.out.println(d.toString());
           // DataOutput dataOutput = resultSet.getMeData();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(resultSet != null)
                    resultSet.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(preparedStatement != null)
                    preparedStatement.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(connection != null)
                    connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public ArrayList<MatchedFinger> matchHashes(ArrayList<ShazamHash> hashesOfSample){

        SongfingerJDBC songfingerJDBC = new SongfingerJDBC();
        String sql = "select * from finger inner join songfinger using (finger_id) where f1 = ? and f2 =? and dt = ?";
        Connection connection = null;
        ArrayList<MatchedFinger> matchedList = new ArrayList<MatchedFinger>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = songfingerJDBC.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for(int i = 0;i<hashesOfSample.size();i++){

                preparedStatement.setInt(1,hashesOfSample.get(i).f1);
                preparedStatement.setInt(2,hashesOfSample.get(i).f2);
                preparedStatement.setInt(3,hashesOfSample.get(i).dt);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){

                    MatchedFinger matchedFinger = new MatchedFinger();
                    matchedFinger.finger_id = resultSet.getInt("finger_id");
                    matchedFinger.song_id = resultSet.getInt("song_id");
                    matchedFinger.dOffset = resultSet.getInt("offset") - hashesOfSample.get(i).offset;
                    matchedList.add(matchedFinger);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(resultSet != null)
                    resultSet.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(preparedStatement != null)
                    preparedStatement.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(connection != null)
                    connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return matchedList;
    }

    public ArrayList<ArrayList<MatchedFinger>> classifyBySong_id(ArrayList<MatchedFinger> matchedHashes){

        ArrayList<ArrayList<MatchedFinger>> classifiedBySong_id = new ArrayList<ArrayList<MatchedFinger>>();
        for (int i = 0;i<5;i++){
            ArrayList<MatchedFinger> certainSong_id = new ArrayList<MatchedFinger>();
            classifiedBySong_id.add(certainSong_id);
        }
        for (int i = 0;i<matchedHashes.size();i++){
            int var = matchedHashes.get(i).song_id;
            classifiedBySong_id.get(var - 1).add(matchedHashes.get(i));
        }
        return classifiedBySong_id;
    }

    public String matchSong(ArrayList<ArrayList<MatchedFinger>> classifiedBySong_id){
        Map<Integer,Integer> map = new HashMap<>();
        int count = 0 ;
        int count_2 = 0;
        int temp = 0;
        int[] tag = new int[5];
        String string = null;
        for (int i = 0;i<classifiedBySong_id.size();i++){
            int mark = count_2;
            for (int j = 0;j<classifiedBySong_id.get(i).size();j = j + count){
                if (j == classifiedBySong_id.get(i).size() - 1){
                    temp = 1;
                    break;
                }
                for (int k = j + 1;k < classifiedBySong_id.get(i).size();k++){
                    if (classifiedBySong_id.get(i).get(j).dOffset == classifiedBySong_id.get(i).get(k).dOffset){
                        count++;
                    }
                    continue;
                }
                if (count>count_2){
                    count_2 = count;
                    map.put(count_2,map.get(count_2));
                }
            }
            tag[i] = count_2 - mark;
        }

        int max,n = 0;
        max = tag[0];
        for (int i = 0;i<5;i++){
            if (tag[i]>max){
                max = tag[i];
                n = i;
            }
        }

        SongfingerJDBC songfingerJDBC = new SongfingerJDBC();
        String sql = "select * from song  where song_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = songfingerJDBC.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,classifiedBySong_id.get(n).get(0).song_id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                string = resultSet.getString("song_name");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(resultSet != null)
                    resultSet.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(preparedStatement != null)
                    preparedStatement.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            try{
                if(connection != null)
                    connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return string;
    }
}
