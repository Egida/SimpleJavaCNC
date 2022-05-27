package xyz.terrific.server.client.cnc;

import xyz.terrific.server.bot.cnc.BotHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;


/**
 * @author TerrificTable55
 * @version 1.0
 */
public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> handlers = new ArrayList<>();

    private String          name;
    private Socket          socket;
    private BufferedReader  reader;
    public BufferedWriter   writer;

    /**
     * @use ClientHandler initializer, defines writer and reader
     * @param socket sets private socket variable
     */
    public ClientHandler(Socket socket) {
        try {

            this.socket     = socket;
            this.writer     = new BufferedWriter(new OutputStreamWriter(    this.socket.getOutputStream()   ));
            this.reader     = new BufferedReader(new InputStreamReader(     this.socket.getInputStream()    ));
            this.name       = this.reader.readLine();
            handlers.add(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @use Implements `run` function from Runnable interface
     */
    @Override
    public void run() {

        while (this.socket.isConnected()) {
            try {
                String message = this.reader.readLine();
                System.out.printf(" [ CLIENT | %s ]  %s\n", this.name, message);

                if (message.startsWith("> ")) {
                    sendAll(message);
                }
                else if (message.startsWith(">> ")) {
                    send(message.split(" ")[1], message);
                }

            } catch (Exception e) {
                close(this.socket, this.reader, this.writer);
                e.printStackTrace();
                return;
            }
        }

    }

    /**
     * @use Sends a message to the clients
     * @param botName sends message to this bot / client
     * @param command sends this command
     */
    public static void send(String botName, String command) {
        for (BotHandler handler : BotHandler.handlers) {
            try {

                if (!handler.name.equals(botName)) {
                    handler.writer.write(command);
                    handler.writer.newLine();
                    handler.writer.flush();
                }

                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param command this command is sent to all clients / bots
     */
    public void sendAll(String command) {
        for (BotHandler handler : BotHandler.handlers) {
            try {

                if (!handler.name.equals(this.name)) {
                    handler.writer.write(command);
                    handler.writer.newLine();
                    handler.writer.flush();
                }

                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @use removes handler from `handlers` list
     */
    public void removeHandler() {
        handlers.remove(this);
        System.out.printf(" [ CLIENT | %s ]  Closed Connection\n", this.name);
    }

    /**
     * @param socket closes socket
     * @param reader closes reader
     * @param writer closes writer
     */
    public void close(Socket socket, BufferedReader reader, BufferedWriter writer) {
        removeHandler();
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
