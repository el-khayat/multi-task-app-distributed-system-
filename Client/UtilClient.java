import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
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
    public float[][] ReadMatrice(String text,int size) {
        System.out.println(text);
        final Scanner sc = new Scanner(System.in);
                        float[][] a = new float[][]{{0, 0}, {0, 0}};
                            System.out.println("Entrez  Matrice");
                            int i;
                            int j;
                            for(i = 0; i <size ; ++i) {
                                for(j = 0; j < size; ++j) {
                                    System.out.println("  M[" + i + "][" + j + "] =  ");
                                    a[i][j] = Integer.parseInt(sc.next());
                                }
                            }
                  return a ;      
    }

    @Override
    public float[][] sendReciveMatrice(Socket socket, float[][] matrice) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void printMatrice(float[][] matice) {
        // TODO Auto-generated method stub
        
    }
    
}
