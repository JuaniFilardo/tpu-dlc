/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_indexacion;

import arbolAVL.AVLTreeSearch;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;
import modulo_indexacion.Libro;
import modulo_indexacion.Palabra;
import modulo_persistencia.GestorPersistenciaGeneral;

/**
 *
 * @author fedeb
 */
public class GestorIndexacion {

    public AVLTreeSearch avl;

    //Al invocarlo, levanta la base de datos y guarda los datos en un arbolAVL
    public GestorIndexacion() {


    }

    private void cargarArbol() {
        avl.clear();
        GestorPersistenciaGeneral palBD = new GestorPersistenciaGeneral();
        ArrayList<Palabra> palabras = palBD.obtenerPalabrasArray();
        //System.out.println(palabras.toString());
        for (Palabra p : palabras) {
            avl.add(p);
        }
    }

    public ArrayList<Palabra> obtenerPalabras(){
        GestorPersistenciaGeneral gpg = new GestorPersistenciaGeneral();
        return gpg.obtenerPalabras();
    }
    
    
    //Método para insertar a la BD nuevas palabras
    public boolean insertarPalabras(Libro libro, ArrayList<Palabra> p) throws SQLException {
        
        if (avl == null){
            avl = new AVLTreeSearch();
            cargarArbol();
        }
        
        ArrayList<Palabra> palabrasNuevas = new ArrayList();
        //ArrayList<Palabra> palabrasExistentes = new ArrayList();
        HashSet<Palabra> palabrasTotales = new HashSet();
        
        
        //Se utiliza esta estructura porque no quiero palabras repetidas
        for (Palabra palabra : p) {
            palabrasTotales.add(palabra);
        }
        
        for (int i = 0; i < p.size(); i++) {
            Palabra pal = (Palabra) avl.search(p.get(i));
            if (pal == null){
                Palabra nueva = new Palabra (p.get(i).getTexto());
                    palabrasNuevas.add(nueva);
                    avl.add(nueva);
            }
//            if (pal != null) {
//                 palabrasExistentes.add(pal);
//
//            } 
//            else {
//                Palabra nueva = new Palabra (p.get(i).getTexto());
//                palabrasNuevas.add(nueva);
//                avl.add(nueva);
//            }
        }
        
        
        

        GestorPersistenciaGeneral gpg = new GestorPersistenciaGeneral();
        //boolean flag = gpg.insertarPalabras(libro, palabrasNuevas, palabrasExistentes, palabrasExistentesSinAsociacion);
        boolean flag = gpg.insertarPalabras(libro, palabrasNuevas, p, palabrasTotales);
        //boolean flag = gpg.insertarPalabras(libro, palabrasNuevas,palabrasExistentes);
        //si obtuvimo un error, leemos nuevamente la bd para que el árbol quede bien
        if (!flag) {
            avl.clear();
            cargarArbol();
        }
        return flag;
    }

    //procesarPalabras
    //leerPalabras

    public DefaultTableModel traerCoincidenciasPalabra(String palabra) {
         GestorPersistenciaGeneral gpg = new GestorPersistenciaGeneral();
         return gpg.obtenerPalabrasPorLibro(palabra);
    }

    public boolean insertarPalabras(Libro libro, ArrayList<Palabra> p, String rutaDB) {
        
        if (avl == null){
            avl = new AVLTreeSearch();
            cargarArbol();
        }
        ArrayList<Palabra> palabrasNuevas = new ArrayList();
        //ArrayList<Palabra> palabrasExistentes = new ArrayList();
        HashSet<Palabra> palabrasTotales = new HashSet();
        //Se utiliza esta estructura porque no quiero palabras repetidas
        for (Palabra palabra : p) {
            palabrasTotales.add(palabra);
        }
        for (int i = 0; i < p.size(); i++) {
            Palabra pal = (Palabra) avl.search(p.get(i));
            if (pal == null){
                Palabra nueva = new Palabra (p.get(i).getTexto());
                    palabrasNuevas.add(nueva);
                    avl.add(nueva);
            }
        }
        GestorPersistenciaGeneral gpg = new GestorPersistenciaGeneral();
        //boolean flag = gpg.insertarPalabras(libro, palabrasNuevas, palabrasExistentes, palabrasExistentesSinAsociacion);
        boolean flag = gpg.insertarPalabras(libro, palabrasNuevas, p, palabrasTotales, rutaDB);
        //boolean flag = gpg.insertarPalabras(libro, palabrasNuevas,palabrasExistentes);
        //si obtuvimo un error, leemos nuevamente la bd para que el árbol quede bien
        if (!flag) {
            avl.clear();
            cargarArbol();
        }
        return flag;
    }

    public void updateFrecuencias(String rutaDB) throws SQLException {
        GestorPersistenciaGeneral gpg = new GestorPersistenciaGeneral();
        gpg.updateFrecuencias(rutaDB);
    }
}
