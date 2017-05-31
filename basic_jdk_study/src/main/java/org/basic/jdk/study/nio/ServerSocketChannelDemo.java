package org.basic.jdk.study.nio;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Erik_Yim on 2017/5/31.
 *
 * https://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html
 */
public class ServerSocketChannelDemo {

   /* public static void main(String[] args) throws IOException {


        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);



    }*/



    private int ports[];
    private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

    public ServerSocketChannelDemo(int ports[]) throws IOException {
        this.ports = ports;

        go();
    }

     public static void main(String args[]) throws Exception {
//        if (args.length <= 0) {
//            System.err.println("Usage: java MultiPortEcho port [port port …]");
//            System.exit(1);
//        }

        int ports[] = new int[] {9999,20002,10001};


        new ServerSocketChannelDemo(ports);
    }

    private void go() throws IOException {
// Create a new selector
        Selector selector = Selector.open();

// Open a listener on each port, and register each one
// with the selector
        for (int i = 0; i < ports.length; ++i) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ServerSocket ss = ssc.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            ss.bind(address);

            SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Going to listen on " + ports[i]);
        }

        while (true) {
            int num = selector.select();

            Set selectedKeys = selector.selectedKeys();
            Iterator it = selectedKeys.iterator();

            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();

                if ((key.readyOps() & SelectionKey.OP_ACCEPT)
                        == SelectionKey.OP_ACCEPT) {
// Accept the new connection
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

// Add the new connection to the ``
                    SelectionKey newKey = sc.register(selector, SelectionKey.OP_READ); //接受事件完成后注册获取数据事件，下次玄幻就会有获取数据事件触发了
                    it.remove(); //使用完以后要删除不然下次遍历依然会有这个事件

                    System.out.println("Got connection from " + sc);
                } else if ((key.readyOps() & SelectionKey.OP_READ)
                        == SelectionKey.OP_READ) {
// Read the data
                    SocketChannel sc = (SocketChannel) key.channel();

// Echo data
                    int bytesEchoed = 0;
                    while (true) {
                        echoBuffer.clear();

                        int r = sc.read(echoBuffer);

                        if (r <= 0) {
                            break;
                        }

                        echoBuffer.flip();

                        sc.write(echoBuffer);
                        bytesEchoed += r;
                    }

                    System.out.println("Echoed " + bytesEchoed + " from " + sc);

                    it.remove();
                }

            }

            System.out.println("going to clear");
            selectedKeys.clear();
            System.out.println("cleared");
        }
    }
}


class ClientDemon {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("127.0.0.1",9999);
        //向本机的9999端口发出客户请求
        BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
        //由系统标准输入设备构造BufferedReader对象
        PrintWriter os=new PrintWriter(socket.getOutputStream());
        //由Socket对象得到输出流，并构造PrintWriter对象
        BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //由Socket对象得到输入流，并构造相应的BufferedReader对象
        String readline;
        readline=sin.readLine(); //从系统标准输入读入一字符串
        while(!readline.equals("bye")){
        //若从标准输入读入的字符串为 "bye"则停止循环
            os.println(readline);
            //将从系统标准输入读入的字符串输出到Server
            os.flush();
            //刷新输出流，使Server马上收到该字符串
            System.out.println("Client:"+readline);
            //在系统标准输出上打印读入的字符串
            System.out.println("Server:"+is.readLine());
            //从Server读入一字符串，并打印到标准输出上
            readline=sin.readLine(); //从系统标准输入读入一字符串
        } //继续循环
        os.close(); //关闭Socket输出流
        is.close(); //关闭Socket输入流
        socket.close(); //关闭Socket
    }
}
