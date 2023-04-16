# ShopWare

### Instrucciones de compilación/ejecución
- Descargar la última versión del controlador para [SQLite-JDBC](https://github.com/xerial/sqlite-jdbc/releases) (el archivo `.jar`)
- Colocar dicho archivo en la carpeta del usuario actual (`~`), por ejemplo, `/home/salas/`
- Descargar el archivo Zip "SDySW-1.zip" y descomprimirlo en la carpeta del usuario actual (`~`)
- Situarse en `~/SDySW/src/` y realizar la orden `javac -cp ~/sqlite-jdbc-3.40.0.0.jar:. *.java -d ../bin/` para compilar todos los archivos
- Una vez compilados, seleccionar un puerto para operar con rmiregistry[^1] y proceder con la ejecución
  - Para ejecutar el servidor, situarse en `~/SDySW/bin/` y utilizar la orden `java -cp ~/sqlite-jdbc-3.40.0.0.jar:. -Djava.security.policy=servidor.permisos serverTienda <num puerto>`
  - Para ejecutar el cliente, situarse en `~/SDySW/bin/` y utilizar la orden`java -cp ~/sqlite-jdbc-3.40.0.0.jar:. -Djava.security.policy=cliente.permisos ClienteMain localhost <num puerto> <nombre_historico> <ID_cliente>`

[^1]: La orden rmiregistry sigue el formato `rmiregistry <num puerto> &` y deberá ejecutarse en `~/SDySW/bin/`

