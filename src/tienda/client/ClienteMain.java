package tienda.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import tienda.common.Producto;
import tienda.server.Tienda;

class ClienteMain {
    @SuppressWarnings({ "removal", "deprecation", "resource" })
	static public void main (String args[]) {
    	int salida = 0;
        if (args.length != 2) {
            System.err.println("Uso: ClienteMain hostregistro numPuertoRegistro ");
            return;
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
	    
	    Tienda srv = (Tienda) Naming.lookup("//" + args[0] + ":" + args[1] + "/Tienda");
	    //Creamos las cadenas para comparar que acción va a realizar el cliente
	    Scanner EntradaDatos =new Scanner(System.in); //para pedir datos por línea de comando
	    
	    do{
			System.out.println("Si desea salir de comprar o devolver introduzca el número 7 ");
			salida = EntradaDatos.nextInt();
			EntradaDatos.nextLine(); //limpiar el buffer de entrada
			//acción que se va a realizar
			System.out.println("Ingrese la acción que va a realizar: ");
			String accion = EntradaDatos.nextLine();
			//comparamos si el cliente quiere comprar
			if ("compra".equalsIgnoreCase(accion)){
			    System.out.println("Ingrese el Id del producto que quiera comprar: ");
			    int id_producto_comprar = EntradaDatos.nextInt();
			    System.out.println("Ingrese la cantidad del producto a comprar: ");
			    int cantidad_producto_comprar = EntradaDatos.nextInt();
			    System.out.println("Ingrese el importe del producto a comprar: ");
			    EntradaDatos.nextLine(); //limpiar el buffer de entrada
			    float importe_producto_comprar = EntradaDatos.nextFloat();
	
			    Producto p = srv.compraProducto(id_producto_comprar, cantidad_producto_comprar, importe_producto_comprar);
			    System.out.println("Se ha efectuado la compra del producto: "+ p.getNombre());
			}
			//comprobamos si el cliente quiere la lista de productos
			else if("Lista".equalsIgnoreCase(accion)){
			    List <Producto> listaProducto;
			    listaProducto = srv.obtenerProductos();
			    for(Producto i: listaProducto){
				String nombre = i.getNombre();
				System.out.println(nombre + ":" + i.getPrecio());
			    }
			}
			//comprabamos si el cliente quiere devolver un producto
			else if("Devolver".equalsIgnoreCase(accion)){
			    System.out.println("Ingrese el Id que se quiera devolver: ");
			    int id_producto_devolver = EntradaDatos.nextInt();
			    System.out.println("Ingrese la cantidad del producto a devolver: ");
			    int cantidad_producto_devolver = EntradaDatos.nextInt();
			    System.out.println("Ingrese el importe del producto a devovler: ");
			    EntradaDatos.nextLine(); //limpiar el buffer de entrada
			    float importe_producto_devolver = EntradaDatos.nextFloat();
	
			    srv.devuelveProducto(id_producto_devolver, cantidad_producto_devolver, importe_producto_devolver);
			    System.out.println("Se ha efectuado la devolución del producto: "+ id_producto_devolver);
			
			}
			else{
			    System.out.println("Introduzca una de las siguientes tres palabras: lista, compra o delvover");
			}
	    }while(salida != 7);
	  
      
        }
	 catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        }
        catch (Exception e) {
            System.err.println("Excepcion en ClienteTienda:");
            e.printStackTrace();
        }
    }
}
