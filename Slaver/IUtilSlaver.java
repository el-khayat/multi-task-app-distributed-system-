import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public interface IUtilSlaver {
    public BufferedImage filter(byte[] file, Kernel kernel) throws IOException;

    public int setInfoFromFile(String filepath) throws IOException;

    public BufferedImage byteToBuffredImage(byte[] bytes) throws IOException;

}
