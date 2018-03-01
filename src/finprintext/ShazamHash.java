package finprintext;

/**
 * Created by Acer on 2016-11-07.
 * 指纹哈希类
 */
public class ShazamHash {
    public short f1; //本帧最强频率
    public short f2; //组合哈希中下一帧（下两帧）最强频率
    public short dt; //f1与f2相差的帧数
    public int offset; //f1位于第几帧
    public int song_id;
}
