package paranoia.visuals;

import paranoia.services.technical.command.HelloCommand;
import paranoia.services.technical.command.PingCommand;
import paranoia.services.technical.networking.Network;
import paranoia.visuals.messages.ParanoiaMessage;
import paranoia.visuals.panels.LobbyPanel;
import paranoia.visuals.panels.acpf.ACPFPanel;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class LobbyFrame extends JFrame implements
    PingCommand.ParanoiaPingListener,
    HelloCommand.ParanoiaInfoListener
{

    private final Network network;
    private final String name;

    public LobbyFrame(Network network, String player) {
        this.network = network;
        this.name = player;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 400));
        setTitle("Alpha Complex Personal Lobby");

        network.getParser().setPingListener(this);
        network.getParser().setInfoListener(this);
        LobbyPanel lobbyPanel = new LobbyPanel(this, network, name);

        setLayout(new BorderLayout());
        add(lobbyPanel.createInfoPanel(), BorderLayout.NORTH);
        add(new JScrollPane(lobbyPanel.createLobbyPanel()), BorderLayout.EAST);
        add(new ACPFPanel(network), BorderLayout.CENTER);
        pack();
    }

    @Override
    public void pong() {
        network.sendCommand(new PingCommand());
    }

    @Override
    public void sayHello(String player, String password, boolean hasPassword) {
        String pass = null;
        if(hasPassword) {
            pass = ParanoiaMessage.input("Enter server password");
        }

        network.sendCommand(new HelloCommand(name, pass, hasPassword, null));
    }
}
