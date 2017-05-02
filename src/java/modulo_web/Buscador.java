/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo_web;

import Testing.Testing;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modulo_indexacion.Libro;
import modulo_vectorial.GestorConsulta;

/**
 *
 * @author filardo
 */
@WebServlet(name = "Buscador", urlPatterns = {"/Buscador"})
public class Buscador extends HttpServlet {
    
    GestorConsulta gc = new GestorConsulta();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        // Obtengo el parámetro del recurso campoBusqueda, lo guardo en un String
        String consulta = request.getParameter("campoBusqueda");
        consulta = this.limpiarConsulta(consulta);
        
        List<Libro> libros = gc.resolverConsulta(consulta);
        String descripciones[] = new String[libros.size()];
        String previews[] = new String[libros.size()];
        int i = 0;
        
        for (Libro libro : libros) {
            descripciones[i] = libro.getDescripcion();
           
            try {
                libro.createPreview();
                // escapo las comillas simples para no tener problemas del lado de JS
                previews[i] = this.escaparComillas(libro.getPreview());
                System.out.println(libro.getPreview());
            } catch (IOException ex) {
                Logger.getLogger(Testing.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            i++;                
            }         
        }
        
        // Seteo el resultadoConsulta con lo que le paso. Este atributo luego
        // lo uso como quiero en el cliente: en un h1, p, tabla, lo que sea
        request.setAttribute("resultadoConsulta", descripciones);
        request.setAttribute("previewsConsulta", previews);
        // Dejo la consulta en el input para que sea más fácil ver lo que se buscó
        request.setAttribute("textoConsulta", consulta);
        // forwardeo la request a otro archivo
        request.getRequestDispatcher("resultados.jsp").forward(request, response);
        
        // otra forma de hacerlo es con la sesión
        //request.getSession().setAttribute("resultadoConsulta", consulta);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    /**
     * 
     * @param consulta La consulta a limpiar. Evita que se realice un
     * ataque XSS desde el campo de búsqueda.
     * @return 
     */
    private String limpiarConsulta(String consulta){
    
        String consultaLimpia = "";
        String s = "";
        
        for (Character c : consulta.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                s += c.toString();
            } else if (s.length() > 0) {
                consultaLimpia += s + " ";
                s = "";
            }
        }
        // Para no perder la última palabra
        consultaLimpia += s;
        
        return consultaLimpia;
    }

    /**
     * Escapa las comillas simples (') para no tener problemas cuando
     * se pasa por parámetro una preview a una función JavaScript.
     * JS interpreta la comilla como fin del parámetro, ocasionando un
     * error al querer mostrar las previews.
     * @param preview La preview que vamos a limpiar de comillas
     * @return La preview con las comillas escapadas.
     */
    private String escaparComillas(String preview) {
        
        String previewLimpia = "";
        
        for (Character c : preview.toCharArray()) {
            if (c.toString().equalsIgnoreCase("\'")) {
                previewLimpia += "\\'";
            } else {
                previewLimpia += c;
            }
        }
        return previewLimpia;
    }

}
