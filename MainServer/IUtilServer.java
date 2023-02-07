import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Stack;
import java.awt.image.BufferedImage;

public interface IUtilServer {
    public Stack<BufferedImage> Decouper(File image, int n) throws IOException;
    public byte[] Merge(List<Data> paries) throws IOException;
    public void DistToSlavers(Stack<BufferedImage> st, Stack<Worker> slavers, List<Data> filtredPartey, float[] kernel);
    public void getAvailabelSlavers(File file, Stack<Worker> slevers) throws IOException;
    public Data getItemById(int id, List<Data> list);
    public float[][] additionMatrice(float[][] matA, float[][] matB);
    public float[][] substractionMatrice(float[][] matA, float[][] matB);
    public float[][] multiplicationMatrice(float[][] matA, float[][] matB);
    public void matriceTraitement(Socket socket, Data data,ObjectInputStream in,ObjectOutputStream out);
    public void convolutionTraitement(Socket socket, Data data, Stack<Worker> slavers, List<Data> filtredPartey,ObjectInputStream in,ObjectOutputStream out);
    public byte[] fileToByte(File file)throws IOException;
    public File byteToFile(byte[] bytes,File file)throws IOException;
    public byte[] buffredImageToByte(BufferedImage bi )throws IOException;
    public BufferedImage byteToBuffredImage(byte[] bytes )throws IOException;
    public void resend(Socket socket,Data data,int id);
}