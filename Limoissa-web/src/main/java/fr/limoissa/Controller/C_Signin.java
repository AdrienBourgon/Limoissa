/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Controller;

import fr.limoissa.Model.Member;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Adrien
 */
public class C_Signin extends HttpServlet {

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
        doPost(request, response);
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
        
        Boolean OK = null != request.getParameter("SI_Mail") && null != request.getParameter("SI_Password");
        
        try
        {
            Member M = fr.limoissa.DAO.Member.Signin(request.getParameter("SI_Mail"), request.getParameter("SI_Password"));
        
            if(null == M)
            {
                if(OK) request.setAttribute("Error", "Le couple Mail/Mot de passe est incorrect, veuillez réessayer.");
                request.getRequestDispatcher("?page=Signin").forward(request, response);
            }
            else
            {
                request.getSession().setAttribute("Member", M);
                response.sendRedirect("Library");
            }
        }
        catch(NullPointerException e)
        {
            request.setAttribute("Error", "Une erreur est survenue avec la base de données, merci de réessayer plus tard.");
            request.getRequestDispatcher("?page=Signin").forward(request, response);
        }
    }
}
