package program;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static final int TCP_PORT = 9000;
    private static ArrayList<SocketClient> sockets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
            System.out.println("Server running...");
            ServerSocket ss = new ServerSocket(TCP_PORT);
            while (true) {
                    boolean check = false;
                    Socket sock = ss.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())),true);
                    String request = in.readLine();
                    System.out.println(request);
                    String[] arrRequest = request.split(":");
                    if(arrRequest[0].equals("Zadatak1")){
                        int brojDana = Integer.parseInt(arrRequest[1]);
                        int fizicki = (int)(Math.sin(2 * Math.PI * brojDana / 23) * 100);
                        int emocionalni = (int)(Math.sin(2 * Math.PI * brojDana / 28) * 100);
                        int intelektualni = (int)(Math.sin(2 * Math.PI * brojDana / 33) * 100);
                        String response = fizicki + ":" + emocionalni + ":" + intelektualni;
                        out.println(response);
                    }
                    else if(arrRequest[0].equals("Zadatak2")){
                        String fajl = "DATA_SERVER/" + arrRequest[1];
                        out.println("Nastavak");
                        InputStream is = sock.getInputStream();
                        FileOutputStream fos = new FileOutputStream(fajl);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        byte[] mybytearray = new byte[1000000];
                        int bytesRead = is.read(mybytearray, 0, mybytearray.length);
                        bos.write(mybytearray, 0, bytesRead);
                        bos.flush();
                        fos.close();
                        is.close();
                    }
                    else if(arrRequest[0].equals("Zadatak3")) {
                        String poruka = arrRequest[1];
                        String ime = arrRequest[2];
                        boolean ispravno = true;
                        if (poruka.equals("Prijava")) {
                            if(sockets.size() < 2) {
                                sockets.add(new SocketClient(ime, sock));
                                check = true;
                                out.println("Ispravno");
                            }
                            else {
                                ispravno = false;
                                out.println("PunaGrupa");
                            }
                        } else if (poruka.equals("Odjava")) {
                            ArrayList<SocketClient> tmpsockets = new ArrayList<>();
                            for (SocketClient s : sockets) {
                                if (!s.getIme().equals(ime)) {
                                    tmpsockets.add(s);
                                }
                            }
                            sockets = tmpsockets;
                            out.println("Ispravno");
                        }
                        if(ispravno) {
                            System.out.println(sockets.size());
                            for (SocketClient s : sockets) {
                                PrintWriter out1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getSock().getOutputStream())), true);
                                out1.println(ime + ": " + poruka);
                            }
                        }
                    }
                    if(!check){
                        in.close();
                        out.close();
                        sock.close();
                    }
                }
        }
}
