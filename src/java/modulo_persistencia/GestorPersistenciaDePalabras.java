/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import modulo_indexacion.Libro;
import modulo_indexacion.Palabra;

/**
 *
 * @author fedeb
 */
public class GestorPersistenciaDePalabras implements GestorPersistencia<Palabra> {

    private Conexion con;

    public GestorPersistenciaDePalabras(Conexion con) {
        this.con = con;
    }

    public int insertar(Palabra palabra) {

        int id = -1;
        try {
            PreparedStatement st = con.connect.prepareStatement("INSERT INTO palabras VALUES(?,1)");
            st.setString(1, palabra.getTexto());
            st.executeUpdate();
            st = con.connect.prepareStatement("SELECT MAX(id) FROM palabras");
            ResultSet rs = st.executeQuery();
            rs.next();
            id = rs.getInt("MAX(id)");

            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            //Codigo de error de unico = 19

        } finally {

        }
        return id;
    }

    public boolean insertar(Libro libro, ArrayList<Palabra> palabrasNuevas, ArrayList<Palabra> palabrasT, HashSet<Palabra> palabras) throws SQLException {
        boolean flag = false;
        con.autoCommit(false);

        try {
            PreparedStatement stUpdatePxL;
            PreparedStatement stPalabra;
            PreparedStatement stPalXLibro;

            //stPalXLibro = con.connect.prepareStatement("INSERT INTO palabraXLibro VALUES (?,?, 1)");
            stPalabra = con.connect.prepareStatement("INSERT INTO palabras VALUES(?,1)");

            for (int i = 0; i < palabrasNuevas.size(); i++) {
                stPalabra.setString(1, palabrasNuevas.get(i).getTexto());
                stPalabra.addBatch();
                //  stPalXLibro.setString(1, palabrasNuevas.get(i).getTexto());
                // stPalXLibro.setInt(2, libro.getId());
                // stPalXLibro.addBatch();

                if (i == 100000 || i == palabrasNuevas.size() - 1) {
                    stPalabra.executeBatch();
                    //stPalXLibro.executeBatch();
                }
            }
            stPalabra.close();
            // stPalXLibro.close();

            stPalXLibro = con.connect.prepareStatement("INSERT INTO palabraXLibro VALUES (?,?,0)");

            int j = 0;
            for (Iterator it = palabras.iterator(); it.hasNext();) {
                Palabra x = (Palabra) it.next();
                stPalXLibro.setString(1, x.getTexto());
                stPalXLibro.setInt(2, libro.getId());
                stPalXLibro.addBatch();
                j++;
                if (j == 100000 || it.hasNext() == false) {
                    stPalXLibro.executeBatch();
                }
            }

            stPalXLibro.close();

            stUpdatePxL = con.connect.prepareStatement("UPDATE palabraXLibro SET frecuencia=frecuencia+1 WHERE palabra = ? AND idLibro = ?");

            for (int i = 0; i < palabrasT.size(); i++) {
                stUpdatePxL.setString(1, palabrasT.get(i).getTexto());
                stUpdatePxL.setInt(2, libro.getId());
                stUpdatePxL.addBatch();

                if (i == 100000 || i == palabrasT.size() - 1) {
                    stUpdatePxL.executeBatch();

                }

            }
            stUpdatePxL.close();

            con.commit();
            flag = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            con.rollback();
            flag = false;
        } finally {
            con.autoCommit(true);

        }
        return flag;
    }

    public ArrayList<Integer> insertar(ArrayList<Palabra> palabras) {
        ArrayList<Integer> ids = new ArrayList();
        PreparedStatement st; //Codigo de error de unico = 19
        for (int i = 0; i < palabras.size(); i++) {
            ids.add(this.insertar(palabras.get(i)));
        }
        //ids = obtenerUltimosInsertados(palabras.size(),con);
        return ids;
    }

    public ArrayList<Palabra> obtenerPal() {

        ArrayList<Palabra> palabras = new ArrayList();

        try {
            try (PreparedStatement st = con.connect.prepareStatement("SELECT descripcion FROM palabras");
                    ResultSet result = st.executeQuery()) {

                while (result.next()) {
                    Palabra p = new Palabra(result.getString("descripcion").toLowerCase());
                    palabras.add(p);
                }

            }
        } catch (SQLException ex) {

        } finally {

        }

        return palabras;
    }

