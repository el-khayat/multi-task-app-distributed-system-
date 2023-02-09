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
    Scanner sc ;
    String name ;
    

    

    
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
            this.sc = new Scanner(System.in);
    }
    
    public void matriceOperation() throws UnknownHostException, IOException, ClassNotFoundException{
        socket = new Socket(this.host,this.port);
        Scanner sc = new Scanner(System.in);
        System.out.println(" chose your operation +, - or *");
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
                Registry registry = LocateRegistry.getRegistry("localhost",9999); 
                IFilterRMI stub = (IFilterRMI) registry.lookup("Test"); 
                System.out.print(" Enter the filter ");
                String filter ;
                Scanner scanner = new Scanner(System.in);
                filter = scanner.nextLine();

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
            scanner.close();
                                
        }catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      
         return image;   
     }
    public void joinToRoom() throws IOException{
        Socket socket = new Socket(this.host,this.port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        new Thread(new Runnable() {
            String message  ="";

            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter your name   ");
                name = scanner.nextLine();
                System.out.println("You are Joined as "+name);

                while (!message.equals("stop")) {
                    Data data = new Data();
                    data.setTask("chat");

                        try {
                        message = sc.nextLine();
                            if (message.equals("") || message == null) {
                                continue;
                            }
                            
                            data.setMessage(name+" :" +message) ;
                            out.writeObject(data);
                            out.flush();
                            System.out.println("\n message est "+message);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    scanner.close();
            }
            
        },"send").start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                    String message ;
                    while (true) {
                       
                        try {
                            Data data = (Data) in.readObject();
                            message = data.getMessage();
                            System.out.println("=> "+message);
                        } catch (Exception e) {

                            throw new RuntimeException(e);
                        }
                    }                
            }
            
        },"receive").start();
    }
     public void getTask() throws UnknownHostException, ClassNotFoundException, IOException{
        System.out.println(" choise a task  ");
        System.out.println(" 0 : for Convolution ");
        System.out.println(" 1 : for Operation Of Matrix ");
        System.out.println(" 2 : for Filter Images ");
        System.out.println(" 3 : for Room Chat ");
        int choise = sc.nextInt();
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
            case 3:
                joinToRoom();

                break;
            default:
            System.out.println("opps it look like an error was happen ");
                break;
        }
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