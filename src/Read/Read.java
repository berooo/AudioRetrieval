package Read;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Acer on 2016-11-06.
 */

public class Read {
    private double[][]data=null;				//数据数组

    private String chunkdescriptor=null;		//根节点名称
    static private int lenchunkdescriptor=4;

    private long chunksize=0;			//从这个字段开始（不包括这个字段）的长度

    private String waveflag=null;			//文件格式
    static private int lenwaveflag=4;

    private String fmtubchunk=null;
    static private int lenfmtubchunk=4;

    private long subchunk1size=0;			//格式结点除去前两个字段的长度

    private int audioformat=0;				//音频格式（1为PCM格式，其他为各种压缩格式）

    private int numchannels=0;				//声道数

    private long samplerate=0;				//采样率

    private long byterate=0;				//比特率/8

    private int blockalign=0;

    private int bitpersample=0;				//位深度

    private int ExtraParamSize=0;			//其他参数的长度

    private String datasubchunk=null;		//数据节点的名称
    static private int lendatasubchunk=4;

    private long  subchunk2size=0;			//数据节点的长度


    private FileInputStream fis=null;				//文件输入流
    private BufferedInputStream bis=null;

    private String readString(int len)				//读取字符串的函数
    {
        byte[]buf=new byte[len];
        try{
            if(bis.read(buf)!=len)
                throw new IOException("no more data.");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return new String(buf);
    }

    private long readLong()						//读取长整形的函数
    {
        long res=0;
        try{
            long[] l=new long[4];
            for(int i=0;i<4;i++)
            {
                l[i]=bis.read();
                if(l[i]==-1)
                    throw new IOException("no more data.");
            }
            res=l[0]|l[1]<<8|l[2]<<16|l[3]<<24;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }

    private short readShort()					//读取短整型的函数
    {
        byte[]buf=new byte[2];
        int res=0;
        try{
            if(bis.read(buf)!=2)
                throw new IOException("no more data.");
            res=(buf[0]&0x000000ff)|((short)buf[1]<<8);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return (short)res;
    }

    public double[][] read(String path)				//读取音频文件的函数
    {
        System.out.println("读取开始...");
        try {
            fis=new FileInputStream(path);
            bis=new BufferedInputStream(fis);

            this.chunkdescriptor=readString(lenchunkdescriptor);		//根节点名称

            this.chunksize=readLong();			//根节点长度（从这个字段开始）

            this.waveflag=readString(lenwaveflag);		//文件格式

            this.fmtubchunk=readString(lenfmtubchunk);

            this.subchunk1size=readLong();			//格式节点除去前两个字段的长度

            this.audioformat=readShort();			//音频格式

            this.numchannels=readShort();			//声道数

            this.samplerate=readLong();				//采样率

            this.byterate=readLong();				//比特率/8

            this.blockalign=readShort();

            this.bitpersample=readShort();			//位深度

            this.ExtraParamSize=readShort();		//其他参数的长度

            this.datasubchunk=readString(lendatasubchunk);		//数据节点的名称

            this.subchunk2size=readLong();			//数据节点的长度

            if(chunkdescriptor.endsWith("RIFF"))			//判断是否为RIFF格式
                System.out.println("它是RIFF格式");
            else
                System.out.println("它不是RIFF格式");

            if(audioformat==1)							//判断是否为PCM格式
                System.out.println("它是PCM格式");
            else
                System.out.println("它不是PCM格式");

            if(numchannels==1)						//判断是否为单声道
                System.out.println("它是单声道");
            else
                System.out.println("它不是单声道");

            if(samplerate==44100)					//判断采样率是否为44100HZ
                System.out.println("采样率是44100HZ");
            else
                System.out.println("采样率不是44100HZ");

            if(bitpersample==16)					//判断位深度是否为16bit
                System.out.println("位深度是16bit");
            else
                System.out.println("位深度不是16bit");

            int len=(int)(this.subchunk2size/(this.bitpersample/8)/this.numchannels);
            this.data=new double[numchannels][len];

            for(int i=0;i<len;i++)								//将数据写入data数组
            {
                for(int n=0;n<this.numchannels;n++)
                {
                    if(this.bitpersample==8)
                        try {
                            this.data[n][i]=bis.read();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    else if(this.bitpersample==16)
                        this.data[n][i]=this.readShort();
                }
            }
            System.out.println("读取成功！");
        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }finally{
            try{
                if(bis!=null)
                    bis.close();			//关闭bis
                if(fis!=null)
                    fis.close();			//关闭fis
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return data;
    }

    public void print(String path)
    {
        try {
            PrintWriter out=new PrintWriter(new File(path));
            for(int i=0;i<data.length;i++)						//将data数组中的数据写入csv文件
            {
                for(int j=0;j<data[0].length;j++)
                    out.printf("%.2f,\n", data[i][j]);
            }
            System.out.print("csv文件创建成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        Read test=new Read();			//实例化一个Read对象
//
//
//        String readpath="doc/不能说的秘密.wav";
//        String printpath="doc/test.csv";
//
//        test.read(readpath);
//        test.print(printpath);
//    }
}