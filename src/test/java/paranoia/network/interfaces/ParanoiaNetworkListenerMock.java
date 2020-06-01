package paranoia.network.interfaces;

public abstract class ParanoiaNetworkListenerMock {

    private boolean success = false;

    protected void succeed() {
        success = true;
    }

    public boolean testSuccess() {
        return success;
    }

}
