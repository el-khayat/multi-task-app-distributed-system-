import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class UtilSlaver implements IUtilSlaver{

    public  BufferedImage filter(byte[] file,Kernel kernel) throws IOException {
        File image = new File("tmpFile.jpeg");
        FileOutputStream fileOutputStream = new FileOutputStream(image);
        fileOutputStream.write(file);

        BufferedImage image2 = ImageIO.read(image);
        ConvolveOp convolution = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP,null);
        BufferedImage resultat=convolution.filter(image2, null);
        fileOutputStream.close(); 

        return resultat;
    }
    public int setInfoFromFile(String filepath) throws IOException{
        int port =3334;
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line ="";
        while (line != null){
            line = br.readLine();
            if (line ==null)  break;              
            port = Integer.parseInt(line); 
        }
        br.close();
        return port ;
    }
}