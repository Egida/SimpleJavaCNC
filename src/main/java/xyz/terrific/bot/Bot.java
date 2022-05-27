package xyz.terrific.bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Bot {
    public static ArrayList<Bot> bots = new ArrayList<>();

    private final Socket            socket;
    private final BufferedReader    reader;
    private final BufferedWriter    writer;


    public Bot(Socket socket) throws Exception {

        String hostname = InetAddress.getLocalHost().getHostName();
        this.socket     = socket;
        this.writer     = new BufferedWriter(new OutputStreamWriter(    this.socket.getOutputStream()   ));
        this.reader     = new BufferedReader(new InputStreamReader(     this.socket.getInputStream()    ));

        this.writer.write(hostname);
        this.writer.newLine();
        this.writer.flush();

        bots.add(this);
    }


    public void execCommand(String message) {
        String status = BotMain.commandManager.handleMessage(message, this);

        if (status.startsWith("ERROR:")) {
        }
    }

    public void sendMessage(String message) {
        try {
            this.writer.write(message);
            this.writer.newLine();
            this.writer.flush();
        } catch (Exception e) {
            close(this.socket, this.reader, this.writer);
            e.printStackTrace();
        }
    }

    /*
    public void inputMessage() {
        try {

            Scanner scanner = new Scanner(System.in);
            while (this.socket.isConnected()) {

                System.out.print(" > ");
                String message = scanner.nextLine().strip().replace("\n", "");
                sendMessage(message);

            }

        } catch (Exception e) {
            close(this.socket, this.reader, this.writer);
            e.printStackTrace();
        }
    }
    */

    public void listen() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;

                while (socket.isConnected()) {
                    try {

                        message = reader.readLine();
                        System.out.println(message);

                        execCommand(message);

                    } catch (Exception e) {
                        close(socket, reader, writer);
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }).start();

    }


    public void removeBot() {
        bots.remove(this);
        System.out.println("Closed Connection");
    }

    public void close(Socket socket, BufferedReader reader, BufferedWriter writer) {
        removeBot();
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() {

        try {

            Socket socket = new Socket("127.0.0.1", 3771);
            Bot bot = new Bot(socket);
            bot.listen();
            // bot.inputMessage();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
