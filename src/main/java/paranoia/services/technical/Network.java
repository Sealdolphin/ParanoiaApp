package paranoia.services.technical;

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
 * Establishes the Network
 */
public class Network {

    private Socket client;
    private static final int workingPort = 6532;
    private BufferedWriter output;
    private BufferedReader input;

    public boolean connectWithIP(String ip) {
        return connect("http://" + ip + ":" + workingPort);
    }

    public boolean connect(String host) {
        try {
            URL url = new URL("http", host, workingPort,"");
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

            return true;
        } catch (IOException malformed) {
            malformed.printStackTrace();
            return false;
        }
    }

    public void sendMessage(String jsonMsg) {
        try {
            if(client.isConnected()) {
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

    public void listen() {
        try {
            if(client.isConnected()) {
                //TODO: send command to JSON parser
                System.out.println(input.readLine());
            } else {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
