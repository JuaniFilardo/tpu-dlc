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
        int id=-1 ;//= exist(libro);
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
            try (PreparedStatement st = con.connect.prepareStatement("SELECT COUNT(*) FROM libros WHERE descripcion = '" + nombre +"'"); ResultSet result = st.executeQuery()) {
                return result.getInt(1) <= 0;
            }
        } catch (SQLException ex) {
                return false;
        } finally {

        }
    }

}
