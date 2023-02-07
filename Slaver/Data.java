import java.awt.image.Kernel;
import java.io.Serializable;

public class Data implements Serializable {
    String task;

    byte[] f;
    int id ;
    int hegth ;
    int width ;
    float[] arrayKirnel ;
    
    float [][] matA ;
    float [][] matB ;
    float [][] Res = {{0,0},{0,0}};
    char operation;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float[][] getMatA() {
        return matA;
    }

    public void setMatA(float[][] matA) {
        this.matA = matA;
    }

    public float[][] getMatB() {
        return matB;
    }

    public void setMatB(float[][] matB) {
        this.matB = matB;
    }

    public float[][] getRes() {
        return Res;
    }

    public void setRes(float[][] res) {
        Res = res;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
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

    public Data(float[][] matA, float[][] matB, char operation) {
        this.matA = matA;
        this.matB = matB;
        this.operation = operation;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
   
}