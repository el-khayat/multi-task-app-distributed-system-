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
    public  byte[]   applyFilterRMI(byte[] image,String host,int port){
            try{
                Scanner sc = new Scanner(System.in);
                System.out.print(" Enter the filter ");
                String filter = sc.nextLine();
                Registry registry = LocateRegistry.getRegistry("localhost",9999); 
                IFilterRMI stub = (IFilterRMI) registry.lookup("Test"); 

                switch(filter){
                    case "gray"  :
                        image = stub.Grayscale(image) ;
                        break;
                case "negative"  :
                        image = stub.negative(image);
                    break;
                case "red"  :
                        image = stub.red(image);
                        break;                                
                case "sepia"  :
                        image = stub.sepia(image);
                        break;
                case "blue"  :
                        image = stub.blue(image);
                        break;
                case "green"  :
                        image = stub.green(image);
                        break;
                default:
                        System.out.println("Your choise Doesn't exist ");
                        applyFilterRMI(image, host, port);
                        break;

            }
            sc.close();
                                
        }catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      
         return image;   
     }
    
     public void getTask() throws UnknownHostException, ClassNotFoundException, IOException{
        Scanner cs = new Scanner(System.in);
        System.out.println(" choise a task  ");
        System.out.println(" 0 : for Convolution ");
        System.out.println(" 1 : for Operation Of Matrix ");
        System.out.println(" 2 : for Filter Images ");
        int choise = cs.nextInt();
        switch (choise) {
            case 0:
            applyConvOnImage();
                break;
            case 1:
            matriceOperation();
                break;
            case 2:
            File image = new File("./assets/img.jpeg");
            File filtredImage = new File("./assets/result.jpeg");
            byte[] res = applyFilterRMI(util.fileToByte(image),"localhost",9999);
            util.byteToFile(res, filtredImage);
                break;
            default:
            System.out.println("opps it look like an error was happen ");
                break;
        }
        cs.close();
}
     public static void main(String[] args) throws IOException, ClassNotFoundException {


        if (args.length < 3) {
         System.out.println(" Error : There is a missing argement ");  
         System.exit(-1); 
        }
        Client client = new Client(args);
        client.getTask();
        
        // start timer 
        long start = System.currentTimeMillis();
        
        
        
        long now = System.currentTimeMillis();
        System.out.println(" duree est ");
        System.out.println(now-start);       
    }
}