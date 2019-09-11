package my.buffer;

import java.nio.ByteBuffer;

public class ZPFTest{

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        buffer.put((byte) 4);

        buffer.put((byte) 5);
        buffer.put((byte) 6);

        System.out.println("put 之后==>");
        System.out.println(buffer);

        buffer.flip();


        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());

        System.out.println("get 之后===>");
        System.out.println(buffer);


        buffer.compact();
        System.out.println("compact 之后===>");
        System.out.println(buffer);



        buffer.put((byte) 7);
        buffer.put((byte) 8);
        buffer.put((byte) 9);
       /* System.out.println("再次 get===>");
        System.out.println(buffer.get());
        System.out.println(buffer.get());*/
        System.out.println("再次put之后的buffer===>");
        System.out.println(buffer);



        buffer.flip();
        System.out.println("再次flip后的buffer===>");
        System.out.println(buffer);


        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());







    }



}
