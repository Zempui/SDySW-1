package tienda;

class CreateTableException extends Exception{
	private static final long serialVersionUID = 1L;
	public CreateTableException(String str) {
		super(str);
	}
}

class CuentaException extends Exception{
	private static final long serialVersionUID = 1L;
	public CuentaException(String str) {
		super(str);
	}
}

class DBException extends Exception{
	private static final long serialVersionUID = 1L;
	public DBException(String str) {
		super(str);
	}
}