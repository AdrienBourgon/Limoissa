/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Controller;

import fr.limoissa.DAO.Factory;
import fr.limoissa.Model.Author;
import fr.limoissa.Model.Country;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Adrien
 */
public class C_LibraryAuthors extends HttpServlet {


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
        
        Factory Factory = fr.limoissa.DAO.Factory.getInstance();
        
        /* Si un Id est passé en paramètre */
        String Id = request.getParameter("A_Id");
        
        if(null != Id)
        {
            // On récupère la catégorie depuis la base
            try
            {
                Author A = Factory.getAuthorDAO().get(Integer.parseInt(Id));
                
                request.setAttribute("AuthorToDisplay", A);
            }
            catch(NumberFormatException | NullPointerException e) {}
        }
        
        
        /* Récupération des auteurs */
        Collection<Author> Authors = new ArrayList<>();
        try
        {
            Authors = Factory.getAuthorDAO().getAll();
        }
        catch (NullPointerException npe) { request.setAttribute("MainError", "Une erreur est survenue, merci de réessayer."); }
        
        /* Récupération des pays */
        Collection<Country> Countries = new ArrayList<>();
        try
        {
            Countries = Factory.getCountryDAO().getAll();
        }
        catch (NullPointerException npe) { request.setAttribute("MainError", "Une erreur est survenue, merci de réessayer."); }

        request.setAttribute("Authors", Authors);
        request.setAttribute("Countries", Countries);
        request.setAttribute("TitleToDisplay", "Auteur");
        request.setAttribute("PageToInclude", "WEB-INF/Library/Author.jsp");
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
        
        Factory Factory = fr.limoissa.DAO.Factory.getInstance();
        
        /* On a récupéré quelque chose ici, donc ajout/modification/suppression d'une catégorie */
        Boolean Add = request.getParameter("A_Add") != null, Edit = request.getParameter("A_Edit") != null, ok = true;
        
        String  A_Id = request.getParameter("A_Id"),
                FirstName = request.getParameter("A_FirstName"),
                LastName = request.getParameter("A_LastName"),
                A_Country = request.getParameter("A_Country"),
                Disabled = request.getParameter("A_Disabled");
        
        int Id = 0, Country = 0;
        
        try {
            Country = Integer.parseInt(A_Country);
            Id = Integer.parseInt(A_Id);
        }
        catch (NumberFormatException e) { ok = false; }
        
        if(null == Disabled) Disabled = "";
        
        if (Add) // Ajout
        {
            if(FirstName.isEmpty() || LastName.isEmpty()) // S'il y a une erreur, on l'ajoute à la request
            {
                request.setAttribute("Error", "Les données entrées sont incorrectes, merci de les vérifier.");
            }
            else // Insertion dans la base de données
            {
                // En cas d'erreur lors de l'insertion
                if(!Factory.getAuthorDAO().add(new Author(FirstName, LastName, new Country(Country), "on".equals(Disabled))))
                    request.setAttribute("Error", "Une erreur est survenue lors de l'ajout, merci de réessayer.");
                else
                    request.setAttribute("Message", "Enregistrement effectué.");
            }
        }
        else if(Edit) // Modification
        {
            for(String S: new String[] {FirstName, LastName })
                if(null == S || S.isEmpty())                    
                {
                    ok = false;
                    break;
                }
            
            if(!ok)
                request.setAttribute("Error", "Les informations entrées sont incorrectes, merci de réessayer.");
            else // Modification dans la base
            {
                try
                {
                    if(!Factory.getAuthorDAO().edit(new Author(Id, FirstName, LastName, new Country(Country), "on".equals(Disabled))))
                        request.setAttribute("Error", "Une erreur est survenue lors de la modification, merci de réessayer.");
                    else
                        request.setAttribute("Message", "Modification effectuée avec succès.");
                }
                catch (NumberFormatException e)
                {}
            }
        }
        
        doGet(request, response);
    }
}
