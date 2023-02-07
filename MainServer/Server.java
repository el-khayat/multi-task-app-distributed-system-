import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends FilterImp implements Runnable {
    ServerSocket serverSocket;

    public static int He;
    public static int Wi;
    public static int numberS = 1;
    int id = 1 ;
    Stack<Worker> slevers = new Stack<Worker>();
    IUtilServer util = new UtilServer();
    static List<Socket> members = new ArrayList<Socket>();

    public Server() throws IOException {
        util.getAvailabelSlavers(new File("./slavers.txt"), slevers);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(3336);
            System.out.println("server is running at port 3334");
            IFilterRMI obj = new FilterImp();
            IFilterRMI skeleton  = (IFilterRMI) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.createRegistry(9999);
            registry.bind("Test", skeleton );
            
            while (true){
                Socket socket = serverSocket.accept() ;
                members.add(socket);
                new MTClient(socket, slevers,id++).start();
            }
        } catch (Exception e) {
            System.out.println(" Error : Client disconnect !");
        }

    }

    public static void main(String[] args) throws IOException {

        Thread server = new Thread(new Server());
        server.start();
    }
}
