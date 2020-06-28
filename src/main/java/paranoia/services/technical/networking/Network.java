package paranoia.services.technical.networking;

import paranoia.services.technical.CommandParser;
import paranoia.services.technical.HelperThread;
import paranoia.services.technical.command.DisconnectCommand;
import paranoia.services.technical.command.ParanoiaCommand;
import paranoia.visuals.messages.ParanoiaMessage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;

/**
 * Establishes the Network.
 * Every client follows the Paranoia protocol:
 *  - Establishing socket connection
 *  - checking server version
 *  - sending credentials if necessary
 *  - sending appropriate command(s)
 */
public class Network implements
    DisconnectCommand.ParanoiaDisconnectListener
{

    private Socket client = null;
    public static final int workingPort = 6532;
    private BufferedWriter output;
    private BufferedReader input;
    private final CommandParser parser;
    private boolean connected = false;
    private final Object readLock = new Object();

    public Network(CommandParser parser) {
        this.parser = parser;
    }

    private void connectWithIP(String ip) throws IOException {
        URL url = new URL("http", ip, workingPort,"");
        connect(url);
    }

    private void connect(URL url) throws IOException {
        System.out.println("Connecting to " + url);
        client = new Socket(url.getHost(), url.getPort());

        output = new BufferedWriter(
            new OutputStreamWriter(
                new BufferedOutputStream(client.getOutputStream())
            )
        );
        input = new BufferedReader(
            new InputStreamReader(
                new BufferedInputStream(client.getInputStream())
            )
        );
        connected = true;
    }

    public CommandParser getParser() {
        return parser;
    }

    public void connectToServer(String ipAddress) throws IOException {
        //Connect to server
        if(ipAddress.contains(":")){
            connect(new URL(ipAddress));
        } else {
            connectWithIP(ipAddress);
        }
        listen();
    }

    public void disconnect() {
        if(client == null) return;
        try {
            synchronized (readLock) { readLock.notify(); }
            client.close();
            connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        ParanoiaMessage.info("You have been disconnected from the Alpha Complex");
    }

    public boolean sendCommand(ParanoiaCommand command) {
        if(isOpen()) {
            sendMessage(command.toJsonObject().toString());
            return true;
        } else {
            return false;
        }
    }

    private synchronized void sendMessage(String jsonMsg) {
        try {
            if(connected) {
                output.write(jsonMsg);
                output.newLine();
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            ParanoiaMessage.error(e);
        }
    }

    private void listen() {
        if(connected) {
            new Thread(this::listenForInput).start();
        }
    }

    private void listenForInput() {
        while (connected) {
            try {
                HelperThread<String> reading =
                    new HelperThread<>(v -> doRead(), readLock);
                reading.start();
                synchronized (readLock) { readLock.wait(); }
                String line = reading.getValue();
                if(line != null) {
                    parser.parse(line);
                } else {
                    disconnect();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                disconnect();
                ParanoiaMessage.error(e);
            }
        }
    }

    private String doRead() {
        try {
            return input.readLine();
        } catch (IOException e) {
            if(connected) e.printStackTrace();
            return null;
        }
    }

    public boolean isOpen() {
        return connected;
    }

    public String getIP() {
        if(client == null) return "Not connected";
        return client.getInetAddress().getHostAddress();
    }
}
