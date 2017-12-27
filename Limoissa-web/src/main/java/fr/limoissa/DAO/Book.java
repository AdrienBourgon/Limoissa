/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Adrien
 */
public class Book implements fr.limoissa.Interface.DAO<fr.limoissa.Model.Book> {
    private static final HashMap<Integer, fr.limoissa.Model.Book> Books = new LinkedHashMap<>();
    
    @Override
    public fr.limoissa.Model.Book get(int Id)
    {
        if(Books.containsKey(Id))
            return Books.get(Id);
        
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Book);
            St.setInt(1, Id);
            
            ResultSet RS = St.executeQuery();
            
            if(null != RS && RS.next()) // S'il y a un résultat
            {
                Books.put(Id, BuildBook(RS));
                return Books.get(Id);
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return null;
    }
    
    @Override
    public Collection<fr.limoissa.Model.Book> getAll() { return this.getAll("", "", 1, Integer.MAX_VALUE); }
    public Collection<fr.limoissa.Model.Book> getAll(String Title, String OrderBy, int Page, int Elements)
    {
        SQL S = new SQL(); // Récupération de la connexion sql
        
        Collection<fr.limoissa.Model.Book> Books = new ArrayList<>();
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(String.format(SQLRequest.Select.Books, OrderBy));
            
            St.setString(1, Title);
            St.setInt(2, Page);
            St.setInt(3, Elements);
            
            ResultSet RS = St.executeQuery();
            
            if(null != RS)
            while(RS.next()) // On lit tant qu'il y a des résultats
            {
                fr.limoissa.Model.Book B = BuildBook(RS);
                
                // On ajoute à la map statique de la class
                if(!Book.Books.containsKey(B.getId()))
                    Book.Books.put(B.getId(), B);
                
                // Et à la liste des résultats
                Books.add(B);
            }
        }
        catch (SQLException | NullPointerException e) { }
        finally { S.Close(); }
        
        // On retourne tous les éléments de la liste
        return Books;
    }
    
    @Override
    public Boolean add(fr.limoissa.Model.Book Book)
    {
        SQL S = new SQL(SQL.Role.Insert); // Récupération de la connexion sql
        Factory Factory = fr.limoissa.DAO.Factory.getInstance();

        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Insert.Book, Statement.RETURN_GENERATED_KEYS);

            St.setString(1, Book.getTitle());
            St.setString(2, Book.getSummary());
            St.setFloat(3, Book.getPrice());
            St.setInt(4, Book.getStock());
            St.setString(5, Book.getPublicationDate());
            St.setInt(6, Book.getAuthor().getId());
            
            if(null == Book.getCategory() || Book.getCategory().getId() == -1) St.setNull(7, java.sql.Types.INTEGER);
            else St.setInt(7, Book.getCategory().getId());
            
            if(St.executeUpdate() == 1)
            {
                // Récupération de l'Id inséré
                ResultSet RS = St.getGeneratedKeys();
                
                if(null != RS && RS.next())
                {
                    Book.setId(RS.getInt(1));
                    Book.setAuthor(Factory.getAuthorDAO().get(Book.getAuthor().getId()));
                    Book.setCategory(Factory.getCategoryDAO().get(null == Book.getCategory() ? -1 : Book.getCategory().getId()));
                    Books.put(Book.getId(), Book);
                    return true;
                }
                    
            }
        }
        catch (SQLException | NullPointerException e)
        {
        }
        finally { S.Close(); }
        
        return false;
    }
    
    @Override
    public Boolean edit(fr.limoissa.Model.Book Book)
    {
        if(!Books.containsKey(Book.getId()))
            return false;
        
        SQL S = new SQL(SQL.Role.Update); // Récupération de la connexion sql
        Factory Factory = fr.limoissa.DAO.Factory.getInstance();
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Update.Book);
            
            St.setString(1, Book.getTitle());
            St.setString(2, Book.getSummary());
            St.setFloat(3, Book.getPrice());
            St.setInt(4, Book.getStock());
            St.setString(5, Book.getPublicationDate());
            St.setInt(6, Book.getAuthor().getId());
            
            // On ajoute Null s'il n'y a pas de catégorie
            if(Book.getCategory().getId() == -1) St.setNull(7, java.sql.Types.INTEGER);
            else St.setInt(7, Book.getCategory().getId());
            
            St.setInt(8, Book.getId());
            
            if(St.executeUpdate() == 1)
            {
                // Mise à jour de la map statique de la classe
                fr.limoissa.Model.Book B = Books.get(Book.getId());
                B.setTitle(Book.getTitle());
                B.setSummary(Book.getSummary());
                B.setStock(Book.getStock());
                B.setPrice(Book.getPrice());
                B.setPublicationDate(Book.getPublicationDate());
                B.setAuthor(Factory.getAuthorDAO().get(Book.getAuthor().getId()));
                B.setCategory(Factory.getCategoryDAO().get(Book.getCategory().getId()));
                
                return true;
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return false;
    }
    
    public Boolean delete(int Id)
    {
        SQL S = new SQL(SQL.Role.All); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Delete.Book);
            
            St.setInt(1, Id);
            
            if(St.executeUpdate() == 1)
            {
                // Mise à jour de la map statique de la classe
                if(Books.containsKey(Id))
                    Books.remove(Id);
                return true;
            }
        }
        catch (SQLException e) {}
        finally { S.Close(); }
        
        return false;
    }
    
    // Construction d'un objet Book à partir d'un ResultSet
    public static fr.limoissa.Model.Book BuildBook(ResultSet RS) throws SQLException
    {
        Factory Factory = fr.limoissa.DAO.Factory.getInstance();
        
        return new fr.limoissa.Model.Book(
                    RS.getInt("Id"),
                    RS.getString("Title"),
                    RS.getString("Summary"),
                    RS.getString("PublicationDate"),
                    RS.getInt("Stock"),
                    RS.getFloat("Price"),
                    Factory.getAuthorDAO().get(RS.getInt("IdAuthor")),
                    Factory.getCategoryDAO().get(RS.getInt("IdCategory")));
    }
}
