/**
 * 
 */
/**
 * @author alvar
 *
 */
module Tienda {
	requires java.sql;
	requires org.xerial.sqlitejdbc;
	requires java.rmi;
	exports tienda.server;
	exports tienda.common;
	exports tienda.client;
}