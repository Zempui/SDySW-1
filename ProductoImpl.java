import java.io.Serializable;
import java.sql.*;

public class ProductoImpl implements Producto, Serializable {
	private static final long serialVersionUID = 1L;
	private float precio;
	private String nombre;
	private int cantidad;
	private int id;
	public ProductoImpl(float precio, String nombre, int id, int cantidad) {
		this.precio = precio;
		this.nombre = nombre;
		this.id = id;
		this.cantidad = cantidad;
	}
	
	@Override
	public float getPrecio() {
		return precio;

	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public int getId(){
		return id;
	}

	@Override
	public int getCantidad(){
		return cantidad;
	}

	@Override
	public String toString() {
		return "ProductoImpl [precio=" + precio + ", nombre=" + nombre + "]";
	}
	

}
