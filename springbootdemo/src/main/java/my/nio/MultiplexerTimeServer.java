package my.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

   private Selector selector;

   private ServerSocketChannel serverChannel;

   private volatile boolean stop;

   public MultiplexerTimeServer(int port){
       try{
           selector = Selector.open();
           serverChannel = ServerSocketChannel.open();
           serverChannel.configureBlocking(false);
           serverChannel.socket().bind(new InetSocketAddress(port),1024);
           serverChannel.register(selector, SelectionKey.OP_ACCEPT);
           System.out.println("the time server is start in port :"+port);
       }catch (Exception e){
            System.exit(1);
       }
   }


   public void stop(){
       this.stop = true;
   }



    @Override
    public void run() {
       while (!stop){
           try{
               selector.select(1000);
               Set<SelectionKey> selectionKeys = selector.selectedKeys();
               Iterator<SelectionKey> iterator = selectionKeys.iterator();
               SelectionKey key = null;
               while (iterator.hasNext()){
                   key = iterator.next();
                   iterator.remove();
                   try{
                       handleInput(key);
                   }catch (Exception e){
                    if (key != null){
                        key.cancel();
                        if (key.channel() != null){
                            key.channel().close();
                        }
                    }
                   }
               }
           }catch (Exception e){

           }
       }
       //多路复用器关闭后,注册的channel和pipe都会自动关闭
        if (selector != null){
            try{
                selector.close();
            }catch (Exception e){

            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
       if (key.isValid()){
        //处理新接入的请求消息
           if (key.isAcceptable()){
               ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
               SocketChannel sc = ssc.accept();
               sc.configureBlocking(false);
                //添加新的连接到selector上
               sc.register(selector,SelectionKey.OP_READ);
           }

           if (key.isReadable()){
               //读数据
              SocketChannel sc = (SocketChannel) key.channel();
               ByteBuffer readBuffer = ByteBuffer.allocate(1024);
               int readBytes = sc.read(readBuffer);
               if (readBytes > 0){
                   readBuffer.flip();
                   byte[] bytes = new byte[readBuffer.remaining()];
                   readBuffer.get(bytes);
                   String body = new String(bytes, "UTF-8");
                   System.out.println("the time server receive order :"
                   +body);

                   String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)
                           ? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                   doWrite(sc,currentTime);
               }else if (readBytes < 0){
                   //对端链路关闭
                   key.cancel();
                   sc.close();
               }else {
                   //0字节  忽略
               }
           }
       }

    }

    private void doWrite(SocketChannel sc, String response) throws IOException {
       if (response != null && response.trim().length() >0){
           byte[] bytes = response.getBytes();
           ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
           writeBuffer.put(bytes);
           writeBuffer.flip();
           sc.write(writeBuffer);
       }
    }
}
