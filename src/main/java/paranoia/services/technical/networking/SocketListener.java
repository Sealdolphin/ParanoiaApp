package paranoia.services.technical.networking;

public interface SocketListener {

    void fireTerminated();

    void readInput(String client, String message);

}