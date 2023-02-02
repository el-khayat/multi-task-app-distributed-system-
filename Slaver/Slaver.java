import java.io.*;
import java.net.ServerSocket;

public class Slaver extends Thread {
    ServerSocket serverSocket ;
    int port ;
    IUtilSlaver util = new UtilSlaver();
    public Slaver(String[] args) throws IOException{
        this.port = util.setInfoFromFile(args[0]);
    }
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(3334);
            System.out.println("server is running at port 3334");
            while (true)
                new ClinetManager(serverSocket.accept()).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        new Slaver(args).start();
    }
}