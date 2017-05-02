/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fedeb
 */
public class Conexion {

    private String url;
    Connection connect;

    public Conexion() {
        url = "TSB.bd";
    }
    
    Conexion(String rutaDB) {
        url = rutaDB;
    }

    public void connect() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:" + url);
            if (connect != null) {
                System.out.println("Conectado");
            }
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar con la BD");
            System.out.println(ex);
        }
    }

    public void close() {
        try {
            connect.close();
        } catch (SQLException ex) {
            System.out.println("No cerró");
        }
    }

    public void autoCommit(boolean com) throws SQLException {
        if (connect != null) {
            connect.setAutoCommit(com);
        }

    }

    public void commit() throws SQLException {
        if (connect != null) {
            connect.commit();
        }
    }

    public void rollback() throws SQLException {
        if (connect != null) {
            connect.rollback();
        }

    }

}
