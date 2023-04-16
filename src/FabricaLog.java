import java.rmi.*;
import java.util.*;

interface FabricaLog extends Remote {
   ServicioLog crearLog(Log log) throws RemoteException;
}

