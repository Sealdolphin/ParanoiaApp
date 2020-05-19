package paranoia.network.interfaces;

import paranoia.services.technical.command.DisconnectCommand;

public class DisconnectListenerMock implements
    DisconnectCommand.ParanoiaDisconnectListener,
    ParanoiaNetworkListenerMock
{

    private boolean success = false;

    @Override
    public void disconnect() {
        success = true;
    }

    @Override
    public boolean testSuccess() {
        return success;
    }
}
