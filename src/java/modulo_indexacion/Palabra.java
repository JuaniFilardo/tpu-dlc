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

    /**
     * Calcula el peso que tiene la palabra para el libro pasado por parámetro
     * @param libro El libro mediante el cual se pesará la palabra
     * @param frecuenciaPalabra La frecuencia de esa palabra en dicho libro.
     * Esto se consigue con el método getFrecuenciaPalabra(palabra,libro) de GestorPersistenciaDeLibros.
     * @param cantLibrosBase La cantidad de libros de la base de datos.
     * Esto se consigue con el método getCantidadDeLibrosBase() de GestorPersistenciaDeLibros.
     * @return El peso de la palabra (this)
     */
    public double calcularPeso(Libro libro, int frecuenciaPalabra, int cantLibrosBase) {
        
        int tf = frecuenciaPalabra;
        double frecInv = this.calcularFrecuenciaInversa(cantLibrosBase);
        double modulo = libro.calcularModulo();
        
        return (tf*frecInv)/modulo;
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
    
    /**
     * @deprecated Directamente lo sacamos de la base!
     * Obtiene la frecuencia de la palabra en determinado libro
     * @param libro El libro en el cual se busca la palabra
     * @return Un entero que indica la frecuencia de la palabra
     * @deprecated 
     */
    public int getFrecuencia(Libro libro) {
        // Básicamente hay que contar las veces que this
        // aparece en el libro
        return 0;
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
