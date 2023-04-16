import java.io.*;


class Log implements Serializable {
     private String nombreFichero;
     private String idApp;
     Log(String nomFich, String id) {
         nombreFichero = nomFich;
         idApp = id;
     }
     public String obtenerNombreFichero() {
         return nombreFichero;
     }
     public String obtenerIdApp() {
         return idApp;
     }
     public String toString() {
         return nombreFichero + " | " + idApp;
     }
}
