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
import java.util.HashMap;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
/**
 *
 * @author Adrien
 */
public class Author implements fr.limoissa.Interface.DAO<fr.limoissa.Model.Author> {
    
    private static final HashMap<Integer, fr.limoissa.Model.Author> Authors = new LinkedHashMap<>();
    
    @Override
    public Collection<fr.limoissa.Model.Author> getAll()
    {        
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Authors_OB_Name);
            
            ResultSet RS = St.executeQuery();
            
            if(null != RS)
            while(RS.next()) // On lit tant qu'il y a des résultats
            {
                // On ajoute à la map statique de la class
                if(!Authors.containsKey(RS.getInt("Id")))
                {
                    fr.limoissa.Model.Author A = BuildAuthor(RS);
                    Authors.put(A.getId(), A);
                }
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        // On retourne les élements de la map trié par nom de famille
        return Authors.values().stream().sorted((a, b) -> a.getLastName().compareToIgnoreCase(b.getLastName())).collect(Collectors.toCollection(ArrayList::new));
    }
    
    @Override
    public fr.limoissa.Model.Author get(int Id)
    {
        if(Authors.containsKey(Id))
            return Authors.get(Id);
        
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Author);
            St.setInt(1, Id);
            
            ResultSet RS = St.executeQuery();
            
            if(null != RS)
            if(RS.next()) // S'il y a un résultat
            {
                fr.limoissa.Model.Author A = BuildAuthor(RS);
                Authors.put(A.getId(), A);
                return A;
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return null;
    }
    
    @Override
    public Boolean add(fr.limoissa.Model.Author A)
    {
        SQL S = new SQL(SQL.Role.Insert); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Insert.Author, Statement.RETURN_GENERATED_KEYS);            
            St.setString(1, A.getFirstName());            
            St.setString(2, A.getLastName());
            St.setInt(3, A.getCountry().getId());
            St.setBoolean(4, A.getDisabled());
            
            if(St.executeUpdate() == 1)
            {
                // Récupération de l'Id inséré
                ResultSet RS = St.getGeneratedKeys();
                if(null != RS && RS.next())
                {
                    A.setId(RS.getInt(1));
                    A.setCountry(Factory.getInstance().getCountryDAO().get(A.getCountry().getId()));
                    Authors.put(A.getId(), A);
                    
                    return true;
                }
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return false;
    }
    
    @Override
    public Boolean edit(fr.limoissa.Model.Author A)
    {
        SQL S = new SQL(SQL.Role.Update); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Update.Author);
            St.setString(1, A.getFirstName());
            St.setString(2, A.getLastName());
            St.setInt(3, A.getCountry().getId());
            St.setBoolean(4, A.getDisabled());
            St.setInt(5, A.getId());
            
            if(St.executeUpdate() == 1)
            {
                // Mise à jour de la map statique de la classe
                if(Authors.containsKey(A.getId()))
                {
                    fr.limoissa.Model.Author Au = Authors.get(A.getId());
                    Au.setFirstName(A.getFirstName());
                    Au.setLastName(A.getLastName());
                    Au.setCountry(Factory.getInstance().getCountryDAO().get(A.getCountry().getId()));
                    Au.setDisabled(A.getDisabled());
                    
                    return true;
                }
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return false;
    }
    
    public int CreateIfNotExists(fr.limoissa.Model.Author A)
    {
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Author_By_Name);
            
            St.setString(1, A.getFirstName());
            St.setString(2, A.getLastName());
            St.setInt(3, A.getCountry().getId());
            
            ResultSet RS = St.executeQuery();
            
            if(null != RS)
            {
                // Si l'auteur existe
                if(RS.next())
                    return RS.getInt("Id");
                // S'il n'existe pas, on l'ajoute
                else
                {
                    add(A);
                    return A.getId();
                }
            }
        }
        catch (SQLException | NullPointerException e)
        {
        }
        finally { S.Close(); }
        
        return -1;
    }
    
    // Construction d'un objet Author à partir d'un ResultSet
    private static fr.limoissa.Model.Author BuildAuthor(ResultSet RS) throws SQLException
    {
        if(null == RS) return null;
        
        return new fr.limoissa.Model.Author(
                RS.getInt("A.Id"),
                RS.getString("A.FirstName"),
                RS.getString("A.LastName"),
                Factory.getInstance().getCountryDAO().get(RS.getInt("A.IdCountry")),
                RS.getBoolean("A.Disabled")
        );
    }
}
