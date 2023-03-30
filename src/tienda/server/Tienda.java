/**
 * Interfaz "Tienda", que contiene definicion de sus metodos.
 * @author Amando Antoñano
 *
 */
package tienda.server;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import tienda.common.*;


public interface Tienda extends Remote {
         /**
	 * Saca un producto con una id y la cantidad x del inventario (la bbdd)
	 * @return producto
         * @throws DBException 
	 * @throws SQLException 
         * @throws CuentaException 
	 */
    public Producto compraProducto (int id, int cantidad,float cambio) throws RemoteException, DBException, SQLException, CuentaException;


         /**
	 * Mete un producto con una id y una cantidad x al inventario
         * @throws DBException 
	 * @throws SQLException 
         * @throws CuentaException 
	 */
    public void devuelveProducto (int id, int cantidad, float cambio) throws RemoteException, SQLException, DBException, CuentaException;


    
         /**
	 * Devuelve una lista con todos los productos disponibles en el inventario
	 * @return Lista de productos
	 * @throws SQLException 
	 */
    public List<Producto> obtenerProductos () throws RemoteException, SQLException;


         /**
	 * Devuelve el estado de la caja en ese instante
	 * @return estado de la caja
	 * @throws SQLException 
	 */
    public float obtenerCashFlow () throws RemoteException, SQLException;
}
