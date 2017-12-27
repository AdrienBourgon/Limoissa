/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Controller;

import fr.limoissa.Model.Member;
import fr.limoissa.Model.SearchResults;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Adrien
 */
public class C_Library extends HttpServlet {

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

        /* Récupération du membre */
        HttpSession Session = request.getSession();
        Member M = (Member)Session.getAttribute("Member");
        
        
        /* Recherche utilisateur */
        Search(request, response);
        
        /* Gestion de la page */
        String Page = request.getParameter("page");
        
        request.setAttribute("Member", M);
        request.setAttribute("TitleToDisplay", "Librairie");
        request.setAttribute("PageToInclude", "WEB-INF/Library/Index.jsp");
        
        if(null != Page)
            request.getRequestDispatcher(Page).include(request, response);
        
        request.getRequestDispatcher("/Library.jsp").forward(request, response);
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
        
        /* Gestion de la page */
        String Page = request.getParameter("page");
        
        if(null != Page)
            request.getRequestDispatcher(Page).include(request, response);
        
        request.getRequestDispatcher("/Library.jsp").forward(request, response);
    }
    
    private void Search(HttpServletRequest request, HttpServletResponse response)
    {
        String Search = request.getParameter("S_Title");
        if(null != Search)
        {
            /* On récupère la valeur pour le ORDER BY */
            String OrderBy = request.getParameter("S_OrderBy"), OrderBy_Direction = request.getParameter("S_OrderBy_Direction");
            
            HashMap<String, Boolean> HM_OrderBy = new HashMap<>();
            if(null != OrderBy && null != OrderBy_Direction)
            {
                if(!OrderBy.isEmpty())
                    HM_OrderBy.put(OrderBy, !OrderBy_Direction.equals("DESC"));
            }
            
            /* La page */
            int Page = 1;
            try { Page = Integer.parseInt(request.getParameter("S_Page")); }
            catch (NumberFormatException | NullPointerException e) {}
            
            /* Résultats de la recherche */
            SearchResults Results = fr.limoissa.DAO.Search.SearchBooks(Search, HM_OrderBy, Page);
            
            // Attributs pour la jsp
            request.setAttribute("Page", Page);
            request.setAttribute("ColumnsOrder", OrderBy);
            request.setAttribute("Results", Results);
        }
    }

}
