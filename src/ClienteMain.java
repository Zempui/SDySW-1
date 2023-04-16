import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.sql.*;
import java.util.Date;

class ClienteMain {
    @SuppressWarnings({ "removal", "deprecation", "resource" })
	static public void main (String args[]) {
      
        if (args.length != 4) {
            System.err.println("Uso: ClienteMain hostregistro numPuertoRegistro historico IdCliente");
            return;
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
	    FabricaLog server = (FabricaLog) Naming.lookup("//" + args[0] + ":" + args[1] + "/Log");
	    Log log = new Log(args[2],args[3]);
	    ServicioLog c = server.crearLog(log);
	    Tienda srv = (Tienda) Naming.lookup("//" + args[0] + ":" + args[1] + "/Tienda");
	    //Creamos las cadenas para comparar que acción va a realizar el cliente
	    Scanner EntradaDatos =new Scanner(System.in); //para pedir datos por línea de comando
	    boolean salir = false;
	    
	    int opcion;
	    Producto p;
                    Date fecha = new Date();
	    
	    while(!salir){
		 System.out.println("\n1. Comprar");
		 System.out.println("2. Lista");
		 System.out.println("3. Devolver");
		 System.out.println("4. Opciones administración");
		 System.out.println("5. Salir\n");
		 try {
 
		     System.out.println("Elija una de las anteriores opciones:\n");
		     opcion = EntradaDatos.nextInt();
 
		     switch (opcion) {
		     case 1:
			 System.out.println("Has seleccionado la opcion de COMPRAR\n");
			 System.out.println("Ingrese el Id del producto que quiera comprar: ");
			 int id_producto_compra = EntradaDatos.nextInt();
			 System.out.println("Ingrese la cantidad del producto a comprar: ");
			 int cantidad_producto_comprar = EntradaDatos.nextInt();
			if(cantidad_producto_comprar < 0){
				System.out.println("No se puede introducir un número negativo\n");	
			}
			else{	
			 	p = srv.getProducto(id_producto_compra, 0);
			 	float precio_compra = p.getPrecio();
			 	precio_compra = cantidad_producto_comprar*precio_compra;
			 	p = srv.compraProducto(id_producto_compra, cantidad_producto_comprar, precio_compra);
			 	System.out.println("Se ha efectuado la compra del producto: "+ p.getNombre() + " con un coste total: "+ precio_compra);
				c.log(fecha+" "+args[3] +" se ha comprado una cantidad "+ cantidad_producto_comprar +" del producto "+ p.getNombre());
			} 
			break;
		     case 2:
			 System.out.println("Has seleccionado la opcion de LISTAR PRODUCTOS\n");
			 List <Producto> listaProducto;
			    listaProducto = srv.obtenerProductos();
			String resultado = "◢__ID__.________NOMBRE________.__PRECIO__._CANTIDAD_◣";

			
			for(Producto i: listaProducto) {
				String fila = String.format("| %4d | %-20s | %8.2f | %-8d |", 
							i.getId(),i.getNombre(), 
							i.getPrecio(),i.getCantidad());
				resultado = resultado +"\n"+fila;
			}

			 System.out.println(resultado+"\n");
			 c.log(fecha+" "+args[3] +" "+"se ha pedido la lista de productos ");
			 break;
		     case 3:
				System.out.println("Has seleccionado la opcion de DEVOLVER\n");
				System.out.println("Ingrese el Id del producto que desea devolver: ");
				int id_producto_devolver = EntradaDatos.nextInt();
				System.out.println("Ingrese la cantidad del producto a devolver: ");
				int cantidad_producto_devolver = EntradaDatos.nextInt();
				if(cantidad_producto_devolver < 0){
					System.out.println("No se puede introducir un número negativo\n");	
				}
				else{
					p = srv.getProducto(id_producto_devolver, 0);
					float precio_devolver = p.getPrecio();
					precio_devolver = cantidad_producto_devolver*precio_devolver;
					if(precio_devolver < 0){
						System.out.println("No se puede introducir un número negativo\n");
					}
					else{
						srv.devuelveProducto(id_producto_devolver, cantidad_producto_devolver, precio_devolver);
						System.out.println("Se ha efectuado la devolución del producto: "+ id_producto_devolver);
						c.log(fecha+" "+args[3] +" "+"se ha devuelto " + cantidad_producto_devolver+ " del producto con id: "+ id_producto_devolver);
					}
				}
			 	break;
			case 4:
				System.out.println("Has seleccionado la opcion de OPCIONES DE ADMINISTRACION\n");
				int opcion1=0;
				boolean salir_2 = false;
				while(!salir_2){

					System.out.println("\n1. Añadir nuevo producto");
					System.out.println("2. Añadir producto");
					System.out.println("3. Eliminar producto");
					System.out.println("4. Ver flujo de caja");
					System.out.println("5. Salir\n");
					try{
						System.out.println("Elija una de las anteriores opciones:\n");
						opcion1 = EntradaDatos.nextInt();
						switch (opcion1) {
							case 1:
								System.out.println("Has seleccionado la opcion de INTRODUCIR NUEVO PRODUCTO\n");
								EntradaDatos.nextLine();
								System.out.println("Ingrese el nombre del producto a introducir: ");
								String nombre_producto_introducir = EntradaDatos.nextLine();
								System.out.println("Ingrese la cantidad del producto a introducir: ");
								int cantidad_producto_introducir = EntradaDatos.nextInt();

								if (cantidad_producto_introducir < 0){
									System.out.println("No se puede introducir un número negativo\n");
								}
								else{
									System.out.println("Ingrese el precio del producto a introducir: ");
									float precio_producto_introducir = EntradaDatos.nextFloat();
									int nuevoProducto = srv.nuevoProducto(nombre_producto_introducir,precio_producto_introducir ,cantidad_producto_introducir);
									if (nuevoProducto == 0){
										System.out.println("Se ha introducido erróneamente el nuevo producto con id: "+ nuevoProducto);
									}else{ 
										System.out.println("Se ha introducido correctamente el nuevo producto con id: "+ nuevoProducto);
										c.log(fecha+" "+args[3] +" "+"se ha añadido "+ cantidad_producto_introducir+" del nuevo producto con id: "+ nuevoProducto);
									}
								}
								break;
							case 2:
								System.out.println("Has seleccionado la opcion de AÑADIR PRODUCTO\n");
								System.out.println("Ingrese el Id del producto que quiera añadir: ");
								int id_producto_añadir = EntradaDatos.nextInt();
								System.out.println("Ingrese la cantidad del producto a añadir: ");
								int cantidad_producto_añadir = EntradaDatos.nextInt();

								if (cantidad_producto_añadir < 0){
									System.out.println("No se puede introducir un número negativo\n");
								}
								else{
									p = srv.getProducto(id_producto_añadir, 0);
									float precio_añadir = p.getPrecio();
									precio_añadir = cantidad_producto_añadir*precio_añadir;
									srv.devuelveProducto(id_producto_añadir, cantidad_producto_añadir, precio_añadir);
									System.out.println("Se ha introducido el producto: "+ id_producto_añadir);
									c.log(fecha+" "+args[3] +" "+"se ha añadido " + cantidad_producto_añadir+ " del producto con id: "+ id_producto_añadir);
								}

								break;
							case 3:
								System.out.println("Has seleccionado la opcion de ELIMINAR\n");
								System.out.println("Ingrese el Id del producto que quiera eliminar: ");
								int id_producto_eliminar = EntradaDatos.nextInt();
								srv.eliminaProducto(id_producto_eliminar);
								System.out.println("Se ha eliminado el producto: "+ id_producto_eliminar);
								c.log(fecha+args[3] +" "+"se ha eliminado el producto "+ id_producto_eliminar);
								break;
							case 4:
								System.out.println("Has seleccionado la opcion de VER FLUJO DE CAJA\n");
								float flujo_de_caja = srv.obtenerCashFlow();
								System.out.println("El dinero en caja es: " + flujo_de_caja);
								c.log(fecha+" "+args[3] +" se ha consultado el flujo de caja: "+ flujo_de_caja);
								break;
							case 5:
								salir_2 = true;
								break;
							default:
								System.out.println("Solo números entre 1 y 5");
							
						}
					} catch (DBException e) {
						System.err.println("Error en el acceso a la base de datos: \n\t"+e.getClass().getName()+": "+e.getMessage());
					} catch (Exception e) {
						System.err.println("Excepcion en ClienteTienda:");
						e.printStackTrace();
					}
				}		
			break;
		     case 5:
				 salir = true;
				 break;
		     default:
			 System.out.println("Solo números entre 1 y 5");
		     }
		 }
		catch (DBException e) {
		   System.err.println("Error en el acceso a la base de datos: \n\t"+e.getClass().getName()+": "+e.getMessage());
		 } 
		catch (Exception e) {
		     System.err.println("Excepcion en ClienteTienda:");
		     e.printStackTrace();
		 }
			
	    }
	  
      
        }
	 catch (Exception e) {
		     System.err.println("Excepcion en ClienteTienda:");
		     e.printStackTrace();
        }

    }
}
