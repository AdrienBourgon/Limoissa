/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Adrien
 */
public class C_Signup extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet S_Signup</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet S_Signup at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        //processRequest(request, response);
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
        
        String FirstName = request.getParameter("SU_FirstName"), LastName = request.getParameter("SU_LastName");
        String Mail = request.getParameter("SU_Mail"), Mail2 = request.getParameter("SU_Mail2");
        String Password = request.getParameter("SU_Password"), Password2 = request.getParameter("SU_Password2");
        
        // Vérification que les valeurs ne sont pas nulles
        Boolean OK = true;
        for(String S: new String[] {FirstName, LastName, Mail, Mail2, Password, Password2})
            if(null == S)
            {
                OK = false;
                break;
            }
        
        if(OK)
        {
            // Vérification que les mots de passe et emails soient identiques
            List<String> Errors = new ArrayList<>();
            if(FirstName.isEmpty()) Errors.add("Vous devez renseigner votre prénom.");
            if(LastName.isEmpty()) Errors.add("Vous devez renseigner votre nom.");
            if(Mail.isEmpty()) Errors.add("Vous devez renseigner votre e-mail");
            if(Password.isEmpty()) Errors.add("Vous devez renseigner un mot de passe.");
            if(!Mail.equals(Mail2)) Errors.add("Les deux adresses e-mail ne sont pas identiques.");
            if(!Password.equals(Password2)) Errors.add("Les deux mots de passe ne sont pas identiques.");
            
            if(Errors.isEmpty())
            {
                // Si l'inscription ne se fait pas
                if(!fr.limoissa.DAO.Member.Signup(FirstName, LastName, Mail, Password))
                    Errors.add("Une erreur est survenue pendant l'inscription, veuillez réessayer.");
                else
                    request.setAttribute("SignupMessage", "Inscription effectuée, vous pouvez vous connecter");
            }
            else
                request.setAttribute("SignupErrors", Errors);
        }
        
        request.getRequestDispatcher("?page=Signin").forward(request, response);
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
