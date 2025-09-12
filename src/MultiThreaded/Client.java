package MultiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public Runnable getRunnable() {
        return () -> {
            int port = 8010;
            try {
                InetAddress address = InetAddress.getByName("localhost");

                try (Socket socket = new Socket(address, port);
                     PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader fromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    // Send message to server
                    toSocket.println("Hello from the client");

                    // Read response
                    String line = fromSocket.readLine();
                    System.out.println("Response from the socket is: " + line);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        Client client = new Client();

        // start a few clients to test
        for (int i = 0; i < 5; i++) {
            new Thread(client.getRunnable()).start();
        }
    }
}
