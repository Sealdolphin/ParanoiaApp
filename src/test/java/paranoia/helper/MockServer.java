package paranoia.helper;

import org.junit.Assert;
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

    public MockServer() {
        this(workingPort);
    }

    public MockServer(int port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        try {
            client = server.accept();
            clientWriter = new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream())
            );
            open = true;
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }

    public void close() {
        try {
            client.close();
            server.close();
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
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
            Assert.fail(e.getLocalizedMessage());
        }
    }
}
