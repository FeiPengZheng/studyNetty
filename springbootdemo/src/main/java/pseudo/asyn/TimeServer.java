package pseudo.asyn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket server = null;
        try {
            server = new ServerSocket(8080);
            System.out.println("server start port:"+port);

            TimeServerHandlerExecutorPool timeServerHandlerExecutor = new TimeServerHandlerExecutorPool(50, 10000);

            Socket socket = null;
            while (true){
                socket = server.accept();
                timeServerHandlerExecutor.execute(new TimeServerHandler(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (server != null){
                System.out.println("server closed");
                server.close();
                server = null;
            }
        }


    }

}
