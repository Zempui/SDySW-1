import java.sql.*;
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;*/
import java.util.LinkedList;
import java.util.List;


public class DatabaseImpl implements Database {
	private Connection c = null;
	private Statement stmt = null;
	/**
	 * Constructor de la clase Database, Abre la base de datos o la crea en caso de no existir
	 */
	public DatabaseImpl() {
		// Creamos (o abrimos) la BD
		try {
			c = DriverManager.getConnection("jdbc:sqlite:prueba.db");
			c.setAutoCommit(true);
			stmt = c.createStatement();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		System.out.println("¡Base de datos abierta correctamente!");
		
		// Creamos las tablas "inventario" (0) y "cuentas" (1)
		for (int i = 0; i<2; i++) {
			try {
				this.createTable(i);
			/*} catch(org.sqlite.SQLiteException e) {
				System.out.println("Tabla "+i+" creada anteriormente");*/
			} catch (Exception e) {
				if (e.getClass().getName().equals("org.sqlite.SQLiteException")){
					System.out.println("Tabla "+i+" creada anteriormente");
				}
				else{
					System.err.println(e.getClass().getName()+": "+e.getMessage());
					System.exit(0);
				}
			}
		}
		
		// Reseteamos el dinero en la caja
		try {
			this.setCuentas(0.0f);
		}catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		} 
	}
	
	/**
	 * Método createTable, que inicializa las tablas "inventario" y "cuentas"
	 * @param table (0/1)
	 * @throws CreateTableException
	 */
	private void createTable(int table) throws Exception {
		String sql="";
		String nombre="";
		if (table==0){
			 sql = 	 "CREATE TABLE INVENTARIO"+
					 "(ID INT PRIMARY KEY NOT NULL,"+
					 "NOMBRE 	TEXT 	NOT NULL UNIQUE,"+
					 "PRECIO 	REAL 	NOT NULL,"+
					 "CANTIDAD 	INT 	NOT NULL)";
			 nombre="inventario";
			
		}else if (table==1) {
			sql =	"CREATE TABLE CUENTAS"
					+ "(ID INT PRIMARY KEY NOT NULL,"
					+ "TOTAL REAL NOT NULL)";
			nombre="cuentas";
		}else {
			throw new CreateTableException("tabla nº"+table+" no definido");
		}
		stmt.executeUpdate(sql);
		System.out.println("Tabla "+nombre+" creada correctamente");
	}

	@Override
	public float getCuentas() throws Exception {
		float total = 0.0f;
		ResultSet rs = stmt.executeQuery("SELECT TOTAL FROM CUENTAS WHERE ID=0");
		total = rs.getFloat("TOTAL");
		System.out.println("getCuentas() -> "+total+" €");
		return total;
	}

	@Override
	public void updateCuentas (float cambio) throws CuentaException, Exception{
		System.out.println("updateCuentas() -> "+cambio+" €");
		float cuenta = this.getCuentas();
		if((0-cambio) > cuenta) 
			throw new CuentaException("se ha intentado extraer una cantidad ("+cambio+") superior al dinero almacenado ("+cuenta+").");
		
		float total =cuenta+cambio;
		stmt.executeUpdate("UPDATE CUENTAS SET TOTAL="+total+" WHERE ID=0;");
		
		
	}
	
	@Override
	public void setCuentas(float total) throws Exception{
		System.out.println("setCuentas() -> "+total+" €");
		if(total >= 0.0f) {
			try {
				stmt.executeUpdate("INSERT INTO CUENTAS (ID, TOTAL)"
									+ "VALUES (0,"+total+");");
			} catch (Exception e) {
				//Cuenta previamente definida, se resetea
				stmt.executeUpdate("DELETE FROM CUENTAS WHERE ID=0;");
				stmt.executeUpdate("INSERT INTO CUENTAS (ID, TOTAL)"
						+ "VALUES (0,"+total+");");
			}
		}else {
			throw new CuentaException("setCuentas -> "+total+" < 0");
		}
		
	}
	
	@Override
	public List<Producto> getInventario() throws Exception {
		List<Producto> resultado = new LinkedList<Producto>();
		ResultSet rs = stmt.executeQuery("SELECT * FROM INVENTARIO;");
		while (rs.next()) {
			Producto producto = new ProductoImpl(rs.getFloat("PRECIO"), rs.getString("NOMBRE"), rs.getInt("ID"), rs.getInt("CANTIDAD"));
			resultado.add(producto);
		}
		System.out.println("getInventario():\n"+inventarioToString());
		return resultado;
		
	}
	
