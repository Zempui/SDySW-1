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
	 * @param id
	 * @param cantidad
	 * @param cambio
	 * @return producto
         * @throws DBException 
	 * @throws Exception 
         * @throws CuentaException 
	 */
    public Producto compraProducto (int id, int cantidad,float cambio) throws RemoteException, DBException, Exception, CuentaException;


         /**
	 * Mete un producto con una id y una cantidad x al inventario
	 * @param id
	 * @param cantidad
	 * @param cambio
         * @throws DBException 
	 * @throws Exception 
         * @throws CuentaException 
	 */
    public void devuelveProducto (int id, int cantidad, float cambio) throws RemoteException, Exception, DBException, CuentaException;


    
         /**
	 * Devuelve una lista con todos los productos disponibles en el inventario
	 * @return Lista de productos
	 * @throws Exception 
	 * @throws RemoteException
	 */
    public List<Producto> obtenerProductos () throws RemoteException, Exception;


         /**
	 * Devuelve el estado de la caja en ese instante
	 * @return estado de la caja
	 * @throws Exception 
	 * @throws RemoteException
	 */
    public float obtenerCashFlow () throws RemoteException, Exception;

     /**
	 * Devuelve un producto en base a la id que se le da
	 * @param id
	 * @return Producto
	 * @throws Exception 
	 */

    public Producto getProducto (int id, int cantidad) throws Exception;


    /**
	  * Añade una cantidad de un nuevo producto al inventario. Devuelve el ID
	 * asignado al nuevo producto (siempre mayor que 1) o, en caso de que
	 * el producto ya estuviese registrado, devolverá 0.
	 * @param nombre
	 * @param precio
	 * @param cantidad
	 * @return id
	 * @throws Exception 
	  
     */
    public int nuevoProducto (String nombre, float precio, int cantidad) throws Exception;


    /**
	  * Añade una cantidad de un nuevo producto al inventario. Devuelve el ID
	 * asignado al nuevo producto (siempre mayor que 1) o, en caso de que
	 * el producto ya estuviese registrado, devolverá 0.
	 * @param id
	 * @throws Exception 
	  
     */

    public void eliminaProducto (int id) throws Exception;
    

}

