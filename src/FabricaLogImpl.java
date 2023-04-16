import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class FabricaLogImpl extends UnicastRemoteObject implements FabricaLog {
	List<ServicioLog> l;
	FabricaLogImpl () throws RemoteException {
		l = new LinkedList<ServicioLog>(); 
	}
	public ServicioLog crearLog (Log log) throws RemoteException{
		ServicioLog s = new ServicioLogImpl(log);
		l.add(s); 
		return s;
	}

}
