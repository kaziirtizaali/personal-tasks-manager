/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebTest;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import java.sql.*;

/**
 *
 * @author Ratul
 */
public class signUp extends HttpServlet {

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

        PrintWriter out = response.getWriter();

        String username = html2text(request.getParameter("userNameField"));
        String email = html2text(request.getParameter("emailField"));
        String password = html2text(request.getParameter("passwordField"));
        String repeatPassword = html2text(request.getParameter("repeatPasswordField"));

        Connection con = DatabaseConnection.dbConnector();

        try {
            Statement stmt = con.createStatement();
            //ResultSet resultset;
            ResultSet resultset = stmt.executeQuery("SELECT username FROM users WHERE username = '" + username.trim().toLowerCase() + "' ");
            int numberRow = 0;
            while (resultset.next()) {
                numberRow = resultset.getInt("count(*)");
            }

            if (numberRow != 0) {
                out.println("Username already TAKEN");
            } else {
                stmt.executeUpdate("insert into users (username,email,password) values('" + username.trim().toLowerCase() + "','" + email + "','" + password + "');");
                out.println("<p>You Have been REGISTERED !!</p>");
                out.println("<a href=\"http://localhost:10000/WebTestApp/\">Click to Sign in</a>");
            }

        } catch (Exception e) {
            out.println(e);
        }

        /*try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
 /*  out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } 
        
         */
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
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

}
