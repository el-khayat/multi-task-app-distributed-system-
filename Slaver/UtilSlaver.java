import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class UtilSlaver implements IUtilSlaver {

    public BufferedImage filter(byte[] file, Kernel kernel) throws IOException {

        BufferedImage image2 = byteToBuffredImage(file);
        ConvolveOp convolution = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage resultat = convolution.filter(image2, null);

        return resultat;
    }

    public int setInfoFromFile(String filepath) throws IOException {
        int port = 3334;
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        while (line != null) {
            line = br.readLine();
            if (line == null)
                break;
            port = Integer.parseInt(line);
        }
        br.close();
        return port;
    }

    @Override
    public BufferedImage byteToBuffredImage(byte[] bytes) throws IOException {
        BufferedImage bufferedImage = null;
        InputStream is = new ByteArrayInputStream(bytes);
        bufferedImage = ImageIO.read(is);
        return bufferedImage;
    }
}