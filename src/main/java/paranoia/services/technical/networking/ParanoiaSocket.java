package paranoia.services.technical.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ParanoiaSocket {

    private final Socket client;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final List<SocketListener> listeners = new ArrayList<>();

    public ParanoiaSocket(Socket socket) throws IOException {
        client = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
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
                int length = input.readInt();
                byte[] message = new byte[length];
                if(length > 0) {
                    input.readFully(message, 0, message.length);
                }
                if(length == 0) break;
                listeners.forEach(l -> l.readInput(message));
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try { client.close(); } catch (IOException ignored) { }
        listeners.forEach(SocketListener::fireTerminated);
    }

    public void sendMessage(byte[] message) {
        if(client.isConnected()) {
            try {
                output.writeInt(message.length);
                output.write(message);
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
