package xyz.terrific.client;

import java.net.Socket;


/**
 * @author TerrificTable55
 * @version 1.0
 */
public class ClientMain {

    /**
     * @use Run the Client
     * @throws RuntimeException
     * @param args
     */
    public static void main(String[] args) {

        try {

            Socket socket = new Socket("127.0.0.1", 1773);
            Client client = new Client(socket);
            client.inputMessage();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
