package xyz.terrific.server.bot.cnc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class BotHandler implements Runnable {
    public static ArrayList<BotHandler> handlers = new ArrayList<>();

    public String           name;
    private Socket          socket;
    public BufferedReader  reader;
    public BufferedWriter   writer;


    public BotHandler(Socket socket) {
        try {

            this.socket     = socket;
            this.writer     = new BufferedWriter(new OutputStreamWriter(    this.socket.getOutputStream()   ));
            this.reader     = new BufferedReader(new InputStreamReader(     this.socket.getInputStream()    ));
            this.name       = this.reader.readLine();
            handlers.add(this);

            sendAll(String.format(" [ BOT | %s ]  Connected", this.name));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;

        while (this.socket.isConnected()) {
            try {

                message = this.reader.readLine();
                System.out.printf(" [ BOT | %s ]  %s\n", this.name, message);

                sendAll(String.format(" [ BOT | %s ]  %s\n", this.name, message));

            } catch (Exception e) {
                close(this.socket, this.reader, this.writer);
                e.printStackTrace();
                return;
            }
        }

    }

    public static void send(String botName, String command) {
        for (BotHandler handler : handlers) {
            try {

                if (!handler.name.equals(botName)) {
                    handler.writer.write(command);
                    handler.writer.newLine();
                    handler.writer.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendAll(String message) {
        for (BotHandler handler : handlers) {
            try {

                if (!handler.name.equals(this.name)) {
                    handler.writer.write(message);
                    handler.writer.newLine();
                    handler.writer.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
                close(this.socket, this.reader, this.writer);
            }
        }
    }

    public void removeHandler() {
        handlers.remove(this);
        System.out.printf(" [ BOT | %s ]  Closed Connection\n", this.name);
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
