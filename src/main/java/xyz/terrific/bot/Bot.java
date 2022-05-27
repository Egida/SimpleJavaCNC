package xyz.terrific.bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


/**
 * @author TerrificTable55
 * @version 1.0
 */
public class Bot {
    public static ArrayList<Bot> bots = new ArrayList<>();

    private final Socket            socket;
    private final BufferedReader    reader;
    private final BufferedWriter    writer;


    /**
     * @use Bot initialization, takes a socket as argument and throws an Exception, it will create the writer and reader, set hostname and socket, and send hostname to the server
     * @param socket
     * @throws Exception
     */
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


    /**
     * @use Execute command, this function is called if the bot receives a command from the server, it takes a message as argument, and calls the `CommandManager.handleMessage` function
     * @param message
     */
    public void execCommand(String message) {
        String status = BotMain.commandManager.handleMessage(message, this);

        if (status.startsWith("ERROR:")) {
        }
    }

    /**
     * @use Sends a message to the server
     * @param message
     */
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

    /**
     * @use Listens for commands send to the bot
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


    /**
     * @use Remove Bot from `bots` list
     */
    public void removeBot() {
        bots.remove(this);
        System.out.println("Closed Connection");
    }

    /**
     * @use Close bot socket, reader and writer
     * @param socket
     * @param reader
     * @param writer
     */
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

    /**
     * @use init function, it's called to create the bot and socket
     * @throws RuntimeException
     */
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
