package tienda;
import java.io.Serializable;

public class ProductoImpl implements Producto, Serializable {
	private static final long serialVersionUID = 1L;
	private float precio;
	private String nombre;
	public ProductoImpl(float precio, String nombre) {
		this.precio = precio;
		this.nombre = nombre;
	}
	
	@Override
	public float getPrecio() {
		// TODO Auto-generated method stub
		return precio;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public String toString() {
		return "ProductoImpl [precio=" + precio + ", nombre=" + nombre + "]";
	}


}
