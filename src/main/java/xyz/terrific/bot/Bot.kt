package xyz.terrific.bot

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket

/**
 * @author TerrificTable55
 * @version 1.0
 */
class Bot(socket: Socket) {
    private val socket: Socket
    private val reader: BufferedReader
    private val writer: BufferedWriter

    /**
     * @use Bot initialization, takes a socket as argument and throws an Exception, it will create the writer and reader, set hostname and socket, and send hostname to the server
     * @param socket
     * @throws Exception
     */
    init {
        val hostname = InetAddress.getLocalHost().hostName
        this.socket = socket
        writer = BufferedWriter(OutputStreamWriter(this.socket.getOutputStream()))
        reader = BufferedReader(InputStreamReader(this.socket.getInputStream()))
        writer.write(hostname)
        writer.newLine()
        writer.flush()
        bots.add(this)
    }

    /**
     * @use Execute command, this function is called if the bot receives a command from the server, it takes a message as argument, and calls the `CommandManager.handleMessage` function
     * @param message
     */
    fun execCommand(message: String?) {
        val status = BotMain.commandManager!!.handleMessage(message, this)
        if (status.startsWith("ERROR:")) {
        }
    }

    /**
     * @use Sends a message to the server
     * @param message
     */
    fun sendMessage(message: String?) {
        try {
            writer.write(message)
            writer.newLine()
            writer.flush()
        } catch (e: Exception) {
            close(socket, reader, writer)
            e.printStackTrace()
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
    fun listen() {
        Thread(Runnable {
            var message: String?
            while (socket.isConnected) {
                try {
                    message = reader.readLine()
                    println(message)
                    execCommand(message)
                } catch (e: Exception) {
                    close(socket, reader, writer)
                    e.printStackTrace()
                    return@Runnable
                }
            }
        }).start()
    }

    /**
     * @use Remove Bot from `bots` list
     */
    fun removeBot() {
        bots.remove(this)
        println("Closed Connection")
    }

    /**
     * @use Close bot socket, reader and writer
     * @param socket
     * @param reader
     * @param writer
     */
    fun close(socket: Socket?, reader: BufferedReader?, writer: BufferedWriter?) {
        removeBot()
        try {
            reader?.close()
            writer?.close()
            socket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        var bots = ArrayList<Bot>()

        /**
         * @use init function, it's called to create the bot and socket
         * @throws RuntimeException
         */
        fun init() {
            try {
                val socket = Socket("127.0.0.1", 3771)
                val bot = Bot(socket)
                bot.listen()
                // bot.inputMessage();
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}