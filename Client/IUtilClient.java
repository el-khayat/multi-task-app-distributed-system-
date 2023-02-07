import java.io.*;
import java.net.Socket;
import java.awt.image.BufferedImage;

public interface IUtilClient {
    public String[] setInfoFromFile(String filepath) throws IOException;
    public float[] setKernelFromFile(String filepath) throws IOException;
    public File setImage(String filepath);
    public float[][] ReadMatrice(String text,int size); // read matrices, dimetion, operation, 
    public float[][] sendReciveMatrice(Socket socket, Data data) throws IOException, ClassNotFoundException;
    public void printMatrice(float[][] matice);
    public byte[] fileToByte(File file)throws IOException;
    public File byteToFile(byte[] bytes,File file)throws IOException;
    public byte[] buffredImageToByte(BufferedImage bi )throws IOException;
    public BufferedImage byteToBuffredImage(byte[] bytes )throws IOException;
    
}
