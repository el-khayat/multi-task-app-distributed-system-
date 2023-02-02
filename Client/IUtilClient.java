import java.io.*;

public interface IUtilClient {
    public void setInfoFromFile(String filepath) throws IOException;
    public void setKernelFromFile(String filepath) throws IOException;
    public void setImage(String filepath);
}
