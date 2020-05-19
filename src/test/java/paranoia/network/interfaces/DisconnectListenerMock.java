package paranoia.network.interfaces;

import paranoia.services.technical.Network;
import paranoia.services.technical.command.DisconnectCommand;

public class DisconnectListenerMock implements
    DisconnectCommand.ParanoiaDisconnectListener,
    ParanoiaNetworkListenerMock
{

    public DisconnectListenerMock() {
        this(null);
    }

    public DisconnectListenerMock(Network network) {
        this.network = network;
    }

    private final Network network;
    private boolean success = false;

    @Override
    public void disconnect() {
        if(network != null)
            network.disconnect();
        success = true;
    }

    @Override
    public boolean testSuccess() {
        return success;
    }
}
