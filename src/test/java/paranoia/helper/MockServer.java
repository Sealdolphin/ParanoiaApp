package paranoia.helper;

import paranoia.services.technical.command.ParanoiaCommand;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static paranoia.services.technical.Network.workingPort;

public class MockServer extends Thread {
    private Socket client;
    private ServerSocket server;
    private boolean open = false;
    private BufferedWriter clientWriter;

    @Override
    public void run() {
        try {
            server = new ServerSocket(workingPort);
            client = server.accept();
            clientWriter = new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream())
            );
            open = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            client.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(ParanoiaCommand command) {
        try {
            if(open && client.isConnected() && !client.isClosed()) {
                clientWriter.write(command.toJsonObject().toString());
                clientWriter.newLine();
                clientWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
