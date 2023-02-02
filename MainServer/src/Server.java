import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Server extends Thread {
    ServerSocket serverSocket ;

    public static int He;
    public static int Wi;
    public static int numberS = 1 ;
    Stack<Worker>  slevers = new Stack<Worker>();
    IUtilServer util = new UtilServer();
    
    public Server() throws IOException {
        util.getAvailabelSlavers(new File("./slavers.txt"), slevers);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(3336);
            System.out.println("server is running at port 3334");
            while (true)
                new MTClient(serverSocket.accept(),slevers).start();
        } catch (IOException e) {
           System.out.println(" Error : Client disconnect !");
        }

    }

    public static void main(String[] args) throws IOException {
        new Server().start();
    }
}
