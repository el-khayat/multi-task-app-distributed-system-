import java.io.*;
import java.net.Socket;

public interface IUtilClient {
    public String[] setInfoFromFile(String filepath) throws IOException;
    public float[] setKernelFromFile(String filepath) throws IOException;
    public File setImage(String filepath);
    public float[][] ReadMatrice(); // read matrices, dimetion 
    public float[][] sendReciveMatrice(Socket socket,float[][] matrice);
    public void printMatrice(float[][] matice);
}
