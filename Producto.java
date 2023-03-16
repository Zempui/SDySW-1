package tienda;
/**
 * Interfaz "Producto", que representa un producto disponible en el inventario
 * @author Álvaro de Castro
 *
 */
public interface Producto {

	/**
	 * Método getPrecio, que devuelve el precio del producto
	 * @return precio
	 */
	public float getPrecio();
	/**
	 * Método getNombre, que devuelve el nombre del producto
	 * @return nombre
	 */
	public String getNombre();
}
