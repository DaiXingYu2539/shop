package cn.itcast.nio.c6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(ByteBuffer.wrap("abcdsdsdsdsdsdsddsds\nbcd\nsdad\n".getBytes()));
        SocketAddress address = sc.getLocalAddress();
        System.out.println("waiting...");
        sc.close();

    }

}
