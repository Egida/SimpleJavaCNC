package xyz.terrific.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static ArrayList<Client> clients = new ArrayList<>();

    private final Socket            socket;
    private final BufferedReader    reader;
    private final BufferedWriter    writer;


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

    public void listen() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;

                while (socket.isConnected()) {
                    try {

                        message = reader.readLine();
                        if (message == null)
                            continue;

                        System.out.println(message);

                    } catch (Exception e) {
                        close(socket, reader, writer);
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


    public void removeClient() {
        clients.remove(this);
        System.out.println("Closed Connection");
    }

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
