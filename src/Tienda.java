/**
 * Interfaz "Tienda", que contiene definicion de sus metodos.
 * @author Amando Antoñano
 *
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.List;



public interface Tienda extends Remote {
         /**
	 * Saca un producto con una id y la cantidad x del inventario (la bbdd)
	 * @return producto
         * @throws DBException 
	 * @throws Exception 
         * @throws CuentaException 
	 */
    public Producto compraProducto (int id, int cantidad,float cambio) throws RemoteException, DBException, Exception, CuentaException;


         /**
	 * Mete un producto con una id y una cantidad x al inventario
         * @throws DBException 
	 * @throws Exception 
         * @throws CuentaException 
	 */
    public void devuelveProducto (int id, int cantidad, float cambio) throws RemoteException, Exception, DBException, CuentaException;


    
         /**
	 * Devuelve una lista con todos los productos disponibles en el inventario
	 * @return Lista de productos
	 * @throws Exception 
	 */
    public List<Producto> obtenerProductos () throws RemoteException, Exception;


         /**
	 * Devuelve el estado de la caja en ese instante
	 * @return estado de la caja
	 * @throws Exception 
	 */
    public float obtenerCashFlow () throws RemoteException, Exception;
}
