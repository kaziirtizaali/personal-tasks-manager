/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;

/**
 *
 * @author Ratul
 */
public class signIn extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {

            String username = html2text(request.getParameter("userNameLoginField"));
            String password = html2text(request.getParameter("userPasswordLoginField"));

            Connection con = DatabaseConnection.dbConnector();

            try {

                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username.trim().toLowerCase() + "' and password ='" + password + "'");
                ResultSet getID = stmt.getResultSet();
                if (rs.next()) {
                    int idVal = getID.getInt("id");
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", username);
                    session.setAttribute("userID", idVal);
                    //String user = (String) session.getAttribute("user");
                    //Integer userID = (Integer) session.getAttribute("userID");
                    //out.println("Logged IN and Your ID is "+userID+ " and user name is "+user);
                    response.sendRedirect("userPage.jsp");
                } else {
                    out.println("Username or Password Doesn't match. <a href=\"http://localhost:10000/WebTestApp/\">Please try again</a>");
                    //response.sendRedirect("index.html");
                }

            } catch (Exception e) {
                out.println(e);
            }
        }
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
