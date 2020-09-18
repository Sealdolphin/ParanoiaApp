package paranoia.services.technical.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ParanoiaSocket {

    private final Socket client;
    private final BufferedReader input;
    private final BufferedWriter output;
    private final List<SocketListener> listeners = new ArrayList<>();

    public ParanoiaSocket(Socket socket) throws IOException {
        client = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        listen();
    }

    public boolean isOpen() {
        return client.isConnected() && !client.isClosed();
    }

    private void listen() {
        Thread readInput = new Thread(this::readSocketInput);
        readInput.start();
    }

    public void addListener(SocketListener listener) {
        listeners.add(listener);
    }

    private void readSocketInput() {
        while (isOpen()) {
            try {
                String line = input.readLine();
                if(line == null) break;
                listeners.forEach(l -> l.readInput(line));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try { client.close(); } catch (IOException ignored) { }
        listeners.forEach(SocketListener::fireTerminated);
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
            client.close();
        } catch (IOException e) {
            if(!client.isClosed())
                e.printStackTrace();
        }
    }

    public String getAddress() {
        return String.valueOf(client.getInetAddress().getHostAddress());
    }
}
