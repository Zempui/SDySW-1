package tienda;

public class Prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatabaseImpl db = new DatabaseImpl();
		try {
			//CUENTAS
			System.out.println(String.format("|    %10s      |","CUENTAS"));
			System.out.println("getCuentas:\t"+db.getCuentas());
			db.updateCuentas(+15000);
			System.out.println("updateCuentas:\t✔");
			System.out.println("getCuentas:\t"+db.getCuentas());
			db.setCuentas(10000);
			System.out.println("setCuentas:\t✔");
			System.out.println("getCuentas:\t"+db.getCuentas());
			
			//INVENTARIO
			System.out.println(String.format("|    %10s      |","INVENTARIO"));
			
			System.out.println("\n"+db.getInventario());
			System.out.println(db.getProducto(0));
			db.setCantidadProducto(0, 10);
			System.out.println("setCantidadProducto\t✔");
			
			System.out.println("nuevoProducto: "+db.nuevoProducto("Pan de pueblo", 0.45f, 63));
			System.out.println("nuevoProducto: "+db.nuevoProducto("Docena huevos", 3.85f, 21));
			System.out.println("nuevoProducto: "+db.nuevoProducto("Vino tinto", 5.95f, 15));
			System.out.println("\n"+db.getInventario());
			
			System.out.println("getID:\tPan de pueblo - "+db.getId("Pan de pueblo"));
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}

}
