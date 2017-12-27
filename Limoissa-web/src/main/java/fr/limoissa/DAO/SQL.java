/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author Adrien
 */
public class SQL {
    private final String ConnexionString = "jdbc:mysql://localhost:3306/Limoissa_AdrienB?characterEncoding=UTF-8";
    private static HashMap<Role, String[]> Users = new HashMap<>();
    
    public enum Role { All, Select, Insert, Update };
    
    private Connection Con;
    private Role UserRole;
    
    public SQL()
    {
        this(Role.Select);
    }
    public SQL(Role R)
    {        
        if(Users.isEmpty())
        {
            //Utilisateurs de la base de données
            Users.put(Role.All, new String[] {"Admin", "HardPass" });
            Users.put(Role.Select, new String[] {"User", "SimplePass"});
            Users.put(Role.Insert, new String[] {"InsertUser", "SOMETHINGSOMETHING"});
            Users.put(Role.Update, new String[] {"UpdateUser", "PinkFluffyUnicornDancingOnRainbows"});
        }
        
        Con = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {}
        
        UserRole = R;
        Connect();
    }
    
    public Connection Connexion() {
        
        return this.Con;
    }
    
    private void Connect()
    {
        if(this.Con == null)
        {
            try
            {
                String[] U = GetUser(UserRole);
                this.Con = DriverManager.getConnection(ConnexionString, U[0], U[1]);
            }
            catch (SQLException e)
            {
                this.Con = null;
            }
        }
    }
    
    public void Close()
    {
        try
        {
            if(null != Con && !Con.isClosed())
                Con.close();
        }
        catch (SQLException ex)
        {
        }
    }
    
    private String[] GetUser(Role R)
    {
        if(Users.containsKey(R)) return Users.get(R);
        return Users.get(Role.Select);
    }
}
