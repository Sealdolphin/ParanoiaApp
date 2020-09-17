package paranoia.services.technical.networking;

import paranoia.services.technical.CommandParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ParanoiaSocket {

    private final Socket client;
    private final BufferedReader input;
    private final BufferedWriter output;
    private Thread readInput;

    public ParanoiaSocket(Socket socket) throws IOException {
        client = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public boolean isOpen() {
        return client.isConnected() && !client.isClosed();
    }

    public void listen(CommandParser parser) {
        readInput = new Thread(() -> readSocketInput(parser));
        readInput.start();
    }

    private void readSocketInput(CommandParser parser) {
        while (isOpen()) {
            try {
                String line = input.readLine();
                if(line != null)
                    parser.parse(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        if(client.isConnected()) {
            try {
                output.write(message);
                output.newLine();
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy() {
        try {
            client.shutdownInput();
            client.shutdownOutput();
            client.close();
            readInput.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
