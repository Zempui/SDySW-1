import java.sql.*;
import java.util.List;


public interface Database {
	/**
	 * Devuelve el estado de la caja en ese instante
	 * @return estado de la caja
	 * @throws SQLException 
	 */
	public float 	getCuentas () throws SQLException;
	
	/**
	 * Ejerce un cambio sobre el estado de la caja (suma o resta dinero)
	 * @param cambio
	 * @throws SQLException 
	 */
	public void 	updateCuentas (float cambio) throws CuentaException, SQLException;
	
	/**
	 * Establece el estado actual de la caja
	 * @param total
	 */
	public void 	setCuentas (float total) throws Exception;
	
	/**
	 * Devuelve una lista de los productos disponibles en la BD
	 * @return Lista con el inventario
	 * @throws SQLException 
	 */
	public List<Producto> 	getInventario () throws SQLException;
	
	/**
	 * Devuelve un String con la lista de los productos disponibles en la BD
	 * @return String con el inventario
	 * @throws SQLException 
	 */
	public String inventarioToString() throws SQLException;
	
	/**
	 * Elimina un producto con la id proporcionada del inventario
	 * @param id
	 * @return Producto
	 * @throws SQLException 
	 */
	public Producto getProducto (int id) throws SQLException;
	
	/**
	 * Elimina una cantidad de un producto del inventario
	 * @param id
	 * @param cantidad
	 * @return Producto
	 * @throws SQLException 
	 */
	public Producto getProducto (int id, int cantidad) throws DBException, SQLException;
	
	/**
	 * Devuelve el ID del producto con el nombre proporcionado
	 * @param nombre
	 * @return id
	 */
	public int 		getId (String nombre) throws DBException, SQLException;
	
	/**
	 * Añade una unidad de un producto al inventario
	 * @param id
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void 	addProducto (int id) throws SQLException, DBException;
	
	/**
	 * Añade una cantidad de un producto al inventario
	 * @param id
	 * @param cantidad
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void 	addProducto (int id, int cantidad) throws SQLException, DBException;
	
	/**
	 * Añade una cantidad de un nuevo producto al inventario. Devuelve el ID
	 * asignado al nuevo producto (siempre mayor que 1) o, en caso de que
	 * el producto ya estuviese registrado, devolverá 0.
	 * @param nombre
	 * @param precio
	 * @param cantidad
	 * @return id
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public int		nuevoProducto (String nombre, float precio, int cantidad) throws SQLException, DBException;
	
	/**
	 * Cambia el nombre del producto con la id proporcionada
	 * @param id
	 * @param nombre
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void 	setNombreProducto (int id, String nombre) throws SQLException, DBException;
	
	/**
	 * Cambia el precio del producto con la id dada
	 * @param id
	 * @param precio
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void 	setPrecioProducto (int id, float precio) throws SQLException, DBException;
	
	/**
	 * Cambia la cantidad del producto con la id dada
	 * @param id
	 * @param cantidad
	 * @throws SQLException 
	 * @throws DBException 
	 */
	public void 	setCantidadProducto (int id, int cantidad) throws SQLException, DBException;
	
}