    // guarda, el segundo parámetro devuelve la sumatoria de las frecuencias
    public ArrayList<Palabra> obtener() {

        ArrayList<Palabra> palabras = new ArrayList();

        try {

            try (PreparedStatement st = con.connect.prepareStatement("SELECT palabras.descripcion , sum(palabraXLibro.frecuencia), count(DISTINCT palabraXLibro.idLibro) FROM palabras  JOIN palabraXLibro ON palabras.descripcion=palabraXLibro.palabra GROUP BY palabras.descripcion"); ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    palabras.add(new Palabra(result.getString(1), result.getInt(2), result.getInt(3)));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception ex) {

        } finally {

        }

        return palabras;
    }

    /**
     * 
     * @return Un ArrayList de Palabra, con estos atributos
     * - descripcion (Columna 1)
     * - frecuencia en un determinado libro (-1 por defecto, no interesa)
     * - frecuencia maxima (Columna 2)
     * - cantidad de libros en los que aparece (Columna 3)
     */
    public ArrayList<Palabra> obtenerVocabulario() {

        ArrayList<Palabra> palabras = new ArrayList();

        try {

            try (PreparedStatement st = con.connect.prepareStatement("SELECT palabras.descripcion, palabras.frecMax, count(DISTINCT palabraXLibro.idLibro) FROM palabras  JOIN palabraXLibro ON palabras.descripcion=palabraXLibro.palabra GROUP BY palabras.descripcion"); ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    palabras.add(new Palabra(result.getString(1), -1, result.getInt(2), result.getInt(3)));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception ex) {

        } finally {

        }

        return palabras;
    }

    public ArrayList<Palabra> buscar(String patron) {

        ArrayList<Palabra> palabras = new ArrayList();

        try {
            PreparedStatement st = con.connect.prepareStatement("SELECT descripcion FROM palabras WHERE descripcion LIKE '" + patron + "%'");

            ResultSet result = st.executeQuery();

            while (result.next()) {
                Palabra p = new Palabra(result.getString("descripcion"));
                palabras.add(p);
            }

            result.close();
            st.close();
        } catch (SQLException ex) {

        } finally {

        }

        return palabras;
    }

    public int obtenerId(String patron) {
        int id = -1;
        try {
            PreparedStatement st = con.connect.prepareStatement("SELECT id FROM palabras WHERE descripcion = '" + patron + "%'");

            ResultSet result = st.executeQuery();
            result.next();
            id = result.getInt("id");

            result.close();
            st.close();
        } catch (SQLException ex) {

        } finally {

        }

        return id;
    }

    /**
     * Devuelve la frecuencia de la palabra pasada por parámetro en cada uno de
     * los libros en donde se encuentra
     *
     * @param palabra La palabra cuya frecuencia en cada libro se desea conocer.
     * @return
     */
    DefaultTableModel obtenerFrecuenciaEnLibroPalabra(String palabra) {
        DefaultTableModel palabras = new DefaultTableModel();
        palabras.addColumn("Libro");
        palabras.addColumn("Frecuencia");
        try {
            try (PreparedStatement st = con.connect.prepareStatement("SELECT l.descripcion, p.frecuencia FROM palabraxlibro p, libros l WHERE p.idLibro = l.id and palabra = '" + palabra + "'"); ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    palabras.addRow(new Object[]{result.getString(1), result.getInt(2)});
                    //System.out.println(result.getString(1) + result.getInt(2));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception ex) {

        } finally {

        }

        return palabras;
    }
    
    public void actualizarFrecuencias() throws SQLException {
        PreparedStatement actualizacion = con.connect.prepareStatement("UPDATE palabras SET frecMax = (select MAX(frecuencia) from palabraxlibro where palabra = palabras.descripcion GROUP BY palabra)");
        actualizacion.executeUpdate();
        actualizacion.close();
    }
}
