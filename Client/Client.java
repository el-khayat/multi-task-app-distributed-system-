import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements IUtilClient{
    int port ;
    String host ;

    File image ;// read from command line 
    String dis = "./result.jpeg" ;
    float[] Kernel ;    
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;

    public void setInfoFromFile(String filepath) throws IOException{
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line ="";
        while (line != null){
            
            line = br.readLine();
            if (line ==null)  break;              
            String [] lineStrings = line.split(";");
            this.host = lineStrings[0];
            this.port = Integer.parseInt(lineStrings[1]);
        }
        br.close();
    }
    public void setKernelFromFile(String filepath) throws IOException{
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String lineKernel ="";
        
        while (lineKernel != null){
            lineKernel = br.readLine();
            if (lineKernel ==null)  break;              
            String [] lineStrings = lineKernel.split(";");
            Kernel = new float[lineStrings.length];
            for(int i = 0 ; i< lineStrings.length;++i)
                this.Kernel[i] =Float.parseFloat(lineStrings[i]); 
        }
        br.close();
    }
    public void setImage(String filepath){
        this.image = new File(filepath);
    }
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
            setInfoFromFile(args[0]);
            setImage(args[1]);
            setKernelFromFile(args[2]);        
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