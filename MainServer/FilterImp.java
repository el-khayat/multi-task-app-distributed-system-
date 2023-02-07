//Implementing the remote interface
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


    public class FilterImp  implements IFilterRMI {
    String source = "./assets/imageResult.png";
    // Implementing the interface method
    public String printMsg() {
        System.out.println("Server: This is an example RMI program");
        return " Resut of Server ...... ";
    }

    public int add(int a, int b) {
        return a + b;
    }

    public File Grayscale(byte file[]) throws RemoteException {
        BufferedImage img = null;
        File f = null;
        System.out.println(" invoked \n");
        try {
            f = new File(source);
            FileOutputStream inf = new FileOutputStream(f);
            inf.write(file);

            img = ImageIO.read(f);
            inf.close();
        } catch (IOException e) {
            System.out.println(e);
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

                int avg = (r + g + b) / 3;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                img.setRGB(x, y, p);
            }
        }

        try {
            f = new File("C:\\Users\\Dell\\Documents\\StorageImages\\imageBefore.jpeg");
            ImageIO.write(img, "jpeg", f);
        } catch (IOException e) {
            System.out.println(e);
        }
        return f;
    }

    public byte[] negative(byte file[]) throws RemoteException {

        BufferedImage img = null;
        File f = null;
        byte[] fileR = new byte[100] ;
                System.out.println(" invoked \n");
        try {
            f = new File(source);
            FileOutputStream inf = new FileOutputStream(f);
            inf.write(file);

            img = ImageIO.read(f);
            inf.close();
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
                //int avg = (r+g+b)/3;
                Color newColor = new Color(red, green, blue);

                // p=(a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, newColor.getRGB());
            }
        }

        try {
            f = new File("C:\\Users\\Dell\\Documents\\StorageImages\\imageBefore.jpeg");
            ImageIO.write(img, "jpeg", f);
            FileInputStream fileInputStream = new FileInputStream(f);

            fileR = new byte[fileInputStream.available()];
            fileInputStream.read(fileR);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return fileR;
    }

    public File red(byte file[]) throws RemoteException {
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

        try {
            f = new File("C:\\Users\\Dell\\Documents\\StorageImages\\imageBefore.jpeg");
            ImageIO.write(img, "jpeg", f);
        } catch (IOException e) {
            System.out.println(e);
        }
        return f;
    }
    public File blue(byte file[]) throws RemoteException {
        BufferedImage img = null;
        File f = null;
        System.out.println("blue invoked \n");
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

                int red = c.getRed();
                int blue = c.getBlue()+50 >255?255:c.getBlue()+50;
                int green = c.getGreen();
                Color newColor = new Color(red, green, blue);

                // p=(a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, newColor.getRGB());
            }
        }

        try {
            f = new File("C:\\Users\\Dell\\Documents\\StorageImages\\imageBefore.jpeg");
            ImageIO.write(img, "jpeg", f);
        } catch (IOException e) {
            System.out.println(e);
        }
        return f;
    }
    public File green(byte file[]) throws RemoteException {
        BufferedImage img = null;
        File f = null;
        System.out.println("green invoked \n");
        try {
            f = new File(source);
            FileOutputStream inf = new FileOutputStream(f);
            inf.write(file);

            img = ImageIO.read(f);
            Thread.sleep(1000);
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

                int red = c.getRed();
                int blue = c.getBlue();
                int green = c.getGreen()+50 >255?255:c.getGreen()+50;
                Color newColor = new Color(red, green, blue);

                // p=(a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, newColor.getRGB());
            }
        }

        try {
            f = new File("C:\\Users\\Dell\\Documents\\StorageImages\\imageBefore.jpeg");
            ImageIO.write(img, "jpeg", f);
        } catch (IOException e) {
            System.out.println(e);
        }
        return f;
    }

    public File sepia(byte file[]) throws RemoteException {
        BufferedImage img = null;
        File f = null;
        System.out.println("sepia invoked \n");
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

        try {
            f = new File("C:\\Users\\Dell\\Documents\\StorageImages\\imageBefore.jpeg");
            ImageIO.write(img, "jpeg", f);
        } catch (IOException e) {
            System.out.println(e);
        }
        return f;
    }

    public File merge(byte file[]) throws RemoteException {

        File f2 = null ;
        try {

            BufferedImage img = null;
            File f = null;
            System.out.println("megge  invoked \n");


            f = new File(source);
            FileOutputStream inf = new FileOutputStream(f);
            inf.write(file);
            img = ImageIO.read(f);
            Thread.sleep(3000);
            inf.close();

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




            f2 = new File("C:\\Users\\Dell\\Documents\\StorageImages\\imageBefore.jpeg");
            ImageIO.write(img, "jpeg", f2);

        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return f2;
    }
}


