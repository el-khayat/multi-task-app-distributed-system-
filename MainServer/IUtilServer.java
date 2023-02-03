import java.io.File;
import java.io.IOException;
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
    public void matriceTraitement(Socket socket, Data data);
    public void convolutionTraitement(Socket socket, Data data);
}