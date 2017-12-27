/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.DAO;

import fr.limoissa.Model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Adrien
 */
public class Search {
    
    public static SearchResults SearchBooks(String Title, HashMap<String, Boolean> OrderBy, int Page)
    {
        final int ITEMS_PER_PAGE = 10;
        
        Collection<fr.limoissa.Model.Book> Books = new ArrayList<>();
        int Pages = 0;
        
        SQL S = new SQL();
        
        try
        {
            /* Compte des éléments pour la pagination */
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Books_Count);
            St.setString(1, "%"+Title+"%");
            
            ResultSet RS = St.executeQuery();            
            if(null != RS && RS.next())
            {
                Pages = RS.getInt("Count_Elements");
                Pages = (Pages / ITEMS_PER_PAGE) + (Pages % ITEMS_PER_PAGE > 0 ? 1 : 0);
                RS.close();
                St.close();
            }
            
            /* Préparation de la chaîne pour le ORDER BY */
            String Order = OrderBy.isEmpty() ? "" : "ORDER BY";
            for(String Key: OrderBy.keySet())
                Order += (Order.length() == 8 ? "" : ",") + " " + Key + " " + (OrderBy.get(Key) ? "ASC" : "DESC");
            
            return new SearchResults(Pages, new Book().getAll("%" + Title + "%", Order, (Page - 1) * ITEMS_PER_PAGE, ITEMS_PER_PAGE));
        }
        catch (SQLException ex)
        {
            String Str = ex.getMessage();
        }
        finally { S.Close(); }
        
        return new SearchResults();
    }
}
