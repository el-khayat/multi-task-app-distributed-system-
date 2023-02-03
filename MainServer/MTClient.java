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
    float[] kernel;
    int hi;
    int we;
    public MTClient(Socket socket,Stack<Worker>  slevers){
        this.socket = socket;
        this.slevers = slevers ;
        System.out.println("client coneccted"+Server.numberS);
        

    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out= new ObjectOutputStream(socket.getOutputStream()) ;
            Data data = (Data) in.readObject();
            switch (data.task) {
                case "matrice":
                    util.matriceTraitement(socket,data);
                    break;
                case "convolution":
                    util.convolutionTraitement(socket,data);
                break;
                default:
                    break;
            }
            this.kernel=data.arrayKirnel;
            this.hi=data.hegth;
            this.we=data.width;
            //========

            File image = new File("./ImageServerinit.jpeg");
            FileOutputStream outf = new FileOutputStream(image);
            outf.write(data.f);
            outf.close();

            Stack<BufferedImage> st = util.Decouper(image,Server.numberS);
            
            util.DistToSlavers(st,slevers,filtredPartey,kernel);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                       //sleep(5000);
                        while (true){
                            if (filtredPartey.size() < Server.numberS){
                                System.out.print("");
                                continue;
                            }
                            data.setF(util.Merge(filtredPartey));
                            out.writeObject(data);
                            out.flush();
                            break;
                        }
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    }

            }).start();
            //=======
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
