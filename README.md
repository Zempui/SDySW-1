# ShopWare

### Instrucciones de compilación/ejecución
- Descargar la última versión del controlador para [SQLite-JDBC](https://github.com/xerial/sqlite-jdbc/releases) (el archivo `.jar`)
- Colocar dicho archivo en la carpeta del usuario actual, por ejemplo, `/home/salas/`
- En caso de utilizar Eclipse IDE, abrir la carpeta descargada de este repositorio como proyecto Java. (se recomienda utilizar como workspace la carpeta `/home/salas/`)
- En caso de no estar utilizando el usuario "salas", en Eclipse, hacer click derecho en el proyecto "Tienda" -> Resource -> Java Build Path y añadir el `.jar` que nos hemos descargado.
- Una vez hemos completado los preparativos, para ejecutar el programa habrá que ejecutar un servidor (`tienda.server.serverTienda`) y uno o varios clientes (`tienda.client.ClienteMain`). Para ello, habrá que hacer click derecho en el archivo correspondiente -> "run as" -> "run configuration" y hacer lo siguiente:
  - En `serverTienda.java`, añadir en "Program arguments" el número de puerto seleccionado con rmiregistry[^1] y en "VM argumens" la línea `-Djava.security.policy=<Workspace>/bin/tienda/server/servidor.permisos`, sustituyendo "Workspace" por el directorio de trabajo de Eclipse (en nuestro caso, `/home/salas/Tienda`) 
  - En `ClienteMain.java`, añadir en "Program arguments" la IP del servidor (`localhost` en caso de ejecutarse todo en la misma máquina) y el puerto correspondiente y en "VM argumens" la línea `-Djava.security.policy=<Workspace>/bin/tienda/server/cliente.permisos`, sustituyendo "Workspace" por el directorio de trabajo de Eclipse (en nuestro caso, `/home/salas/Tienda`) 

[^1]: Previamente, se debe hacer ejecutado la orden `rmiregistry <num puerto> &`

