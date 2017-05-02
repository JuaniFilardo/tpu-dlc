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
import modulo_indexacion.Libro;
import modulo_indexacion.Palabra;

/**
 *
 * @author fedeb
 */
public class GestorPersistenciaDeLibros implements GestorPersistencia<Libro> {

    private Conexion con;

    public GestorPersistenciaDeLibros(Conexion con) {
        this.con = con;
    }

    public int insertar(Libro libro) {
        int id = -1;//= exist(libro);
        try {

            //if (id == -1) {
            PreparedStatement st = con.connect.prepareStatement("INSERT INTO Libros (descripcion) VALUES (?)");
            st.setString(1, libro.getDescripcion());
            st.executeUpdate();
            st = con.connect.prepareStatement("SELECT MAX(id) FROM libros");
            ResultSet rs = st.executeQuery();
            rs.next();
            id = rs.getInt("MAX(id)");

            rs.close();
            st.close();
            //  }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

        }
        return id;
    }

    public void borrarLibro(Libro libro) {

        try {
            PreparedStatement st = con.connect.prepareStatement("DELETE FROM libros WHERE id = ?");
            st.setInt(1, libro.getId());
            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {

        }

    }

    public ArrayList obtener() {
        ArrayList<Libro> libros = new ArrayList();

        try {
            PreparedStatement st = con.connect.prepareStatement("SELECT * FROM libros");
            ResultSet result = st.executeQuery();

            while (result.next()) {
                Libro p = new Libro(result.getInt("id"), result.getString("descripcion"));
                libros.add(p);
            }

            result.close();
            st.close();
        } catch (SQLException ex) {

        } finally {

        }

        return libros;

    }

    /**
     *
     * @param libro El libro a buscar en la base.
     * @return El id del libro, -1 si no existe.
     */
    public int exist(Libro libro) {

        int id = -1;

        try {
            PreparedStatement st = con.connect.prepareStatement("SELECT id FROM libros WHERE descripcion = ?");
            st.setString(1, libro.getDescripcion());
            ResultSet result = st.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
            result.close();
            st.close();
        } catch (SQLException ex) {

        } finally {

        }

        return id;
    }

    boolean existeLibro(String nombre) {

        try {
            try (PreparedStatement st = con.connect.prepareStatement("SELECT COUNT(*) FROM libros WHERE descripcion = '" + nombre + "'"); ResultSet result = st.executeQuery()) {
                return result.getInt(1) <= 0;
            }
        } catch (SQLException ex) {
            return false;
        } finally {

        }
    }

    /**
     *
     * @return La cantidad de libros en la base de datos, a.k.a. N
     */
    public int getCantidadDeLibrosBase() {
        try {
            try (PreparedStatement st = con.connect.prepareStatement("SELECT COUNT(*) FROM libros"); ResultSet result = st.executeQuery()) {
                return result.getInt(1);
            }
        } catch (SQLException ex) {
            return 0;
        }
    }

    public int getFrecuenciaPalabra(Palabra palabra, Libro libro) {

        try {
            // Obtengo el id del libro a buscar
            int idLibro = this.exist(libro);
            // Obtengo el texto con la palabra
            String textoPalabra = palabra.getTexto();

            PreparedStatement st = con.connect.prepareStatement("SELECT pxl.frecuencia FROM palabraxlibro pxl JOIN palabras p ON pxl.idPalabra=p.id WHERE pxl.idLibro=? AND p.descripcion=?");
            st.setInt(1, idLibro);
            st.setString(2, textoPalabra);
            ResultSet result = st.executeQuery();
            // Devuelvo la frecuencia
            return result.getInt(1);

        } catch (SQLException ex) {
            return 0;
        }

    }

    
    /**
     * 
     * @param libro El libro del cual se quieren obtener las palabras
     * @return La cantidad de palabras distintas del libro
     */
    public int getCantidadPalabrasDistintas(Libro libro) {

        try {
            // Obtengo el id del libro a buscar
            int idLibro = this.exist(libro);

            PreparedStatement st = con.connect.prepareStatement("SELECT COUNT(*) FROM palabraXLibro pxl WHERE pxl.idLibro = ?");
            st.setInt(1, idLibro);

            ResultSet result = st.executeQuery();
            // Devuelvo la frecuencia
            return result.getInt(1);

        } catch (SQLException ex) {
            return 0;
        }

    }

    /**
     * @param libro El libro del cual obtenemos las palabras
     * @return Las palabras que tiene el libro, sin repetir
     */
    public ArrayList<Palabra> obtenerPalabrasXLibro(Libro libro) {

        ArrayList<Palabra> palabras = new ArrayList();

        try {
            
            int idLibro = this.exist(libro);
            
            String sql = "SELECT DISTINCT palabras.descripcion, palabraXLibro.frecuencia,"
                    + " count(DISTINCT palabraXLibro.idLibro) FROM palabras JOIN"
                    + " palabraXLibro ON palabras.descripcion=palabraXLibro.palabra"
                    + " WHERE palabraXLibro.idLibro= " + idLibro + " "
                    + " GROUP BY palabras.descripcion";
            
            try (PreparedStatement st = con.connect.prepareStatement(sql); ResultSet result = st.executeQuery()) {
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
         * @param palabra La palabra cuyo posteo se desea conocer
         * @param n Número de libros que retorna la consulta
         * @return  ArrayList de libros y la frecuencia en cada uno
         */
        public ArrayList<Libro> getPosteo(Palabra palabra, int n) {

        try {
            
            String sql = "SELECT l.descripcion, pxl.frecuencia FROM "
                    + "palabraxlibro pxl JOIN libros l ON pxl.idlibro=l.id "
                    + "WHERE pxl.palabra = ? ORDER BY pxl.frecuencia DESC ";
            
            PreparedStatement st = con.connect.prepareStatement(sql);
            
            st.setString(1, palabra.getTexto());

            ResultSet result = st.executeQuery();
            ArrayList<Libro> al = new ArrayList();

            while (result.next()) {
                // La descripcion es el nombre del archivo
                String descripcion = result.getString(1);
                // Van a estar guardados en la carpeta libros, dentro de la carpeta del proyecto
                String ruta = "libros/" + descripcion;
                Libro libro = new Libro(descripcion, ruta);
                al.add(libro);
            }
            
            return al;

        } catch (SQLException ex) {
            return null;
        }

    }

    public int ObtenerCantidadLibros() {
        try {
            //Obtengo cantidad de libros de la base
            PreparedStatement st = con.connect.prepareStatement("SELECT COUNT() FROM libros");
            ResultSet result = st.executeQuery();
            return result.getInt(1);

        } catch (SQLException ex) {
            return 0;
        }
        
    }

    public int getFrecuenciaPalabraEnLibro(Palabra palabra, Libro libro) {
        try {
            //Obtengo la frecuencia de una palabra en un libro

            PreparedStatement st = con.connect.prepareStatement("SELECT COUNT() FROM palabraxlibro pxl "
                                                                + "JOIN libros l ON pxl.idLibro = libro.id"
                                                                + "WHERE palabra = ? AND libro.descripcion = ?");
            st.setString(1, palabra.getTexto());
            st.setString(2, libro.getDescripcion());
            
                
            ResultSet result = st.executeQuery();
            return result.getInt(1);

        } catch (SQLException ex) {
            return 0;
        }
        
    }

    
    
}
