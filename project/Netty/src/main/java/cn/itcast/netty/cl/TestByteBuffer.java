package cn.itcast.netty.cl;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) throws IOException {
        //FileChannel
        //获取方式 1.输入输出流  2. RandomAccessFile

       try(FileChannel channel = new FileInputStream("d://out.txt").getChannel()){
           //准备缓冲区
           ByteBuffer buffer = ByteBuffer.allocate(2);
           //从channel读取数据，向buffer写入
           int len = 0;
           while ((len = channel.read(buffer))!=-1){ //数据读取完 返回-1


               buffer.flip();//切换至读模式

               while(buffer.hasRemaining()) //判断是否还有未读数据
               {
                   byte b = buffer.get();
                   //打印buffer的内容
                   System.out.println((char)b);
               }
               log.debug("============");
               buffer.clear();
           }

       }
       catch (IOException e){

       }

        HashMap<Integer,Integer> map = new HashMap<>();
       
    }
}
