/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_persistencia;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;
import modulo_indexacion.Libro;
import modulo_indexacion.Palabra;

/**
 *
 * @author fedeb
 */
public class GestorPersistenciaGeneral {

    public DefaultTableModel obtenerPalabrasPorLibro(String palabra){
        Conexion con = new Conexion();
        con.connect();
        GestorPersistenciaDePalabras gp = new GestorPersistenciaDePalabras(con);
        DefaultTableModel palabras = gp.obtenerFrecuenciaEnLibroPalabra(palabra);
        con.close();
        return palabras;
    }

    public ArrayList obtenerPalabras() {
        Conexion con = new Conexion();
        con.connect();
        GestorPersistenciaDePalabras gp = new GestorPersistenciaDePalabras(con);
        ArrayList palabras = gp.obtener();
        con.close();
        return palabras;
    }
    
    public ArrayList obtenerPalabrasLibro(Libro libro) {
        Conexion con = new Conexion();
        con.connect();
        GestorPersistenciaDeLibros gl = new GestorPersistenciaDeLibros(con);
        ArrayList palabras = gl.obtenerPalabrasXLibro(libro);
        con.close();
        return palabras;
    }

     public ArrayList<Palabra> obtenerPalabrasArray() {
        Conexion con = new Conexion();
        con.connect();
        GestorPersistenciaDePalabras gp = new GestorPersistenciaDePalabras(con);
        ArrayList<Palabra> palabras = gp.obtenerPal();
        con.close();
        return palabras;
    }
    
    
    
    public boolean insertarPalabras(Libro libro, ArrayList<Palabra> palabrasNuevas, ArrayList<Palabra> palabrasT,HashSet<Palabra> palabras) throws SQLException {
       
        boolean flag = false;
        Conexion con = new Conexion();
        
        try{
            con.connect();
          
            
           
            GestorPersistenciaDeLibros gpl = new GestorPersistenciaDeLibros(con);
            if ( gpl.exist(libro) != -1) return false;
            int idLibro = gpl.insertar(libro);
            libro.setId(idLibro);
            
            
            GestorPersistenciaDePalabras gp = new GestorPersistenciaDePalabras(con);
            System.out.println(libro.getId());
            flag = gp.insertar(libro, palabrasNuevas, palabrasT, palabras);
            
            if (!flag) {
                gpl.borrarLibro(libro);
            }
            
        }
        catch(Exception e){
            
        }
        finally {
            con.close();
        }
        return flag;
    }

    //se encarga de dar de alta en tablas como palabraXLibro y gestionar la lógica de donde va cada cosa

    public Boolean comprobarLibro(String nombre) {
        
        Conexion con = new Conexion();
        con.connect();
        GestorPersistenciaDeLibros gl = new GestorPersistenciaDeLibros(con);
        boolean respuesta = gl.existeLibro(nombre);
        con.close();
        return respuesta;
        
        
    }
}
