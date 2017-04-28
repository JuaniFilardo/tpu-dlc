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
    private int frecuencia; // frecuencia de la palabra en det libro
    private int frecuenciaMaxima; // la frecuencia más alta con la que aparece en algún libro, cualquiera sea.
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

    // Constructor que se usará para el vocabulario
    public Palabra(String texto, int frecuencia, int frecuenciaMaxima, int libros) {
        this.texto = texto;
        this.frecuencia = -1; // no interesa esta frecuencia, así que por las dudas...
        this.frecuenciaMaxima = frecuenciaMaxima;
        this.libros = libros;
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

    /**
     * Calcula el peso que tiene la palabra para una consulta
     * @param frecuenciaPalabra La frecuencia de esa palabra.
     * Esto se consigue con el método getFrecuenciaPalabra(palabra,libro) de GestorPersistenciaDeLibros.
     * @param N La cantidad de libros de la base de datos.
     * Esto se consigue con el método getCantidadDeLibrosBase() de GestorPersistenciaDeLibros.
     * @return El peso de la palabra (this)
     */
    public double calcularPeso(int frecuenciaPalabra, int N) {
        
        int tf = frecuenciaPalabra;
        double frecInv = this.calcularFrecuenciaInversa(N);
        
        return (double)(tf*frecInv);
    }
    
    /**
     * Calcula la frecuencia inversa de la palabra. Esto se hace
     * con el logaritmo del cociente entre la cantidad de documentos
     * N y la cantidad de documentos donde aparece la palabra.
     * @param N la cantidad de documentos de la base.
     * @return frecuencia inversa de this para N documentos.
     */
    private double calcularFrecuenciaInversa(int N) {
        double a = (double) (N/libros);
        return Math.log(a);
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
