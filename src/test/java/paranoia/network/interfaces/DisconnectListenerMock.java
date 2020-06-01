package paranoia.network.interfaces;

import paranoia.services.technical.command.DisconnectCommand;

public class DisconnectListenerMock
    extends ParanoiaNetworkListenerMock
    implements DisconnectCommand.ParanoiaDisconnectListener
{
    @Override
    public void disconnect() {
        succeed();
    }
}