	@Override
	public String inventarioToString() throws Exception {
		String resultado = "◢__ID__.________NOMBRE________.__PRECIO__._CANTIDAD_◣";

		ResultSet rs = stmt.executeQuery("SELECT * FROM INVENTARIO;");
		while(rs.next()) {
			String fila = String.format("| %4d | %-20s | %8.2f | %-8d |", 
							rs.getInt("ID"), rs.getString("NOMBRE"), 
							rs.getFloat("PRECIO"), rs.getInt("CANTIDAD"));
			resultado = resultado +"\n"+fila;
		}

		return resultado;
	}

	@Override
	public Producto getProducto(int id) throws Exception {
		
		Producto producto = null;
	
		ResultSet rs = stmt.executeQuery("SELECT * FROM INVENTARIO WHERE ID="+id);
		if (rs.next()) {
			producto = new ProductoImpl(rs.getFloat("PRECIO"), rs.getString("NOMBRE"), rs.getInt("ID"), rs.getInt("CANTIDAD"));
			int cantidad = rs.getInt("CANTIDAD");
			if(cantidad <= 0) {
				// salta una excepción
				throw new DBException("no se puede sacar una unidad del producto con id "+id);
			}else {
				// se decrementa el número de unidades en 1
				stmt.executeUpdate("UPDATE INVENTARIO SET CANTIDAD="+(cantidad-1)+" WHERE ID="+id+";");
			}
		}
		System.out.println("getProducto("+id+") -> "+producto.getNombre());
		return producto;
		
	}

	@Override
	public Producto getProducto(int id, int cantidad) throws DBException, Exception{
		
		Producto producto = null;

		ResultSet rs = stmt.executeQuery("SELECT * FROM INVENTARIO WHERE ID="+id);
		if (rs.next() && cantidad >= 0) {
			if (cantidad >=1){
				producto = new ProductoImpl(rs.getFloat("PRECIO"), rs.getString("NOMBRE"), rs.getInt("ID"), rs.getInt("CANTIDAD"));
				int n = rs.getInt("CANTIDAD");
				if (n>=cantidad) {
					// se decrementa el número de unidades
					stmt.executeUpdate("UPDATE INVENTARIO SET CANTIDAD="+(n-cantidad)+" WHERE ID="+id+";");
				} else {
					throw new DBException("getProducto -> 'cantidad' debe tener un valor inferior al inventario ("+cantidad+" > "+n+")");
				}
			}else if (cantidad==0){
				producto = new ProductoImpl(rs.getFloat("PRECIO"), rs.getString("NOMBRE"), rs.getInt("ID"), rs.getInt("CANTIDAD"));
			}
		} else if (cantidad <1) {
			throw new DBException("getProducto -> 'cantidad' debe tener un valor positivo");
		}
		System.out.println("getProducto("+id+", "+cantidad+") -> "+producto.getNombre());
		return producto;
		
	}
	
	@Override
	public int getCantidadProducto (int id) throws Exception{
                int cantidad = 0;
        
                ResultSet rs = stmt.executeQuery("SELECT CANTIDAD FROM INVENTARIO WHERE ID="+id);
                if (rs.next()) {
                	cantidad = rs.getInt("CANTIDAD");
                } else{
			throw new DBException("el producto con id "+id+" no existe");
		}

                System.out.println("getCantidadProducto("+id+") -> "+cantidad);
                return cantidad;

	}


	@Override
	public int getId(String nombre) throws DBException, Exception {
		
		int id = 0;
		ResultSet rs = stmt.executeQuery("SELECT ID FROM INVENTARIO WHERE NOMBRE='"+nombre+"';");
		if (rs.next()) 
			id = rs.getInt("ID");
		else
			throw new DBException("el producto con nombre '"+nombre+"' no existe.");
		System.out.println("getId('"+nombre+"') -> "+id);
		return id;
		
	}
	
	@Override
	public float getPrecio (int id) throws Exception{
		//No hace nada
		float a = 0;
		return a;
	}


	@Override
	public void addProducto(int id) throws Exception, DBException {
		System.out.println("addProducto("+id+")");
		ResultSet rs = stmt.executeQuery("SELECT CANTIDAD FROM INVENTARIO WHERE ID="+id+";");
		if (rs.next()) {
			stmt.executeUpdate("UPDATE INVENTARIO SET CANTIDAD="+(rs.getInt("CANTIDAD")+1)+" WHERE ID="+id+";");
			
		}else
			throw new DBException("el producto con id '"+id+"' no existe.");
		
	}

