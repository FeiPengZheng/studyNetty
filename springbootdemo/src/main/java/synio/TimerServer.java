package synio;

import java.net.ServerSocket;
import java.net.Socket;

public class TimerServer {
    public static void main(String[] args) throws Exception {
        int port = 9080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {

            }
        }

        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("time server is start in port ==>" + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
               /* new Thread(new TimeServerHandler(socket)).start();*/
                new Thread(new TimeServerHandler(socket)).start();
            }
        }finally {
            if (server != null) {
                System.out.println("the time server close");
                server.close();
                server = null;
            }
        }


    }


}
