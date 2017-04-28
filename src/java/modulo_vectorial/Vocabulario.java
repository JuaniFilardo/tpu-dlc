/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_vectorial;

import arbolAVL.AVLTreeSearch;
import java.util.ArrayList;
import modulo_indexacion.Palabra;

/**
 *
 * @author filardo
 */
public class Vocabulario {

    // Lista de palabras del vocabulario
    public AVLTreeSearch palabras;

    public Vocabulario() {
        palabras = new AVLTreeSearch();
    }

    /**
     * Carga el vocabulario en el arbol AVL
     *
     * @param alPalabras
     */
    public void cargarVocabulario(ArrayList<Palabra> alPalabras) {

        for (Palabra p : alPalabras) {
            palabras.add(p);
        }
    }

    /**
     *
     * @param consulta La consulta del usuario, la cual también es un ArrayList
     * de Palabras
     * @return Un ArrayList de las Palabras de la consulta, ordenadas de mayor a
     * menor frecuencia inversa (o lo que es lo mismo, menor a mayor cantidad de
     * documentos en los que aparece)
     */
    public ArrayList<Palabra> getPalabrasConsultaPorRelevancia(ArrayList<Palabra> consulta) {

        ArrayList<Palabra> al = new ArrayList();

        for (Palabra p : consulta) {

            // Vemos si existe la palabra en el vocabulario
            Palabra r = (Palabra) palabras.search(p);

            if (r != null) {
                al.add(r);
            }
        }
        // Ordenamos la consulta de menor a mayor nr
        return ordenarConsulta(al);
    }

    /**
     * 
     * @param al La lista a ordenar
     * @return Un ArrayList ordenado por cantidad de libros en los que aparece la Palabra.
     */
    private ArrayList<Palabra> ordenarConsulta(ArrayList<Palabra> al) {

        for (int i = 0; i < al.size() - 1; i++) {

            for (int j = i + 1; j < al.size(); j++) {

                if (al.get(i).getNr() > al.get(j).getNr()) {
                    Palabra aux = (Palabra) al.get(i);
                    al.set(i, al.get(j));
                    al.set(j, aux);
                }
            }
        }
        return al;
    }

}
