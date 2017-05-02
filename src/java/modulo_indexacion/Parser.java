/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_indexacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author filardo
 */
public class Parser {

    private Libro libro;
    private File archivo;
    private String ruta;

    public Parser(Libro libro) {
        this.libro = libro;
        this.ruta = libro.getRuta();
        this.archivo = new File(ruta);
    }
    
    public Parser(File archivo) {
        this.libro = new Libro("Descripcion",archivo.getAbsolutePath());
        this.ruta = libro.getRuta();
        this.archivo = archivo;
    }

    /**
     * @deprecated @return un vector de palabras válidas encontradas en el
     * archivo. versión preliminar, faltan un par de cosas.
     */
    public String[] parsearArchivo() {

        String[] vector = new String[10];

        int i = 0;

        try (Scanner sca = new Scanner(archivo)) {
            String s = "", t = "";
            
            while (sca.hasNext()) { // está tomando siempre false esto. Por alguna razón el Juani del pasado lo deprecó.

                s = sca.next();
                // Paso a lower case para evitar que considere distintas palabras cuando son iguales.
                s = s.toLowerCase();
                // System.out.println(s);
                for (Character c : s.toCharArray()) {
                    if (Character.isAlphabetic(c)) {
                        t += c.toString();
                    }
                }

                if (t.length() > 0) {
                    if (vector.length * 0.9 < i) { //si está al 90%
                        vector = ajustarCapacidad(vector);
                    }
                    vector[i] = t.toLowerCase();
                    i++;
                    t = "";
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return vector;
    }

    /**
     * @deprecated @param vector el vector para cambiarle la capacidad
     * @return un vector con el doble de capacidad
     */
    public String[] ajustarCapacidad(String[] vector) {
        String[] nuevoVector = new String[(int) (vector.length * 1.5)]; //se crea uno nuevo de 1,5 de capacidad
        System.arraycopy(vector, 0, nuevoVector, 0, vector.length); //se copia el array
        return nuevoVector;
    }

    /**
     *
     * @return el encoding del libro
     * @throws java.io.FileNotFoundException
     */
    public String obtenerEncoding() throws FileNotFoundException {

        try (Scanner sca = new Scanner(new BufferedReader(new FileReader(archivo)))) {

            String s = "";

            while (sca.hasNext()) {
                s = sca.next();
                //System.out.println(s);
                if (s.compareToIgnoreCase("encoding:") == 0) {
                    libro.setEncoding(sca.next());
                    break;
                }
                // si no había ningún encoding va a quedar "UTF-8" que es por defecto
            }
            sca.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            return libro.getEncoding();
        }
    }

    /**
     *
     * @return un ArrayList de palabras ya limpias y separadas
     * @throws java.io.FileNotFoundException
     */
    public ArrayList<Palabra> parsearArchivoBuffered() throws FileNotFoundException {

        String[] vector = new String[10];
        int i = 0;
        String encoding = this.obtenerEncoding();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), encoding))) {

            String s;
            ArrayList<Palabra> palabras = new ArrayList();

            while ((s = br.readLine()) != null) {

                ArrayList<String> renglonPalabras = parsearRenglon(s);

                for (String pal : renglonPalabras) {

                    Palabra p = new Palabra(pal);
                    palabras.add(p);
                }
            }
            br.close();
            return palabras;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param s - El string que se va a parsear
     * @return un ArrayList de String, con "protopalabras"
     */
    private ArrayList<String> parsearRenglon(String s) {

        String t = "";
        ArrayList<String> renglonPalabras = new ArrayList();

        for (Character c : s.toCharArray()) {
            
            if (Character.isAlphabetic(c)) {
               
                t += c.toString();
                
            } else if (t.length() > 0) {
                renglonPalabras.add(t.toLowerCase());
                t = "";
            }
        }
        return renglonPalabras;
    }
    
    /**
     * 
     * @param s La consulta como String.
     * @return La misma consulta, pasada a ArrayList y sin caracteres no alfabéticos.
     */
     public static ArrayList<Palabra> parsearConsulta(String s) {

        String t = "";
        ArrayList<Palabra> alConsulta = new ArrayList();

        for (Character c : s.toCharArray()) {
            
            if (Character.isAlphabetic(c)) {
               
                t += c.toString();
                
            } else if (t.length() > 0) {
                Palabra p = new Palabra(t.toLowerCase());
                // Para no añadir palabras repetidas.
                if (!alConsulta.contains(p)) {
                    alConsulta.add(p); 
                }
                t = "";
            }
        }
         if (t.length() > 0) {
             Palabra p = new Palabra(t.toLowerCase());
             alConsulta.add(p);
         }
        return alConsulta;
    }
}
