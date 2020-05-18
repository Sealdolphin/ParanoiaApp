package paranoia.services.technical;

import paranoia.services.technical.command.ChatCommand;
import paranoia.visuals.messages.ParanoiaError;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Establishes the Network
 */
public class Network {

    private Socket client = null;
    public static final int workingPort = 6532;
    private BufferedWriter output;
    private BufferedReader input;
    private final CommandParser parser = new CommandParser();

    public Network(
        ChatCommand.ParanoiaChatListener chatListener
    ) {
        parser.setChatListener(chatListener);
    }

    public void connectWithIP(String ip) throws MalformedURLException, UnknownHostException {
        URL url = new URL("http", ip, workingPort,"");
        connect(url);
    }

    public void connect(URL url) throws UnknownHostException {
        try {
            System.out.println("Connecting to " + url.toString());
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
        } catch (UnknownHostException it) {
            throw it;
        } catch (IOException error) {
            error.printStackTrace();
            ParanoiaError.error(error);
        }
    }

    public void disconnect() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String jsonMsg) {
        try {
            if(client.isConnected() && !client.isClosed()) {
                output.write(jsonMsg);
                output.newLine();
                output.flush();
            } else {
                output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            ParanoiaError.error(e);
        }
    }

    public void listen() {
        try {
            if(client.isConnected() && !client.isClosed()) {
                String msg = input.readLine();
                if(msg != null)
                    parser.parse(msg);
                else client.close();
            } else {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            ParanoiaError.error(e);
        }
    }

    public boolean isOpen() {
        if(client == null) return false;
        return client.isConnected() && !client.isClosed();
    }
}
