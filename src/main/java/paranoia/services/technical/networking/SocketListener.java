package paranoia.services.technical.networking;

public interface SocketListener {

    void fireTerminated();

    void readInput(byte[] message);

}
