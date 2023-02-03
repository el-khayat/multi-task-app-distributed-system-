import java.awt.image.Kernel;
import java.io.Serializable;

public class Data implements Serializable {

    byte[] f;
    int id ;
    int hegth ;
    int width ;
    float[] arrayKirnel ;
    
    public int getHegth() {
        return hegth;
    }

    public void setHegth(int hegth) {
        this.hegth = hegth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Data() {
    }

    public Data(byte[] f, Kernel kernel) {
        this.f = f;
    }

    public byte[] getF() {
        return f;
    }

    public void setF(byte[] f) {
        this.f = f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float[] getArrayKirnel() {
        return arrayKirnel;
    }

    public void setArrayKirnel(float[]arrayKirnel) {
        this.arrayKirnel = arrayKirnel;
    }
}