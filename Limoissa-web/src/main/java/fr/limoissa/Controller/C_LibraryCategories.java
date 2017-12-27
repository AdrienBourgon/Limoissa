/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Controller;

import fr.limoissa.DAO.Factory;
import fr.limoissa.Model.Category;
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
public class C_LibraryCategories extends HttpServlet {

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
        String Id = request.getParameter("C_Id");
        
        if(null != Id)
        {
            // On récupère la catégorie depuis la base
            try
            {
                Category C = Factory.getCategoryDAO().get(Integer.parseInt(Id));
                
                request.setAttribute("CategoryToDisplay", C);
            }
            catch(NumberFormatException | NullPointerException e) {}
        }
        
        Collection<Category> Cat = new ArrayList<>();
        try
        {
            Cat = Factory.getCategoryDAO().getAll();
        }
        catch (NullPointerException npe) { request.setAttribute("MainError", "Une erreur est survenue, merci de réessayer."); }
        
        request.setAttribute("Categories", Cat);
        request.setAttribute("TitleToDisplay", "Catégories");
        request.setAttribute("PageToInclude", "WEB-INF/Library/Category.jsp");
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
        Boolean Add = request.getParameter("C_Add") != null, Edit = request.getParameter("C_Edit") != null, ok = true;
        
        String  C_Id = request.getParameter("C_Id"),
                Name = request.getParameter("C_Name"),
                C_IdParent = request.getParameter("C_Parent"),
                Disabled = request.getParameter("C_Disabled");
        
        int Id = 0, IdParent = 0;
        
        try {
            IdParent = Integer.parseInt(C_IdParent);
            Id = Integer.parseInt(C_Id);
        }
        catch (NumberFormatException e) { ok = false; }
        
        if(null == Disabled) Disabled = "off";
        
        if (Add) // Ajout
        {
            
            if(Name.isEmpty()) // S'il y a une erreur, on l'ajoute à la request
            {
                request.setAttribute("Error", "Les données entrées sont incorrectes, merci de les vérifier.");
            }
            else // Insertion dans la base de données
            {
                // En cas d'erreur lors de l'insertion
                Category Cat = new Category(Name, "on".equals(Disabled));
                    Cat.setParent(new Category(IdParent));
                
                if(!Factory.getCategoryDAO().add(Cat))
                    request.setAttribute("Error", "Une erreur est survenue lors de l'ajout, merci de réessayer.");
                else
                    request.setAttribute("Message", "Enregistrement effectué.");
            }
        }
        else if(Edit) // Modification
        {            
            for(String Str: new String[] {Name, Disabled })
                if(null == Str || Str.isEmpty())
                    ok = false;
            
            if(Id == IdParent) ok = false;
            
            if(!ok)
            {
                request.setAttribute("Error", "Les informations entrées sont incorrectes, merci de réessayer.");
            }
            else // Modification dans la base
            {
                try
                {
                    Category Cat = new Category(Id, Name, "on".equals(Disabled));
                        Cat.setParent(new Category(IdParent));
                    if(!Factory.getCategoryDAO().edit(Cat))
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
