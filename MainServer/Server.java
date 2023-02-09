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
    static List<ObjectOutputStream> outs = new ArrayList<ObjectOutputStream>();
    static List<ObjectInputStream> ins = new ArrayList<ObjectInputStream>();

    public Server() throws IOException {
        util.getAvailabelSlavers(new File("./slavers.txt"), slevers);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(3336);
            System.out.println("server is running at port 9999");
            IFilterRMI obj = new FilterImp();
            IFilterRMI skeleton  = (IFilterRMI) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.createRegistry(9999);
            registry.bind("Test", skeleton );
            
            while (true){
                Socket socket = serverSocket.accept() ;
                members.add(socket);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ins.add(in);
                outs.add(out);
                new MTClient(socket,in,out,slevers,id++).start();
                System.out.println("=======| ++++++++++++=====================");

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
