/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_indexacion;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author filardo
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Indexer indexer = new Indexer();
        
        // a este archivo hay que escribirle todas las rutas absolutas
        File rutas = new File("rutas.txt");
        
        ArrayList<Palabra> al = new ArrayList<Palabra>();
        
        indexer.obtenerRutas(rutas);

        System.out.println("Archivos cargados: ");

        System.out.println(indexer.getListaArchivos().toString());
        
        //File f = new File("/home/filardo/UTN/Tercer_Año/TSB/Practico_Posta/Libros/18166-8.txt");
        
        for (File f : indexer.getListaArchivos()) {

            Parser p = new Parser(f);
            
            try {
               
                al = p.parsearArchivoBuffered();
                
                for (Palabra palabra : al) {
                    System.out.println(palabra.toString());
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getClass().getName());
            }

        }
    }

}
