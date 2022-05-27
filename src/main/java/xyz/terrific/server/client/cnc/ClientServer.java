package xyz.terrific.server.client.cnc;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author TerrificTable55
 * @version 1.0
 */
public class ClientServer {
    private final ServerSocket socket;

    /**
     * @param socket sets private socket variable
     */
    public ClientServer(ServerSocket socket) {
        this.socket = socket;
    }

    public static void init() throws IOException {

        ServerSocket cSocket    = new ServerSocket(1773);
        ClientServer server     = new ClientServer(cSocket);
        server.start();

    }

    /**
     * @use start function
     */
    public void start() {
        try {
            while (!socket.isClosed()) {

                Socket conn = socket.accept();

                System.out.printf(" [ CLIENT | %s ] Connected\n", conn.getRemoteSocketAddress().toString());
                ClientHandler handler = new ClientHandler(conn);

                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @use closes socket
     */
    public void close() {

        try {
            if (socket != null)
                socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
