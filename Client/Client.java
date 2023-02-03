import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    int port ;
    String host ;

    File image ;// read from command line 
    String dis = "./result.jpeg" ;
    float[] Kernel ;    
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    IUtilClient util = new UtilClient(); 

    

    
    public void sendRecieveData() throws UnknownHostException, IOException, ClassNotFoundException{
        
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        // = = = = == === ==== ====== ======== ======== ===== 
        
        Data data = new Data();
        FileInputStream fileInputStream = new FileInputStream(image);
        byte[] b = new byte[fileInputStream.available()];
        fileInputStream.read(b);
        fileInputStream.close();

        data.setF(b);
        data.setArrayKirnel(Kernel);
        data.setHegth((int)Math.sqrt(Kernel.length));
        data.setWidth((int)Math.sqrt(Kernel.length));
        out.writeObject(data);
        out.flush();
                    
        data=(Data)in.readObject();
        File file = new File(dis);
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(data.getF());
        fileOutputStream.close();
        socket.close();
    }
    public Client(String[] args)throws  IOException{
            
            String[] config = util.setInfoFromFile(args[0]);
            this.host = config[0];
            this.port = Integer.parseInt(config[1]);
            this.image = util.setImage(args[1]);
            this.Kernel = util.setKernelFromFile(args[2]);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        if (args.length < 3) {
         System.out.println(" Error : There is a missing argement ");  
         System.exit(-1); 
        }
        Client client = new Client(args);
        
        // start timer 
        long start = System.currentTimeMillis();
        client.sendRecieveData();
        long now = System.currentTimeMillis();
        System.out.println(" duree est ");
        System.out.println(now-start);       
    }
}