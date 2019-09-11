package my.buffer;


import java.nio.ByteBuffer;

public class   P {


    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(6);
        System.out.println(buffer);

        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);

        buffer.mark();
        buffer.put((byte) 4);

        System.out.println("put后=======>");
        System.out.println(buffer);

        buffer.reset();


        System.out.println("reset后=======>");
        System.out.println(buffer);



        buffer.flip();
        System.out.println("flip后===>");
        System.out.println(buffer);



        buffer.get();

        System.out.println("get后===>");
        System.out.println(buffer);

        buffer.mark();
        System.out.println("再次mark后===>");
        System.out.println(buffer);


        buffer.reset();
        System.out.println("再次reset后===>");
        System.out.println(buffer);


        buffer.get();
        buffer.get();
        buffer.get();
        System.out.println("get后===>");
        System.out.println(buffer);
    }

}
