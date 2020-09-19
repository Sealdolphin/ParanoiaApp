package paranoia.services.technical.networking;

import paranoia.services.technical.CommandParser;
import paranoia.services.technical.command.DisconnectCommand;
import paranoia.services.technical.command.ParanoiaCommand;
import paranoia.visuals.messages.ParanoiaMessage;

import java.io.IOException;
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
    SocketListener,
    DisconnectCommand.ParanoiaDisconnectListener
{

    private ParanoiaSocket client = null;
    public static final int workingPort = 6532;
    private final CommandParser parser;

    public Network(CommandParser parser) {
        this.parser = parser;
    }

    private void connect(URL url) throws IOException {
        System.out.println("Connecting to " + url);
        Socket socket = new Socket(url.getHost(), url.getPort());
        client = new ParanoiaSocket(socket);
        client.addListener(this);
    }

    private void connectWithIP(String ip) throws IOException {
        URL url = new URL("http", ip, workingPort,"");
        connect(url);
    }

    public void connectToServer(String ipAddress) throws IOException {
        //Connect to server
        if(ipAddress.contains(":")){
            connect(new URL(ipAddress));
        } else {
            connectWithIP(ipAddress);
        }
    }

    public CommandParser getParser() {
        return parser;
    }

    public void disconnect() {
        if(client == null) return;
        client.destroy();
        client = null;
        ParanoiaMessage.info("You have been disconnected from the Alpha Complex");
    }

    public boolean sendCommand(ParanoiaCommand command) {
        if(client != null && client.isOpen()) {
            client.sendMessage(command.toNetworkMessage(client.getAddress()));
            return true;
        } else {
            return false;
        }
    }

    public String getIP() {
        return client.getAddress();
    }

    @Override
    public void readInput(byte[] message) {
        try {
            ParanoiaCommand parsedCommand = ParanoiaCommand.parseCommand(message);
            //Parse command!
            parser.parse(parsedCommand);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fireTerminated() {
        disconnect();
    }
}
