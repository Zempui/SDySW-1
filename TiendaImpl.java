/**
 * Clase "TiendaImpl", que contiene los metodos ya construidos y detallados.
 * @author Amando Antoñano
 *
 */
package tienda;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;



class TiendaImpl extends UnicastRemoteObject implements Tienda
{
    private List<Producto> inventario;
    private Producto producto;
    private int id;
    private int cantidad;
    private float precio;
    private String name;
    private float cambio;
    private float cashFlow;
    private Database objDB;
    

    TiendaImpl() throws RemoteException {
	inventario = new LinkedList<Producto>();
	objDB = new DatabaseImpl();
	producto = new ProductoImpl(precio, name);
    }

    public Producto compraProducto (int id, int cantidad, float cambio) throws RemoteException
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

    public void devuelveProducto (int id, int cantidad,float cambio) throws RemoteException
    {
        this.cantidad = cantidad;
	this.id = id;
	this.cambio = cambio;

        objDB.addProducto(id, cantidad);
	System.out.println ("EL producto con id: "+producto.getNombre()+"ha sido devuelto en una cantidad de: "+cantidad);

	objDB.updateCuentas(cambio);
	System.out.println ("Se ha actualizado el flujo de caja perdiendo: "+cambio+"euros");
	
    }

    public List<Producto> obtenerProductos () throws RemoteException
    {

	//Aqui tenemos inconveniente porque el metodo de getInventrio devuelve un string y yo quiero una lista para que la profe vea que las manejamos.
	inventario = objDB.getInventario();

	return inventario;
    }

    public float obtenerCashFlow () throws RemoteException
    {
	cashFlow = objDB.getCuentas();

	return cashFlow;
    }
}
    
    
