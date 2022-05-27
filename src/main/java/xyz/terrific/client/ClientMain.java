package xyz.terrific.client;

import java.net.Socket;

public class ClientMain {

    public static void main(String[] args) {

        try {

            Socket socket = new Socket("127.0.0.1", 1773);
            Client client = new Client(socket);
            client.listen();
            client.inputMessage();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
