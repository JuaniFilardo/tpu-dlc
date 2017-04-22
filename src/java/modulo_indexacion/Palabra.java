/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_indexacion;

import java.util.ArrayList;

/**
 *
 * @author filardo
 */
public class Palabra implements Comparable {

    private String texto;
    private int frecuencia; //frecuencia en un determinado libro (al momento de cargar se utilizará)
    private int frecuenciaMaxima; // la máxima frecuencia que tiene esa palabra (no importa ahora en qué libro)
    private int libros; //cantidad de libros en los que aparece la palabra

    public Palabra(String texto) {

        this.texto = texto;

    }

    public Palabra(String texto, int frecuencia, int libros) {
        this.texto = texto;
        this.frecuencia = frecuencia;
        this.libros = libros;
    }
    

    public Palabra(String texto, int frecuencia) {
        this.texto = texto;
        this.frecuencia = frecuencia;
    }
    
 
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getLength() {
        return texto.length();
    }

    public ArrayList<Libro> mostrarLibros() {
        return null;
    } //Busca en la bd y retorna un arrayList con los libros en donde apareció la
    //palabra

    //calcularFrecuenciaTotal()
    //calcularFrecuenciaPorLibro()
    @Override
    public String toString() {
        return texto;
    }

    @Override
    public int compareTo(Object p) {

        Palabra pal = (Palabra) p;
        return this.getTexto().compareTo(pal.getTexto());

    }

    public boolean equals (Object objeto){
        if (objeto == null ) return false;
        Palabra palabra = (Palabra)objeto;
        if (this.getTexto().equals(palabra.getTexto())) return true;
        return false;
    }
    
    public int hashCode(){
        return this.getTexto().hashCode();
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getLibros() {
        return libros;
    }

    public void setLibros(int libros) {
        this.libros = libros;
    }
    
    
}
