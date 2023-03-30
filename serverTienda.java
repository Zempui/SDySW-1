/**
 *Clase main del server que controla su ejecución correcta.
 * @author Amando Antoñano
 *
 */
package tienda;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;


class serverTienda  {
    static public void main (String args[]) {
       if (args.length!=1) {
            System.err.println("Uso: ServidorFabrica numPuertoRegistro");
            return;
        }
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            TiendaImpl t = new TiendaImpl();
	    
	    //Lo que hace rebind es vincular el arg[0] (puertoregistro) con el nuevo objeto remoto
	    //rebind(String name, Remote obj) donde name tiene que tener formato de url
            Naming.rebind("rmi://localhost:" + args[0] + "/Tienda", t);
	    
        }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Excepcion en ServidorFabrica:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
