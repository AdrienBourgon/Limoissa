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
public class Category implements fr.limoissa.Interface.DAO<fr.limoissa.Model.Category> {
    
    private static final HashMap<Integer, fr.limoissa.Model.Category> Categories = new LinkedHashMap<>();
    
    @Override
    public Collection<fr.limoissa.Model.Category> getAll() { return getAll(-1); }
    private Collection<fr.limoissa.Model.Category> getAll(int Parent)
    {
        
        Collection<fr.limoissa.Model.Category> Cats = new ArrayList<>();
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St;
            
            if(Parent <= 0) // Si on cherche les catégories sans parent
                St = S.Connexion().prepareStatement(SQLRequest.Select.Category_Without_Parent);
            else
            {
                St = S.Connexion().prepareStatement(SQLRequest.Select.Category_By_Parent);
                St.setInt(1, Parent);
            }
            
            ResultSet RS = St.executeQuery();
            
            while(RS.next()) // On lit tant qu'il y a des résultats
            {
                fr.limoissa.Model.Category C = new fr.limoissa.Model.Category(
                    RS.getInt("Id"),
                    RS.getString("Name"),
                    RS.getBoolean("Disabled"));
                
                Cats.add(C);
                
                // Ajout des éléments à la map statique de la classe
                if(!Categories.containsKey(C.getId()))
                    Categories.put(C.getId(), C);
                
                // On ajoute à la catégorie son parent (s'il y en a un)
                try { C.setParent(get(Parent)); }
                catch (NullPointerException e) { }
            }
            
            RS.close();
            St.close();
            
            // Récupération des enfants des nouveaux éléments ajoutés dans la map
            for(fr.limoissa.Model.Category Cat: Cats)
                Cat.setChildren(getAll(Cat.getId()));
        }
        catch (SQLException | NullPointerException ex) { }
        finally { S.Close(); }

        return Cats;
    }
    
    @Override
    public fr.limoissa.Model.Category get(int Id)
    {
        if(Id <= 0) return null;
        if(Categories.containsKey(Id)) return Categories.get(Id);
        
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Category_By_Id);
            St.setInt(1, Id);
            
            ResultSet RS = St.executeQuery();
            
            if(RS != null) // S'il y a un résultat
            {
                if(RS.next())
                {
                    fr.limoissa.Model.Category C = new fr.limoissa.Model.Category(
                        Id,
                        RS.getString("Name"),
                        RS.getBoolean("Disabled"));
                    C.setParent(get(RS.getInt("IdCategory")));
                    
                    RS.close();
                    
                    return C;
                }
                RS.close();
            }
        }
        catch (SQLException | NullPointerException e) { }
        finally { S.Close(); }
        
        return null;
    }
    
    @Override
    public Boolean add(fr.limoissa.Model.Category C)
    {
        SQL S = new SQL(SQL.Role.Insert); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête 
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Insert.Category, Statement.RETURN_GENERATED_KEYS);           
            St.setString(1, C.getName());
            
            //Si le Parent doit être null
            if(C.getParent().getId() == -1) St.setNull(2, java.sql.Types.INTEGER);
            else St.setInt(2, C.getParent().getId());
            
            St.setBoolean(3, C.getDisabled());
            
            Boolean Result = St.executeUpdate() == 1;
            
            /* Mise à jour de la HashMap */
            if(Result)
            {
                // Récupération de l'Id inséré
                try (ResultSet GK = St.getGeneratedKeys())
                {
                    if(null != GK && GK.next())
                    {
                        fr.limoissa.Model.Category Cat = get(GK.getInt(1));
                        
                        if(null != Cat)
                            Categories.put(Cat.getId(), Cat);
                    }
                }
            }
            
            return Result;
        }
        catch (SQLException | NullPointerException e)
        {
            String Str = e.getMessage();
        }
        finally { S.Close(); }
        
        return false;
    }
    
    @Override
    public Boolean edit(fr.limoissa.Model.Category C)
    {
        SQL S = new SQL(SQL.Role.Update); // Récupération de la connexion sql
        
        try
        {
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Update.Category);
            St.setString(1, C.getName());
            if(C.getParent().getId() <= 0) St.setNull(2, java.sql.Types.INTEGER);
            else St.setInt(2, C.getParent().getId());
            St.setBoolean(3, C.getDisabled());
            St.setInt(4, C.getId());
            
            Boolean Result = St.executeUpdate() == 1;
            
            /* Mise à jour de la HashMap */
            if(Categories.containsKey(C.getId()))
            {
                Categories.get(C.getId()).setName(C.getName());
                Categories.get(C.getId()).setParent(get(C.getParent().getId()));
            }
            else
                Categories.put(C.getId(), get(C.getId()));
            
            St.close();
            return Result;
        }
        catch (SQLException | NullPointerException e)
        {
            String Str = e.getMessage();
        }
        finally { S.Close(); }
        return false;
    }
}
