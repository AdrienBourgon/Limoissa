/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Controller;

import fr.limoissa.DAO.Factory;
import fr.limoissa.Model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Adrien
 */
public class C_LibraryBooks extends HttpServlet {

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
        String B_Id = request.getParameter("B_Id");
        
        if (null != B_Id)
        {
            try
            {
                int Id = Integer.parseInt(B_Id);
                
                Book B = Factory.getBookDAO().get(Id);
                
                if(null != B)
                    request.setAttribute("BookToDisplay", B);
            }
            catch(NumberFormatException e) {}
        }
        
        /* Récupération des auteurs et des catégories */
        Collection<Author> Authors = new ArrayList<>();
        Collection<Category> Cat = new ArrayList<>();
        try
        {
            Authors = Factory.getAuthorDAO().getAll();
            Cat = Factory.getCategoryDAO().getAll();
        }
        catch (NullPointerException npe) { request.setAttribute("MainError", "Une erreur est survenue, merci de réessayer."); }
        
        request.setAttribute("Categories", Cat);
        request.setAttribute("Authors", Authors);
        request.setAttribute("TitleToDisplay", "Livres");
        request.setAttribute("PageToInclude", "WEB-INF/Library/Book.jsp");
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
        
        /* Si on a un fichier XML */
        Collection<Part> P = request.getParts();
        Part part = request.getPart("B_XML");
        
        if(null != part)
        {
            //Insertion dans la base de données
            int Insertions = fr.limoissa.DAO.XML.ParseXML(part.getInputStream());
            
            request.setAttribute("MessageXML", Insertions + " éléments insérés dans la base de données.");
        }
        
        /* Ajout d'un livre */
        if(null != request.getParameter("B_Add"))
            Add(request, response);
        
        /* Modification d'un livre */
        else if (null != request.getParameter("B_Edit"))
            Edit(request, response);
        
        /* Suppression d'un livre */
        else if (null != request.getParameter("B_Delete"))
            Delete(request, response);
        
        doGet(request, response);
    }
    
    private void Add(HttpServletRequest request, HttpServletResponse response)
    {
        String Title = request.getParameter("B_Title"), Summary = request.getParameter("B_Summary"), PublicationDate = request.getParameter("B_PublicationDate");
        int Stock = 0, Author = 0, Category = 0;
        float Price = 0;
        
        try
        {
            Stock = Integer.parseInt(request.getParameter("B_Stock"));
            Author = Integer.parseInt(request.getParameter("B_Author"));
            Price = Float.parseFloat(request.getParameter("B_Price").isEmpty() ? "0" : request.getParameter("B_Price"));
            Category = Integer.parseInt(request.getParameter("B_Category"));
        }
        catch (NumberFormatException | NullPointerException e) { }
        
        Boolean ok = true;
        
        if(null == Title || Title.isEmpty() || Author < 1) ok = false;

        if(!ok)
        {
            request.setAttribute("Error", "Les informations renseignées ne sont pas correctes, merci de les vérifier.");
        }
        else
        {
            ok = Factory.getInstance().getBookDAO().add(new Book(
                    Title, Summary, PublicationDate, Stock, Price,new Author(Author), new Category(Category)));

            if(ok) request.setAttribute("Message", "Livre enregistré avec succès.");
            else request.setAttribute("Error", "Une erreur est survenue lors de l'enregistrement des données, merci de réessayer.");
        }
    }
    
    private void Edit(HttpServletRequest request, HttpServletResponse response)
    {
        String Title = request.getParameter("B_Title"), Summary = request.getParameter("B_Summary"), PublicationDate = request.getParameter("B_PublicationDate");
        int Stock = 0, Author = 0, Category = 0, Id = 0;
        float Price = 0;
        
        try
        {
            Stock = Integer.parseInt(request.getParameter("B_Stock"));
            Author = Integer.parseInt(request.getParameter("B_Author"));
            Price = Float.parseFloat(request.getParameter("B_Price").isEmpty() ? "0" : request.getParameter("B_Price"));
            Id = Integer.parseInt(request.getParameter("B_Id"));
            Category = Integer.parseInt(request.getParameter("B_Category"));
        }
        catch (NumberFormatException | NullPointerException e) { }
        
        Boolean ok = true;
        
        if(null == Title || Title.isEmpty() || Author < 1 || Id < 1) ok = false;

        if(!ok)
        {
            request.setAttribute("Error", "Les informations renseignées ne sont pas correctes, merci de les vérifier.");
        }
        else
        {
            ok = Factory.getInstance().getBookDAO().edit(new Book(Id,
                    Title, Summary, PublicationDate, Stock, Price,new Author(Author), new Category(Category)));

            if(ok) request.setAttribute("Message", "Modification effectuée avec succès.");
            else request.setAttribute("Error", "Une erreur est survenue lors de l'enregistrement des données, merci de réessayer.");
        }
    }
    
    private void Delete(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            if(Factory.getInstance().getBookDAO().delete(Integer.parseInt(request.getParameter("B_Id"))))
                request.setAttribute("Message", "Livre supprimé avec succès.");
            else
                request.setAttribute("Error", "Une erreur est survenue lors de la suppression, merci de réessayer.");
        }
        catch (NumberFormatException | NullPointerException e)
        {
            request.setAttribute("Error", "Les informations entrées sont incorrectes.");
        }
    }
}
