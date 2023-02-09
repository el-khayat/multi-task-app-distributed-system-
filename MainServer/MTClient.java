import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class MTClient extends Thread {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    List<Data> filtredPartey = new ArrayList<Data>();
    Stack<Worker> slevers;
    IUtilServer util = new UtilServer();
    int id ;

    public MTClient(Socket socket,ObjectInputStream in,ObjectOutputStream out, Stack<Worker> slevers,int id) {
        this.socket = socket;
        this.slevers = slevers;
        this.id = id ;
        this.out = out ;
        this.in = in ;
        System.out.println("client coneccted" + Server.numberS);

    }

    @Override
    public void run() {
        try {
            Data data ;
            while(true){
                 data = (Data) in.readObject();

            switch (data.task) {
                case "matrice":
                System.out.println("matrice called .... !  ");
                util.matriceTraitement(socket, data, in, out);
                    break;
                case "convolution":
                    util.convolutionTraitement(socket, data, this.slevers, filtredPartey, in, out);
                    break; 
                case "chat":

                    util.resend(socket,data,id);
                    break;
                default:
                    break;
            }
            ;
        }
        } catch (Exception e) {
            System.out.println(e);
        }
}
}
