/**
 * Clase "TiendaImpl", que contiene los metodos ya construidos y detallados.
 * @author Amando Antoñano
 *
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;



class TiendaImpl extends UnicastRemoteObject implements Tienda
{
    private static final long serialVersionUID = 1L;
    private List<Producto> inventario;
    private Producto producto;
    int id;
    int cantidad;
    int cantidadReal;
    private float precio;
    private String name;
    float cambio;
    private float cashFlow;
    private Database objDB;
    

    TiendaImpl() throws RemoteException {
	inventario = new LinkedList<Producto>();
	objDB = new DatabaseImpl();
	producto = new ProductoImpl(precio, name,id,cantidad);
    }

    public Producto compraProducto (int id, int cantidad, float cambio) throws RemoteException, DBException, Exception, CuentaException
    {
	//La idea seria que este metodo tenga una variable del tipo DataBase para poder usar sus metodos
        this.cantidad = cantidad;
	this.id = id;
	this.cambio = cambio;
		
	producto = objDB.getProducto(id, cantidad); //Obtengo el producto
	System.out.println ("Se ha extraido la cantidad: "+cantidad+"del producto: " + producto.getNombre());
		
	objDB.updateCuentas(cambio);  //Actualizo el flujo de caja    
	System.out.println ("Se ha actualizado el flujo de caja ganando: "+cambio+"euros");

	return producto;	
    }

    public void devuelveProducto (int id, int cantidad,float cambio) throws RemoteException, Exception, DBException, CuentaException
    {
        this.cantidad = cantidad;
	this.id = id;
	this.cambio = cambio;

	objDB.addProducto(id, cantidad);
	System.out.println ("EL producto con id: "+producto.getNombre()+"ha sido devuelto en una cantidad de: "+cantidad);

	objDB.updateCuentas(cambio);
	System.out.println ("Se ha actualizado el flujo de caja perdiendo: "+cambio+"euros");
	
    }

    public List<Producto> obtenerProductos () throws RemoteException, Exception
    {
	inventario = objDB.getInventario();

	return inventario;
    }

    public float obtenerCashFlow () throws RemoteException, Exception
    {
	cashFlow = objDB.getCuentas();

	return cashFlow;
    }

    public Producto getProducto (int id, int cantidad) throws Exception
    {
	this.id = id;
	this.cantidad = cantidad;

	cantidadReal = objDB.getCantidadProducto(id);
	if (cantidad <= cantidadReal) 
	    {
		producto = objDB.getProducto (id, cantidad);
	    }
	else
	    System.out.println ("'cantidad' introducida superior a la existente en inventario, introduzca una menor"); 

	return producto;
    }
    public int nuevoProducto (String name, float precio, int cantidad) throws Exception
    {
	this.name = name;
	this.precio = precio;
	this.cantidad = cantidad;
		

	int id = objDB.nuevoProducto (name, precio, cantidad);

	if (id == 0)
	    System.out.println ("El producto con id: " + id + "ya existe");
	return id;
    }

    public void eliminaProducto (int id) throws Exception
    {
	this.id = id;
	
	objDB.eliminaProducto(id);
    }

}
    
    
