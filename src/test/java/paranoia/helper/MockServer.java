package paranoia.helper;

import daiv.networking.command.ParanoiaCommand;
import org.junit.Assert;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServer extends Thread {
    private Socket client;
    private ServerSocket server;
    private boolean open = false;
    private BufferedWriter clientWriter;
    private final Object lock;

    public MockServer(int port, Object lock) {
        this.lock = lock;
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
            synchronized (lock) {
                lock.notify();
            }
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

    public boolean isOpen() {
        return open;
    }

    public void sendCommand(ParanoiaCommand command) {
        try {
            if(open && client.isConnected() && !client.isClosed()) {
                clientWriter.write(command.toJsonObject().toString());
                clientWriter.newLine();
                clientWriter.flush();
            } else Assert.fail("Server is closed");
        } catch (IOException e) {
            Assert.fail(e.getLocalizedMessage());
        }
    }
}
