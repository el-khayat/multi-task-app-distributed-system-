import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client{
    int port ;
    String host ;

    File image ;// read from command line 
    String dis = "./result.jpeg" ;
    float[] Kernel ;    
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    IUtilClient util = new UtilClient(); 

    

    
    public void applyConvOnImage() throws UnknownHostException, IOException, ClassNotFoundException{
        
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
        data.setTask("convolution");
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
    
    public void matriceOperation() throws UnknownHostException, IOException, ClassNotFoundException{
        socket = new Socket(this.host,this.port);

        System.out.println(" chose your operation +, - or *");
        Scanner sc = new Scanner(System.in);
        char operation = sc.nextLine().charAt(0);
        System.out.println(" enter matrice's size ");
        int  size = sc.nextInt();
        float[][] matA = util.ReadMatrice("enter the first matrice ",size);
        float[][] matB = util.ReadMatrice("enter the second matrice ",size);
        Data data = new Data(matA,matB,operation);
        data.setTask("matrice");
        
        float [][] result = util.sendReciveMatrice(socket, data);
        
            util.printMatrice(result);
            sc.close();
            socket.close();
    }
    public  File   applyFilterRMI(File image,String filter,String host,int port){
        byte[] br = new byte[100];
            try{
                
        Registry registry = LocateRegistry.getRegistry(host,port); 
        IFilterRMI stub = (IFilterRMI) registry.lookup("Test"); 

         // Looking up the registry for the remote object   File image = new File("imqge.jpg")                                 
                                FileInputStream inf = new FileInputStream(image);
                                 byte b[] = new byte[inf.available()];
                                 inf.read(b);
                                 inf.close();
                                switch(filter){
                                    case "gray"  :
                                     image = stub.Grayscale(b);
                                        break;
                                case "negative"  :
                                    br = stub.negative(b);
                                    FileOutputStream fileOutputStream = new FileOutputStream(image);
                                    fileOutputStream.write(br);
                                    return image;
                                    
                                case "red"  :
                                     image = stub.green(b);
                                        break;                                
                                case "sepia"  :
                                     image = stub.sepia(b);
                                        break;
                                case "blue"  :
                                     image = stub.sepia(b);
                                        break;
                                case "green"  :
                                     image = stub.sepia(b);
                                        break;
                                        
                                }
                                
        }catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      }
         return image;   
     }
    public static void main(String[] args) throws IOException, ClassNotFoundException {


        if (args.length < 3) {
         System.out.println(" Error : There is a missing argement ");  
         System.exit(-1); 
        }
        Client client = new Client(args);
        
        // start timer 
        long start = System.currentTimeMillis();
        String file = "./assets/img.jpeg";
        File image = new File(file);
        File result = new File("./assets/result.jpeg");
        result = client.applyFilterRMI(image,"gray","localhost",9999);

        long now = System.currentTimeMillis();
        System.out.println(" duree est ");
        System.out.println(now-start);       
    }
}