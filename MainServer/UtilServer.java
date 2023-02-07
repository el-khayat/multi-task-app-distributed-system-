import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class UtilServer implements IUtilServer {
    ObjectInputStream in;
    ObjectOutputStream out;

    public Stack<BufferedImage> Decouper(File image, int n) throws IOException {
        Stack<BufferedImage> imageDivs = new Stack<BufferedImage>();
        BufferedImage bufferedImage = ImageIO.read(image);
        int he = bufferedImage.getHeight();
        int wi = bufferedImage.getWidth();
        Server.He = he;
        Server.Wi = wi;
        for (int i = 0; i < n; i++) {
            BufferedImage tmp_Recorte = ((BufferedImage) bufferedImage).getSubimage(0, i * (he / n), wi, he / n);
            imageDivs.push(tmp_Recorte);

        }
        return imageDivs;
    }

    public byte[] Merge(List<Data> paries) throws IOException {

        int x = 0, y = 0;
        BufferedImage result = new BufferedImage(
                Server.Wi, Server.He, // work these out
                BufferedImage.TYPE_INT_RGB);

        Data data;
        for (int i = paries.size(); i >= 1; --i) {

            data = getItemById(i, paries);

            InputStream is = new ByteArrayInputStream(data.getF());
            BufferedImage bi = ImageIO.read(is);
            result.createGraphics().drawImage(bi, x, y, null);
            y += bi.getHeight();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(result, "jpeg", baos);
        return baos.toByteArray();
    }

    public void DistToSlavers(Stack<BufferedImage> st, Stack<Worker> slavers, List<Data> filtredPartey,
            float[] kernel) {

        Iterator<BufferedImage> itr = st.iterator();
        Stack<Worker> workers = (Stack<Worker>) slavers.clone();
        while (itr.hasNext()) {
            BufferedImage bi = itr.next();
            Worker slaver = (Worker) workers.pop();
            System.out.println("slaver id  " + slaver.id);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        try {
                            Socket socket = new Socket(slaver.host, slaver.port);
                            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                            File f = new File("./buImagePart" + slaver.id + ".jpeg");
                            ImageIO.write(bi, "jpeg", f);
                            FileInputStream fileInputStream = new FileInputStream(f);
                            byte[] b = new byte[fileInputStream.available()];
                            fileInputStream.read(b);
                            Data data = new Data();

                            data.setId(slaver.id);
                            data.setF(b);
                            data.setArrayKirnel(kernel);
                            out.writeObject(data);

                            out.flush();

                            data = (Data) in.readObject();
                            filtredPartey.add(data);
                            System.out.println(Thread.currentThread().getName() + " id is " + data.id);
                            System.out.println(" stack size after slaver response ..." + filtredPartey.size());
                            socket.close();
                            fileInputStream.close();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();
        }

    }

    public void getAvailabelSlavers(File file, Stack<Worker> slevers) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        int nbrLine = 0;
        while (line != null) {
            line = br.readLine();
            if (line == null)
                break;
            String[] slaverline = line.split(";");
            Worker slaver = new Worker(slaverline[1], Integer.parseInt(slaverline[2]), Integer.parseInt(slaverline[0]));
            slevers.push(slaver);
            nbrLine++;

        }
        br.close();
        Server.numberS = nbrLine;
        System.out.println("there are " + Server.numberS + " slaver");

    }

    public Data getItemById(int id, List<Data> list) {
        Data data = null;
        for (int i = 0; i < list.size(); i++) {
            data = (Data) list.get(i);
            if (data.id == id)
                return data;
        }
        return data;
    }

    @Override
    public float[][] additionMatrice(float[][] matA, float[][] matB) {
        float[][] result = new float[matA.length][matA[0].length];

        for (int i = 0; i < matA.length; ++i) {
            for (int j = 0; j < matA[i].length; ++j) {
                result[i][j] = matA[i][j] + matB[i][j];
            }
        }
        return result;
    }

    @Override
    public float[][] substractionMatrice(float[][] matA, float[][] matB) {
        float[][] result = new float[matA.length][matA[0].length];

        for (int i = 0; i < matA.length; ++i) {
            for (int j = 0; j < matA[i].length; ++j) {
                result[i][j] = matA[i][j] - matB[i][j];
            }
        }
        return result;
    }

    @Override
    public float[][] multiplicationMatrice(float[][] matA, float[][] matB) {

        float[][] result = new float[matA.length][matA[0].length];
        for (int i = 0; i < matA.length; ++i) {
            for (int j = 0; j < matB.length; ++j) {
                result[i][j] = 0;
                for (int k = 0; k < matA.length; k++) {
                    result[i][j] += matA[i][k] * matB[k][j];
                }
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        return result;
    }

    @Override
    public void matriceTraitement(Socket socket, Data data, ObjectInputStream in, ObjectOutputStream out) {
        float[][] res = null;
        switch (data.operation) {
            case '+':

                res = additionMatrice(data.matA, data.matB);
                break;
            case '-':
                res = substractionMatrice(data.matA, data.matB);
                break;
            case '*':
                res = multiplicationMatrice(data.matA, data.matB);
                break;
            default:
                break;
        }
        data.setRes(res);
        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;

    }

    @Override
    public void convolutionTraitement(Socket socket, Data data, Stack<Worker> slavers, List<Data> filtredPartey,
            ObjectInputStream in, ObjectOutputStream out) {
        float[] kernel;

        kernel = data.arrayKirnel;
        // ========

        File image = new File("./ImageServerinit.jpeg");
        FileOutputStream outf;
        try {
            outf = new FileOutputStream(image);
            outf.write(data.f);
            outf.close();
            Stack<BufferedImage> st = Decouper(image, Server.numberS);
            DistToSlavers(st, slavers, filtredPartey, kernel);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        // in = new ObjectInputStream(socket.getInputStream());
                        // out= new ObjectOutputStream(socket.getOutputStream()) ;
                        System.out.print("done");
                        while (true) {
                            if (filtredPartey.size() < Server.numberS) {
                                System.out.print("wait ...");
                                continue;
                            }

                            data.setF(Merge(filtredPartey));

                            out.writeObject(data);
                            out.flush();
                            break;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[] fileToByte(File file) throws IOException {
        FileInputStream fl = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.length()];
        fl.read(bytes);

        fl.close();

        return bytes;
    }

    @Override
    public File byteToFile(byte[] bytes,File file)throws IOException{
        OutputStream os = new FileOutputStream(file);
        os.write(bytes);
        os.close();
        return file;
    }

    @Override
    public BufferedImage byteToBuffredImage(byte[] bytes) throws IOException {
        BufferedImage bufferedImage = null;
        InputStream is = new ByteArrayInputStream(bytes);
        bufferedImage = ImageIO.read(is);
        return bufferedImage;
    }

    @Override
    public byte[] buffredImageToByte(BufferedImage bi) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
    @Override
    public void resend(Socket socket,Data data,int id){
        System.out.println(" resent called ... "+id);
        for ( int i = 0 ; i<Server.members.size();++i) {
            if(Server.members.get(i) != socket) {
                try {
                    Server.outs.get(i).writeObject(data);
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
            }
        }
    }
}
