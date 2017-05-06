/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_indexacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import modulo_persistencia.GestorPersistenciaGeneral;

/**
 *
 * @author filardo
 */
public class Libro implements Comparable {

    private int id;
    private String descripcion; //puede ser el nombre del archivo, el titulo, etc
    private String ruta; // la ruta del archivo desde donde lo cargamos
    private String encoding;
    private String preview; 
    private double peso; // el peso o prioridad del libro en una consulta
    public static final String UTF = "UTF-8";

    public Libro(String descripcion, String ruta) {
        this.id = -1;
        this.descripcion = descripcion;
        this.ruta = ruta;
        this.encoding = UTF;
        this.peso = 0;
        this.preview = "";
    }

    public Libro(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.encoding = UTF;
        this.peso = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public static String getUTF() {
        return UTF;
    }

    public Palabra[] mostrarPalabras() {
        return null;
    } //muestra todas las palabras del archivo

    //Agregar
    @Override
    public int compareTo(Object l) {
        Libro li = (Libro) l;
        return this.getDescripcion().compareTo(li.getDescripcion());
    }
    
    public boolean equals (Object objeto){
        if (objeto == null ) return false;
        Libro libro = (Libro) objeto;
        if (this.getDescripcion().equals(libro.getDescripcion())) return true;
        return false;
    }

    public Boolean comprobarExistencia() {
        GestorPersistenciaGeneral gpg = new GestorPersistenciaGeneral();
        return gpg.comprobarLibro(descripcion);
    }

    public int getFrecuencia(Palabra palabra) {
        GestorPersistenciaGeneral gpg = new GestorPersistenciaGeneral();
        return gpg.getFrecuenciaPalabraEnLibro(palabra, this);
        
    }

    public String getPreview() {
        return preview;
    }

    public void createPreview() throws FileNotFoundException, IOException {
        File libro = new File(ruta);
        FileReader fr = new FileReader(libro);
        BufferedReader br = new BufferedReader(fr);
        for (int i = 0; i < 3; i++) {
            String linea = br.readLine();
            if(linea.compareTo("") == 0) i--;
            else {
                preview += linea;
            }
        }
        preview += ".....";
    }
}
