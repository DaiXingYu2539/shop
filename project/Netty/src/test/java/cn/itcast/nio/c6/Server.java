package cn.itcast.nio.c6;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;


@Slf4j
public class Server {
    public static void main(String[] args) throws Exception {

        //创建selector 管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        //创建selector 和channel的联系（注册）

        //SelectionKey  就是将来事件发生后， 通过它可以知道事件和那个channel的事件。
        SelectionKey sscKey = ssc.register(selector, 0, null);
        log.debug("sscKey:{}",sscKey);
        /*
         * 四种事件
         * accept  会在有链接请求时触发
         *connect 是客户端连接建立后触发
         * read  可读事件
         * write 可写事件
         * */
        //这个时候 ssckey只关注 accentpt事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        while(true){
            //3. select 方法，没有事情发生 线程阻塞 有事件  线程才会恢复运行
            //select  事件在未处理的时候 不阻塞 ， 事件 要么处理 要么取消 key。cancel（）；
            selector.select();
            //处理事件

            //这个集和中的所有事件 ，因为它绑定了 多个channel
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();

                log.debug("key:{}",key);
                //*****
                iterator.remove();
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    log.debug("{}",sc);
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = sc.register(selector, 0, buffer);//buffer作为attachment
                    scKey.interestOps(SelectionKey.OP_READ);
                }else if(key.isReadable()){

                    try{
                        SocketChannel channel = (SocketChannel) key.channel();
                        //获取selectKey 上关联的附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);//正常断开的话 返回-1 表示没有数据
                        if(read==-1) key.cancel();
                        else{
                            //position 和 limit 一样 说明缓存满了 需要扩容
                            if(buffer.position()==buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity()*2);
                                buffer.flip();
                                newBuffer.put(buffer);  // 就缓存 让入到新缓存中
                                key.attach(newBuffer);  //设置新的附件。
                                continue;
                            }
                            split(buffer);
                            //buffer.flip();
                            //System.out.println(StandardCharsets.UTF_8.decode(buffer).toString());


                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        key.cancel();
                    }

                }



            }

        }

    }

    private static void split(ByteBuffer source) {
        source.flip();
        log.debug("source.limit()=={}",source.limit());
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整消息
            if (source.get(i) == '\n' ) {

                int length = i + 1 - source.position();
                // 把这条完整消息存入新的 ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }

                //System.out.println((char) target.get(0));
                target.flip();
                System.out.println(StandardCharsets.UTF_8.decode(target).toString());
            }
        }
        source.compact();
    }
}
