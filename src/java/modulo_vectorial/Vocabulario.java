/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_vectorial;

import java.util.ArrayList;
import modulo_indexacion.Palabra;

/**
 *
 * @author filardo
 */
public class Vocabulario {
 
    // Lista de palabras del vocabulario
    private ArrayList<Palabra> palabras;

    public Vocabulario() {
        palabras = null;
    }
    
    public Vocabulario(ArrayList<Palabra> palabras) {
        this.palabras = palabras;
    }

    /**
     * 
     * @param consulta La consulta del usuario, la cual también es un ArrayList
     * de Palabras
     * @return Un ArrayList de las Palabras de la consulta, ordenadas de mayor
     * a menor frecuencia inversa (o lo que es lo mismo, menor a mayor cantidad
     * de documentos en los que aparece)
     */
    public ArrayList<Palabra> getPalabrasConsultaPorRelevancia(ArrayList<Palabra> consulta) {
       
        for (Palabra p : consulta) {
            for (Palabra r : palabras) {
                
            if (p.getTexto().equalsIgnoreCase(r.getTexto())) {
                
            }
            }
        }
        
        return null;
    }
    
    
}
