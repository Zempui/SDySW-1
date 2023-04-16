import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Date;


class ServicioLogImpl extends UnicastRemoteObject implements ServicioLog {
    PrintWriter fd;
    private Log log;
    Date fecha = new Date();
    ServicioLogImpl(Log l) throws RemoteException {
        try {
             log=l;
             String f=log.obtenerNombreFichero(); 
             fd = new PrintWriter(new FileWriter (f, true)); 
        }
        catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
	catch (IOException o){
	    System.err.println(o);
            System.exit(1);
	}
    }
    //public synchronized void log(String m) throws RemoteException {
    public synchronized void log(String m) throws RemoteException {
        System.out.println(m);
        try {
           Thread.sleep(1);
        }
        catch( InterruptedException e)
        {
        }
        fd.println(m);
        fd.flush();
    }

}
