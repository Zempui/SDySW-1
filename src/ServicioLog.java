
import java.rmi.*;
import java.util.Date;

interface ServicioLog extends Remote {
	void log (String m) throws RemoteException;
    
}

