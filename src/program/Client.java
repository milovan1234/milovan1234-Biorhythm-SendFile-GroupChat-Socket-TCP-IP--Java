package program;


import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Client extends Thread {
    public static final int TCP_PORT = 9000;
    private static Socket sock;
    private static Socket tmp;
    private BufferedReader in;
    private PrintWriter out;
    private InetAddress addr;
    private OutputStream os;
    private ObservableList<String> listaPoruka;
    public Client(){

    }
    public Client(ObservableList<String> listaPoruka) {
        this.listaPoruka = listaPoruka;
    }
    public String racunajBioritam(long brojDana){
        try {
            addr = InetAddress.getByName("127.0.0.1");
            sock = new Socket(addr, TCP_PORT);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
            String request = "Zadatak1:" + brojDana;
            System.out.println("[Client]: " + request);
            out.println(request);
            String response = in.readLine();
            System.out.println("[Server]: " + response);
//            in.close();
//            out.close();
            return response;
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
    public boolean posaljiFajlNaServer(String fajl){
        try {
            addr = InetAddress.getByName("127.0.0.1");
            sock = new Socket(addr, TCP_PORT);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
            File myFile = new File("DATA_KLIJENT/" + fajl);
            String request = "Zadatak2:" + fajl;
            System.out.println("[Client]: " + request);
            out.println(request);
            String response = in.readLine();
            System.out.println("[Server]: " + response);
            if(response.equals("Nastavak")){
                byte[] mybytearray = new byte[0];
                try {
                    mybytearray = Files.readAllBytes(myFile.toPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    os = sock.getOutputStream();
                    os.write(mybytearray, 0, mybytearray.length);
                } finally {
                    if (os != null) os.close();
                }
            }
//            in.close();
//            out.close();
            return true;
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }


    public String posaljiPoruku(String poruka){
        try {
            addr = InetAddress.getByName("127.0.0.1");
            sock = new Socket(addr, TCP_PORT);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String request = "Zadatak3:" + poruka;
            System.out.println("[Client]: " + request);
            out.println(request);
            String response = in.readLine();
            return response;
        } catch (UnknownHostException e1) {
                e1.printStackTrace();
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println("Milovan");
            String response = "";
            while (true){
                if((response = in.readLine()) != null) {
                    System.out.println("POSLATA PORUKA: " + response);
                    String finalResponse = response;
                    Platform.runLater(() -> listaPoruka.add(finalResponse));
                }
            }
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