	@Override
	public void addProducto(int id, int cantidad) throws Exception, DBException {
		System.out.println("addProducto("+id+", "+cantidad+")");
		ResultSet rs = stmt.executeQuery("SELECT CANTIDAD FROM INVENTARIO WHERE ID="+id+";");
		if (rs.next()) {
			stmt.executeUpdate("UPDATE INVENTARIO SET CANTIDAD="+(rs.getInt("CANTIDAD")+cantidad)+" WHERE ID="+id+";");
			
		}else
			throw new DBException("el producto con id '"+id+"' no existe.");
		
	}

	@Override
	public int nuevoProducto(String nombre, float precio, int cantidad) throws Exception, DBException {
		//Obtenemos el ID más alto y le sumamos 1
		int id=0;
		ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS ID FROM INVENTARIO;");
		
		
		if (rs.next() && cantidad >= 1) {
			id = rs.getInt("ID")+1;
			ResultSet rs2 = stmt.executeQuery("SELECT NOMBRE FROM INVENTARIO;");
			Boolean repetido = false;
			while(rs2.next())
				if (rs2.getString("NOMBRE").equals(nombre))
					repetido = true;
			
			if (repetido) {
				//System.err.println("nuevoProducto -> el producto '"+nombre+"' ya está registrado");
				id = 0;
			}
			else
				stmt.executeUpdate("INSERT INTO INVENTARIO (ID,NOMBRE,PRECIO,CANTIDAD)"
								+ " VALUES("+id+",'"+nombre+"',"+precio+","+cantidad+" );");
		} else if (cantidad <1)
			throw new DBException("nuevoProducto -> 'cantidad' debe ser >=1 ("+cantidad+")");
		System.out.println("nuevoProducto("+nombre+", "+precio+", "+cantidad+") -> "+id);
		return id;
	}

	@Override
	public void setNombreProducto(int id, String nombre) throws Exception, DBException {
		System.out.println("setNombreProducto("+id+", "+nombre+")");
		ResultSet rs = stmt.executeQuery("SELECT 1 FROM INVENTARIO WHERE ID="+id+";");
		if(!rs.next())
			throw new DBException("el producto con id '"+id+"' no existe.");
		else
			stmt.executeUpdate("UPDATE INVENTARIO SET NOMBRE='"+nombre+"' WHERE ID="+id+";");	
		
	}

	@Override
	public void setPrecioProducto(int id, float precio) throws Exception, DBException {
		System.out.println("setPrecioProducto("+id+", "+precio+")");
		ResultSet rs = stmt.executeQuery("SELECT 1 FROM INVENTARIO WHERE ID="+id+";");
		if(!rs.next())
			throw new DBException("el producto con id '"+id+"' no existe.");
		else if(precio > 0.0f)
			stmt.executeUpdate("UPDATE INVENTARIO SET PRECIO="+precio+" WHERE ID="+id+";");		
		else
			throw new DBException("el producto con id '"+id+"' no puede tener un precio inferior a 0 ("+precio+")");
		
	}

	@Override
	public void setCantidadProducto(int id, int cantidad) throws Exception, DBException {
		System.out.println("setCantidadProducto("+id+", "+cantidad+")");
		ResultSet rs = stmt.executeQuery("SELECT 1 FROM INVENTARIO WHERE ID="+id+";");
		if(!rs.next())
			throw new DBException("el producto con id '"+id+"' no existe.");
		else if (cantidad > 0)
			stmt.executeUpdate("UPDATE INVENTARIO SET CANTIDAD="+cantidad+" WHERE ID="+id+";");		
		else
			stmt.executeUpdate("DELETE FROM INVENTARIO WHERE ID="+id+";");
		
	}
	
	@Override
	public void eliminaProducto(int id) throws Exception{
		ResultSet rs = stmt.executeQuery("SELECT * FROM INVENTARIO WHERE ID="+id);
		if (!rs.next())
			throw new DBException("el producto con id '"+id+"' no existe.");
		else
			stmt.executeUpdate("DELETE FROM INVENTARIO WHERE ID="+id+";");
	}


	@Override
	/**
	 * Sobreescribimos el método finalize() para asegurarnos de que la BD
	 * y el statement se cierran antes de ser eliminados.
	 */
	protected void finalize() throws Throwable{
		try {
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		try {
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}


	
}
