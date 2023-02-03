import java.awt.image.Kernel;
import java.io.Serializable;

public class Data implements Serializable {

    byte[] f;
    int id ;
    int hegth ;
    int width ;
    float[] arrayKirnel ;
    
    int [][] matA ;
    int [][] matB ;
    int [][] Res = {{0,0},{0,0}};
    String operation;

    public int[][] getMatA() {
        return matA;
    }

    public void setMatA(int[][] matA) {
        this.matA = matA;
    }

    public int[][] getMatB() {
        return matB;
    }

    public void setMatB(int[][] matB) {
        this.matB = matB;
    }

    public int[][] getRes() {
        return Res;
    }

    public void setRes(int[][] res) {
        Res = res;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }




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
    // constructor
    public Data(int[][] matA, int[][] matB, String operation) {
        this.matA = matA;
        this.matB = matB;
        this.operation = operation;
    }
}