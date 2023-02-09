
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;
// Creating Remote interface for our application
public interface IFilterRMI extends Remote {
    public byte[] Grayscale(byte file[] ) throws RemoteException, IOException ;
    public byte[] negative(byte file[] ) throws RemoteException, IOException ;
    public byte[] red(byte file[] ) throws RemoteException, IOException ;
    public byte[] green(byte file[] ) throws RemoteException, IOException ;
    public byte[] blue(byte file[] ) throws RemoteException, IOException ;
    public byte[] sepia(byte file[] ) throws RemoteException, IOException ;
    public byte[] merge(byte file[] ) throws RemoteException, IOException ;
    
}
