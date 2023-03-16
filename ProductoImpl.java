package tienda;

public class ProductoImpl implements Producto {
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
