import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.awt.image.BufferedImage;

class MTClient extends Thread{
    Socket socket ;
    ObjectInputStream in ;
    ObjectOutputStream out ;
    List<Data> filtredPartey =new ArrayList<Data>();
    Stack<Worker>  slevers ;
    IUtilServer util = new UtilServer();
    public MTClient(Socket socket,Stack<Worker>  slevers){
        this.socket = socket;
        this.slevers = slevers ;
        System.out.println("client coneccted"+Server.numberS);
        

    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
           
            Data data = (Data) in.readObject();

            switch (data.task) {
                case "matrice":

                    util.matriceTraitement(socket,data,in,out);
                    break;
                case "convolution":

                    util.convolutionTraitement(socket,data,this.slevers,filtredPartey,in,out);
                break;
                default:
                    break;
            };
            
            //=======
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
