import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class UtilClient implements IUtilClient {

    @Override
    public String[] setInfoFromFile(String filepath) throws IOException{
        File file = new File(filepath);
        String config[] = new String[2];

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line ="";
        while (line != null){
            
            line = br.readLine();
            if (line ==null)  break;              
            String [] lineStrings = line.split(";");
            config[0]  = lineStrings[0];
            config[1] = lineStrings[1];
        }
        br.close();
        return config;
    }

    @Override
    public float[] setKernelFromFile(String filepath) throws IOException{
        File file = new File(filepath);
        float[] kernel  =null;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String lineKernel ="";
        
        while (lineKernel != null){
            lineKernel = br.readLine();
            if (lineKernel ==null)  break;              
            String [] lineStrings = lineKernel.split(";");
            kernel = new float[lineStrings.length];
            for(int i = 0 ; i< lineStrings.length;++i)
                kernel[i] =Float.parseFloat(lineStrings[i]); 
        }
        br.close();
        return kernel ;
    }

    @Override
    public File setImage(String filepath) {
           return new File(filepath);
    }

    @Override
    public float[][] ReadMatrice() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public float[][] sendReciveMatrice(Socket socket, Data data) {
        // TODO Auto-generated method stub

        ObjectInputStream in = new ObjectOutputStream(socket.getOutputStream());
        ObjectOutputStream out = new ObjectInputStream(socket.getInputStream());
        //send
        out.writeObject(data);
        out.flush();
        //receive
        data= (Data) in.readObject();
        System.out.println("le resultat est renvoyer du serveur" );


        return data.Res;
    }

    @Override
    public void printMatrice(float[][] matice) {
        // TODO Auto-generated method stub
        
    }
    
}
