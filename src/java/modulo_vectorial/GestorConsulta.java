/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_vectorial;

import java.util.ArrayList;
import java.util.List;
import modulo_indexacion.Libro;
import modulo_indexacion.Palabra;
import modulo_indexacion.Parser;
import modulo_persistencia.GestorPersistenciaGeneral;
/**
 *
 * @author filardo
 */
public class GestorConsulta {
 
    private static final GestorConsulta INSTANCIA = new GestorConsulta();
    ArrayList<Palabra> consulta;
    Vocabulario vocabulario;
    GestorPersistenciaGeneral gpg;
    int N;
    
    private GestorConsulta() {
        this.vocabulario = new Vocabulario();
        this.gpg = new GestorPersistenciaGeneral();
        this.vocabulario.cargarVocabulario(gpg.obtenerPalabrasArray());
        this.obtenerN();
    }
    
    public GestorConsulta(String consulta) {
        this.consulta = Parser.parsearConsulta(consulta);
        this.vocabulario = new Vocabulario();
        this.gpg = new GestorPersistenciaGeneral();
        this.vocabulario.cargarVocabulario(gpg.obtenerPalabrasArray());
        this.obtenerN();
    }
    
    /**
     * Implementación del patrón Singleton
     * @return La instancia del Singleton
     */
    public static GestorConsulta getInstancia() {
        return INSTANCIA;
    }
    
    /**
     * Busca el posteo de cada palabra de la consulta y los guarda en un
     * List de Libros. Si el libro ya existe en la lista, aumenta en 1
     * su peso, si no existe lo añade.
     * @param consultaAsString La consulta como un string
     * @return Una List de pesos
     */
    public List<Libro> resolverConsulta(String consultaAsString) {
        
        consulta = Parser.parsearConsulta(consultaAsString);
        consulta = vocabulario.getPalabrasConsultaPorRelevancia(consulta);
        ArrayList<Libro> resultadoConsulta = new ArrayList();
        
        for (Palabra palabra : consulta) {
            ArrayList<Libro> posteo = gpg.getPosteo(palabra);
            Double idf = obtenerIdf(posteo);//tengo que obtener el idf, que es el log(N/nr), nr es el largo de la lista de posteo.
            for (Libro libro : posteo) {
                if (resultadoConsulta.contains(libro)) {
                    libro.setPeso(libro.getPeso() + idf*libro.getFrecuencia(palabra));
                } else {
                    resultadoConsulta.add(libro);
                    libro.setPeso(idf*libro.getFrecuencia(palabra));
                }
            }
        }
        try{
            return resultadoConsulta.subList(0, 16);
        }
        catch(Exception e){
            return resultadoConsulta;
        }
    }
    /**
     * Obtener cantidad de documentos de la base.
     */
    private void obtenerN(){
        this.N = gpg.obtenerCantidadDocumentosDeBase();
    }

    private Double obtenerIdf(ArrayList<Libro> posteo) {
        return Math.log(posteo.size()/N);
    }

    private double obtenerPeso(Double idf) {
        return Math.log(N);
    }

    public ArrayList<Palabra> getConsulta() {
        return consulta;
    }

    public void setConsulta(ArrayList<Palabra> consulta) {
        this.consulta = consulta;
    }

    public int getN() {
        return N;
    }
    
}
