/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_vectorial;

import java.util.ArrayList;
import modulo_indexacion.Libro;
import modulo_indexacion.Palabra;
import modulo_indexacion.Parser;
import modulo_persistencia.GestorPersistenciaGeneral;
/**
 *
 * @author filardo
 */
public class gestorConsulta {
 
    ArrayList<Palabra> consulta;
    Vocabulario vocabulario;
    GestorPersistenciaGeneral gpg;
    
    public gestorConsulta(String consulta) {
        this.consulta = Parser.parsearConsulta(consulta);
        this.vocabulario = new Vocabulario();
        this.gpg = new GestorPersistenciaGeneral();
    }
    
    /**
     * Busca el posteo de cada palabra de la consulta y los guarda en un
     * ArrayList de Libros. Si el libro ya existe en la lista, aumenta en 1
     * su peso, si no existe lo añade.
     * @return Un ArrayList de pesos
     */
    public ArrayList<Libro> resolverConsulta() {
        
        consulta = vocabulario.getPalabrasConsultaPorRelevancia(consulta);
        ArrayList<Libro> resultadoConsulta = new ArrayList();
        
        for (Palabra palabra : consulta) {
            ArrayList<Libro> posteo = gpg.getPosteo(palabra);
            
            for (Libro libro : posteo) {
                if (resultadoConsulta.contains(libro)) {
                    libro.setPeso(libro.getPeso()+1);
                } else {
                    resultadoConsulta.add(libro);
                }
            }
        }
       
        return resultadoConsulta;
    }
    
}
