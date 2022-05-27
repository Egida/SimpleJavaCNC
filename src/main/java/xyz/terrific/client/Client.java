package xyz.terrific.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author TerrificTable55
 * @version 1.0
 */
public class Client {
    public static ArrayList<Client> clients = new ArrayList<>();

    private final Socket            socket;
    private final BufferedReader    reader;
    private final BufferedWriter    writer;


    /**
     * @use Initializes the Client, it sets hostname and socket, defines writer and reader and sends hostname to server
     * @param socket used to connect to server
     * @throws Exception
     */
    public Client(Socket socket) throws Exception {

        String hostname = InetAddress.getLocalHost().getHostName();
        this.socket     = socket;
        this.writer     = new BufferedWriter(new OutputStreamWriter(    this.socket.getOutputStream()   ));
        this.reader     = new BufferedReader(new InputStreamReader(     this.socket.getInputStream()    ));

        this.writer.write(hostname);
        this.writer.newLine();
        this.writer.flush();

        clients.add(this);
    }

    /**
     * @param message Message sent to server
     */
    public void sendMessage(String message) {
        try {

            this.writer.write(message);
            this.writer.newLine();
            this.writer.flush();

        } catch (Exception e) {
            close(this.socket, this.reader, this.writer);
            System.out.print("EXCEPTION ->  sendMessage()  <-");
            e.printStackTrace();
        }
    }

    /**
     * @use Waits for a message / command to be inputed by a user and sends that message / command to the server
     */
    public void inputMessage() {
        try {

            Scanner scanner = new Scanner(System.in);
            while (this.socket.isConnected()) {

                System.out.print(" > ");
                String message = scanner.nextLine().strip().replace("\n", "");

                if (message.equals("exit")) {
                    close(this.socket, this.reader, this.writer);
                    break;
                }

                if (message.contains("bot=")) {
                    message = "> " + message;
                } else {
                    message = ">> " + message;
                }

                sendMessage(message);

            }

        } catch (Exception e) {
            close(this.socket, this.reader, this.writer);
            System.out.print("EXCEPTION ->  inputMessage()  <-");
            e.printStackTrace();
        }
    }


    public void removeClient() {
        clients.remove(this);
        System.out.println("Closed Connection");
    }

    /**
     * @param socket closes socket
     * @param reader closes reader
     * @param writer closes writer
     */
    public void close(Socket socket, BufferedReader reader, BufferedWriter writer) {
        removeClient();
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (Exception e) {
            System.out.print("EXCEPTION ->  close()  <-");
            e.printStackTrace();
        }
    }

}
