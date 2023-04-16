import java.sql.*;
import java.util.List;


public interface Database {
	/**
	 * Devuelve el estado de la caja en ese instante
	 * @return estado de la caja
	 * @throws Exception 
	 */
	public float 	getCuentas () throws Exception;
	
	/**
	 * Ejerce un cambio sobre el estado de la caja (suma o resta dinero)
	 * @param cambio
	 * @throws Exception 
	 */
	public void 	updateCuentas (float cambio) throws CuentaException, Exception;
	
	/**
	 * Establece el estado actual de la caja
	 * @param total
	 */
	public void 	setCuentas (float total) throws Exception;
	
	/**
	 * Devuelve una lista de los productos disponibles en la BD
	 * @return Lista con el inventario
	 * @throws Exception 
	 */
	public List<Producto> 	getInventario () throws Exception;
	
	/**
	 * Devuelve un String con la lista de los productos disponibles en la BD
	 * @return String con el inventario
	 * @throws Exception 
	 */
	public String inventarioToString() throws Exception;
	
	/**
	 * Elimina un producto con la id proporcionada del inventario
	 * @param id
	 * @return Producto
	 * @throws Exception 
	 */
	public Producto getProducto (int id) throws Exception;
	
	/**
	 * Elimina una cantidad de un producto del inventario
	 * @param id
	 * @param cantidad
	 * @return Producto
	 * @throws Exception 
	 */
	public Producto getProducto (int id, int cantidad) throws DBException, Exception;
	
	/**
	 * Devuelve la cantidad de un producto disponible en la base de datos
	 * @param id
	 * @return cantidad
	 * @throws Exception
	 */
	public int getCantidadProducto (int id) throws Exception;


	/**
	 * Devuelve el ID del producto con el nombre proporcionado
	 * @param nombre
	 * @return id
	 */
	public int 		getId (String nombre) throws DBException, Exception;

	/**
	 * Devuelve el precio de un producto con el ID proporcionado
	 * @param id
         * @throws Exception
	 * @return precio
	 */
	public float getPrecio(int id) throws Exception;

	/**
	 * Añade una unidad de un producto al inventario
	 * @param id
	 * @throws Exception 
	 * @throws DBException 
	 */
	public void 	addProducto (int id) throws Exception, DBException;
	
	/**
	 * Añade una cantidad de un producto al inventario
	 * @param id
	 * @param cantidad
	 * @throws Exception 
	 * @throws DBException 
	 */
	public void 	addProducto (int id, int cantidad) throws Exception, DBException;
	
	/**
	 * Añade una cantidad de un nuevo producto al inventario. Devuelve el ID
	 * asignado al nuevo producto (siempre mayor que 1) o, en caso de que
	 * el producto ya estuviese registrado, devolverá 0.
	 * @param nombre
	 * @param precio
	 * @param cantidad
	 * @return id
	 * @throws Exception 
	 * @throws DBException 
	 */
	public int	       nuevoProducto (String nombre, float precio, int cantidad) throws Exception, DBException;
	
	/**
	 * Cambia el nombre del producto con la id proporcionada
	 * @param id
	 * @param nombre
	 * @throws Exception 
	 * @throws DBException 
	 */
	public void 	setNombreProducto (int id, String nombre) throws Exception, DBException;
	
	/**
	 * Cambia el precio del producto con la id dada
	 * @param id
	 * @param precio
	 * @throws Exception 
	 * @throws DBException 
	 */
	public void 	setPrecioProducto (int id, float precio) throws Exception, DBException;
	
	/**
	 * Cambia la cantidad del producto con la id dada
	 * @param id
	 * @param cantidad
	 * @throws Exception 
	 * @throws DBException 
	 */
	public void 	setCantidadProducto (int id, int cantidad) throws Exception, DBException;

	/**
	 * Elimina un producto de la base de datos con la ID dada
	 * @param id
	 * @throws Exception
	 */
	public void eliminaProducto(int id) throws Exception;
}
