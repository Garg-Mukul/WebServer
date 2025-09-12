package MultiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    public Consumer<Socket> getConsumer(){
//        return new Consumer<Socket>() {
//            @Override
//            public void accept(Socket cliensocket) {
//                try{
//                PrintWriter toClient = new PrintWriter(clienSocket.getOutputStream());
//                toClient.println("Hello from the server. ");
//                toClient.close();
//                clienSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        };
//            }
//        };
        return (clienSocket)->{
            try{
                PrintWriter toClient = new PrintWriter(clienSocket.getOutputStream());
                toClient.println("Hello from the server. ");
                toClient.close();
                clienSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
    public static void main(String[] args){
        int port = 8010;
        Server server = new Server();
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(20000);
            System.out.println("Server is listening on port "+port);
            while(true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(acceptedSocket));
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
