//Implementing the remote interface
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


    public class FilterImp  implements IFilterRMI {
    String source = "./assets/imageResult.png";
    IUtilServer util = new UtilServer();

    public byte[] Grayscale(byte file[]) throws RemoteException,IOException {
        BufferedImage img = null;
        System.out.println(" Grayscale invoked \n");
        img = util.byteToBuffredImage(file);
        int width = img.getWidth();
        int height = img.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                int avg = (r + g + b) / 3;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                img.setRGB(x, y, p);
            }
        }
        return util.buffredImageToByte(img);
    }
    public byte[] negative(byte file[]) throws RemoteException,IOException {

        BufferedImage img = null;
                System.out.println(" invoked \n");
        try {
            img = util.byteToBuffredImage(file);
            Thread.sleep(1000);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(img.getRGB(x, y));
                int red = 255 - c.getRed();
                int blue = 255 - c.getBlue();
                int green = 255 - c.getGreen();
                Color newColor = new Color(red, green, blue);
                img.setRGB(x, y, newColor.getRGB());
            }
        }

        
        return util.buffredImageToByte(img);
    }
    public byte[] red(byte file[]) throws RemoteException,IOException {
        BufferedImage img = null;
        File f = null;
        System.out.println(" invoked \n");
        try {
            f = new File(source);
            FileOutputStream inf = new FileOutputStream(f);
            inf.write(file);

            img = ImageIO.read(f);
            Thread.sleep(3000);
            inf.close();
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(img.getRGB(x, y));

                int red = c.getRed()+50 >255?255:c.getRed()+50;
                int blue = c.getBlue();
                int green = c.getGreen();
                Color newColor = new Color(red, green, blue);

                // p=(a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, newColor.getRGB());
            }
        }

        return util.buffredImageToByte(img);
    }
    public byte[] blue(byte file[]) throws RemoteException,IOException {
        BufferedImage img = null;
        System.out.println("blue invoked \n");
        try {
    
            img = util.byteToBuffredImage(file);
            Thread.sleep(3000);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(img.getRGB(x, y));

                int red = c.getRed();
                int blue = c.getBlue()+50 >255?255:c.getBlue()+50;
                int green = c.getGreen();
                Color newColor = new Color(red, green, blue);

                // p=(a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, newColor.getRGB());
            }
        }

        return util.buffredImageToByte(img);
    }
    public byte[] green(byte file[]) throws RemoteException,IOException {
        BufferedImage img = null;
        try {
            
            img = util.byteToBuffredImage(file);
            Thread.sleep(1000);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color c = new Color(img.getRGB(x, y));

                int red = c.getRed();
                int blue = c.getBlue();
                int green = c.getGreen()+50 >255?255:c.getGreen()+50;
                Color newColor = new Color(red, green, blue);

                // p=(a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, newColor.getRGB());
            }
        }
        return util.buffredImageToByte(img);
    }
    public byte[] sepia(byte file[]) throws RemoteException,IOException {
        BufferedImage img = null;
        System.out.println("sepia invoked \n");
        try {
            
            img = util.byteToBuffredImage(file);
            Thread.sleep(3000);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int) (0.343 * r + 0.689 * g + 0.168 * b);
                int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);
                if (tr > 255) {
                    r = 255;
                } else {
                    r = tr;
                }

                if (tg > 255) {
                    g = 255;
                } else {
                    g = tg;
                }

                if (tb > 255) {
                    b = 255;
                } else {
                    b = tb;
                }
                p = (a << 24) | (r << 16) | (g << 8) | b;

                img.setRGB(x, y, p);
            }
        }
        return util.buffredImageToByte(img);
    }
    public byte[] merge(byte file[]) throws RemoteException,IOException {
        BufferedImage img = null;
           
            img = util.byteToBuffredImage(file);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int width = img.getWidth();

            for (int y = 0; y < img.getHeight(); y++) {
                // fetch a line of data from each image

                // apply the mask
                for (int x = 0; x < width; x++) {
                    Color c = new Color(img.getRGB(x, y));

                    int red = c.getRed()    +    88 > 255 ? 255 :c.getRed() + 88 ;
                    int blue = c.getBlue()  +    38 > 255 ? 255 :c.getBlue() + 38;
                    int green = c.getGreen()+    11 > 255 ? 255 :c.getGreen() + 11;
                    int alpha = c .getAlpha();


                    //int avg = (r+g+b)/3;
                    Color newColor = new Color(red, green, blue,alpha);

                    // p=(a<<24) | (avg<<16) | (avg<<8) | avg;

                    img.setRGB(x, y, newColor.getRGB());
                }


            }

            
        
        return util.buffredImageToByte(img);
    }
}


