package paranoia.services.technical;

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
import java.util.function.Function;

/**
 * Establishes the Network
 */
public class Network {

    private Socket client;
    public static final int workingPort = 6532;
    private BufferedWriter output;
    private BufferedReader input;
    private CommandParser parser;

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
        }
    }

    public void listen(Function<String, Void> function) {
        try {
            if(client.isConnected() && !client.isClosed()) {
                String msg = input.readLine();
                if(msg != null)
                    function.apply(msg);
                else client.close();
            } else {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
