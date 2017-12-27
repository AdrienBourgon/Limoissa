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
import java.util.stream.Collectors;

/**
 *
 * @author Adrien
 */
public class Country implements fr.limoissa.Interface.DAO<fr.limoissa.Model.Country> {
    private static final HashMap<Integer, fr.limoissa.Model.Country> Countries = new LinkedHashMap<>();
    
    @Override
    public Collection<fr.limoissa.Model.Country> getAll()
    {        
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Countries_OB_Name);
            
            // Exécution
            ResultSet RS = St.executeQuery();
            
            if(null != RS)
            {
                while(RS.next()) // On lit tant qu'il y a des résultats
                {
                    // On ajoute à la map statique de la class
                    if(!Countries.containsKey(RS.getInt("Id")))
                        Countries.put(RS.getInt("Id"), BuildCountry(RS));
                }

                RS.close();
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        // On retourne tous les éléments de la map
        return Countries.values().stream().sorted((c, d) -> c.getName().compareToIgnoreCase(d.getName())).collect(Collectors.toCollection(ArrayList::new));
    }
    
    @Override
    public fr.limoissa.Model.Country get(int Id)
    {
        if(Countries.containsKey(Id))
            return Countries.get(Id);
        
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Country);
            St.setInt(1, Id);
            
            ResultSet RS = St.executeQuery();
            
            if(null != RS)
            if(RS.next())
            {
                Countries.put(Id, BuildCountry(RS));
                return Countries.get(Id);
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return null;
    }
    
    @Override
    public Boolean add(fr.limoissa.Model.Country C)
    {
        SQL S = new SQL(SQL.Role.Insert); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Insert.Country, Statement.RETURN_GENERATED_KEYS);
            
            St.setString(1, C.getName());
            
            if(St.executeUpdate() == 1)
            {
                // Récupération de l'Id inséré
                ResultSet RS = St.getGeneratedKeys();
                
                if(null != RS && RS.next())
                {
                    C.setId(RS.getInt(1));
                    Countries.put(C.getId(), C);
                    return true;
                }
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return false;
    }
    
    @Override
    public Boolean edit(fr.limoissa.Model.Country C)
    {
        if(!Countries.containsKey(C.getId())) return false;
        
        SQL S = new SQL(SQL.Role.Update); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Update.Country);
            
            St.setString(1, C.getName());
            St.setInt(2, C.getId());
            
            if(St.executeUpdate() == 1)
            {
                Countries.get(C.getId()).setName(C.getName());
                return true;
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return false;
    }
    
    public int CreateIfNotExists(fr.limoissa.Model.Country C)
    {
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Country_By_Name);
            
            String Like = C.getName();
            St.setString(1, Like);
            
            ResultSet RS = St.executeQuery();
            
            if(null != RS)
            {
                // Le pays existe dans la base
                if(RS.next())
                    return RS.getInt("Id");
                // Le pays n'existe pas
                else
                {
                    // Formatage du nom du pays (extraction depuis fichier xml
                    String CountryToInsert = C.getName().replace("_", " ").toLowerCase();
                    CountryToInsert = CountryToInsert.substring(0, 1).toUpperCase() + CountryToInsert.substring(1);
                    C.setName(CountryToInsert);
                    
                    if(add(C))
                        return C.getId();
                }
            }
        }
        catch (SQLException | NullPointerException e) {}
        finally { S.Close(); }
        
        return 0;
    }
    
    // Construction d'un objet Country à partir d'un ResultSet
    private static fr.limoissa.Model.Country BuildCountry(ResultSet RS) throws SQLException
    {
        if(null == RS) return null;
        
        return new fr.limoissa.Model.Country(
                RS.getInt("Id"),
                RS.getString("Name"));
    }
}
