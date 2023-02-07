
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.File;
// Creating Remote interface for our application
public interface IFilterRMI extends Remote {
    String printMsg() throws RemoteException;
    int add(int x,int y) throws RemoteException;
    public File Grayscale(byte file[] ) throws RemoteException ;
    public byte[] negative(byte file[] ) throws RemoteException ;
    public File red(byte file[] ) throws RemoteException ;
    public File green(byte file[] ) throws RemoteException ;
    public File blue(byte file[] ) throws RemoteException ;
    public File sepia(byte file[] ) throws RemoteException ;
    public File merge(byte file[] ) throws RemoteException ;
}
