import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

class ClinetManager extends Thread {
    Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    IUtilSlaver util = new UtilSlaver();

    public ClinetManager(Socket socket) {
        this.socket = socket;
        System.out.println("client coneccted");
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            Data data = (Data) in.readObject();
            long start = System.currentTimeMillis();

            System.out.println("id is " + data.id);
            int id = data.id;
            Kernel kernel = new Kernel((int) Math.sqrt(data.arrayKirnel.length),
                    (int) Math.sqrt(data.arrayKirnel.length), data.arrayKirnel);
            BufferedImage res = util.filter(data.f, kernel);
            File f = new File("./retImage.jpeg");
            ImageIO.write(res, "jpeg", f);

            FileInputStream inf = new FileInputStream(f);
            byte b[] = new byte[inf.available()];
            inf.read(b);

            data = new Data();
            data.setF(b);
            data.setId(id);
            out.writeObject(data);
            out.flush();
            long now = System.currentTimeMillis();
            System.out.println(" duree est ");
            System.out.println(now - start);
            inf.close();
            // =======
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
