package program;

import java.net.Socket;

public class SocketClient {
    private String ime;
    private Socket sock;
    public SocketClient(String ime, Socket sock){
        this.ime = ime;
        this.sock = sock;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }
}
