/**
 * Clase "TiendaImpl", que contiene los metodos ya construidos y detallados.
 * @author Amando Antoñano
 *
 */
package tienda.server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import tienda.common.CuentaException;
import tienda.common.DBException;
import tienda.common.Database;
import tienda.common.DatabaseImpl;
import tienda.common.Producto;
import tienda.common.ProductoImpl;



class TiendaImpl extends UnicastRemoteObject implements Tienda
{
	private static final long serialVersionUID = 1L;
	private List<Producto> inventario;
    private Producto producto;
    int id;
    int cantidad;
    private float precio;
    private String name;
    float cambio;
    private float cashFlow;
    private Database objDB;
    

    TiendaImpl() throws RemoteException {
	inventario = new LinkedList<Producto>();
	objDB = new DatabaseImpl();
	producto = new ProductoImpl(precio, name);
    }

    public Producto compraProducto (int id, int cantidad, float cambio) throws RemoteException, DBException, SQLException, CuentaException
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

    public void devuelveProducto (int id, int cantidad,float cambio) throws RemoteException, SQLException, DBException, CuentaException
    {
        this.cantidad = cantidad;
	this.id = id;
	this.cambio = cambio;

        objDB.addProducto(id, cantidad);
	System.out.println ("EL producto con id: "+producto.getNombre()+"ha sido devuelto en una cantidad de: "+cantidad);

	objDB.updateCuentas(cambio);
	System.out.println ("Se ha actualizado el flujo de caja perdiendo: "+cambio+"euros");
	
    }

    public List<Producto> obtenerProductos () throws RemoteException, SQLException
    {

	//Aqui tenemos inconveniente porque el metodo de getInventrio devuelve un string y yo quiero una lista para que la profe vea que las manejamos.
	inventario = objDB.getInventario();

	return inventario;
    }

    public float obtenerCashFlow () throws RemoteException, SQLException
    {
	cashFlow = objDB.getCuentas();

	return cashFlow;
    }
}
    
    
