package modulo_indexacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author filardo
 */
public class Indexer {
    
    // Una ArrayList que contiene todos de los archivos a indexar.
    // Se va a cargar a partir del método agregarArchivo()
    private ArrayList<File> listaArchivos;
  
    
    public Indexer () {
        listaArchivos = new ArrayList<File>();
    }
    
    /**
     * Obtiene de un archivo de texto las rutas, crea archivos a partir de
     * cada una de ellas y los carga llamando al método agregarArchivo()
     * @param rutas Un archivo de texto con las rutas de los archivos
     * que van a ser indexados.
     */
    public void obtenerRutas(File rutas) {
        
        String path = "";
        
        try (Scanner sca = new Scanner(rutas)) {
            
            while (sca.hasNext()) {
                path = sca.nextLine();
                // Creo un archivo con dicha ruta
                File archivo = new File(path);
                // Lo agrego a listaArchivos
                boolean b = agregarArchivo(archivo);
                System.out.println(b);
            }
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getClass());
            System.out.println("No existe el archivo " + path);
        }
    }
    
    /**
     * Carga un nuevo archivo a la lista, siempre y cuando el mismo no exista de
     * antemano
     *
     * @param archivo El archivo que queremos agregar.
     * @return true si se cargó correctamente, false si no.
     */
    private boolean agregarArchivo(File archivo) {

        for (File path : listaArchivos) {
            // Si ya está en la lista no se agrega
            if (path.getName().compareToIgnoreCase(archivo.getName()) == 0) {
                return false;
            }
        }
        // Ahora recién lo agrego
        listaArchivos.add(archivo);
        return true;
    }

    public ArrayList<File> getListaArchivos() {
        return listaArchivos;
    }
}
