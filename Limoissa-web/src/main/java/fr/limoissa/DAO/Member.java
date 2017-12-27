/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.DAO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

/**
 *
 * @author Adrien
 */
public class Member {
    
    //Fonction de Hashage pour le mot de passe utilisateur
    private static String Hash(String Password, String Salt)
    {
        try
        {
            MessageDigest MD = MessageDigest.getInstance("SHA-256");
            byte[] EncodedString = MD.digest((Password + Salt).getBytes(StandardCharsets.UTF_8));
            String Hash = Base64.getEncoder().encodeToString(EncodedString);
            return Hash;
        }
        catch (NoSuchAlgorithmException e) { }
        return "";
    }
    
    public static fr.limoissa.Model.Member Signin(String Mail, String Password) throws NullPointerException
    {
        SQL S = new SQL(); // Récupération de la connexion sql
        
        try
        {
            // Récupération du Sel pour le hash du mot de passe
            // Préparation de la requête
            PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Select.Member_Salt);            
            St.setString(1, Mail);
            
            ResultSet RS = St.executeQuery();            
            if(RS.next())
            {
                String Salt = RS.getString("Salt");
                String Hash = Hash(Password, Salt);
                
                // On regarde s'il existe un membre avec le mail et hash correspondants
                St = S.Connexion().prepareStatement(SQLRequest.Select.Member_By_Password);
                St.setString(1, Mail);
                St.setString(2, Hash);
                
                RS.close();
                
                RS = St.executeQuery();
                
                if(RS.next()) //S'il y a un résultat, on renvoie le membre
                {
                    fr.limoissa.Model.Member M = new fr.limoissa.Model.Member(
                        RS.getInt("M.Id"),
                        RS.getString("M.FirstName"),
                        RS.getString("M.LastName"),
                        Mail,
                        Hash,
                        Salt,
                        RS.getString("M.SignupDate"),
                        new fr.limoissa.Model.Role(
                            RS.getInt("M.IdRole"),
                            RS.getString("R.Name"),
                            RS.getBoolean("R.CanAdd"),
                            RS.getBoolean("R.CanEdit"),
                            RS.getBoolean("R.CanDelete"))
                    );
                    
                    return M;
                }
            }
            
        }
        catch (SQLException e) { }
        finally { S.Close(); }
        return null;
    }
    
    public static Boolean Signup(String FirstName, String LastName, String Mail, String Password)
    {
        // On vérifie la présence de tous les éléments
        Boolean OK = !FirstName.isEmpty() && !LastName.isEmpty() && !Mail.isEmpty() && !Password.isEmpty();
        
        if(OK)
        {
            SQL S = new SQL(SQL.Role.Insert); // Récupération de la connexion sql
            
            try
            {
                // Préparation de la requête
                PreparedStatement St = S.Connexion().prepareStatement(SQLRequest.Insert.Member);
                
                // Génération du sel pour le hash
                String Salt = GenerateSalt(8, 12);
                
                // Génération du hash
                String Hash = Hash(Password, Salt);
                
                //Remplissage de la requête
                St.setString(1, FirstName);
                St.setString(2, LastName);
                St.setString(3, Mail);
                St.setString(4, Hash);
                St.setString(5, Salt);
                
                //Date d'inscription
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
                ZonedDateTime dt = ZonedDateTime.now();
                St.setString(6, dt.format(dtf));
                
                // Ajout dans la base de données et renvoie de la valeur
                return St.executeUpdate() == 1;                
            }
            catch (SQLException | NullPointerException ex) { }
            finally { S.Close(); }
        }
        
        return OK;
    }
    
    //Génération d'un Sel aléatoire entre Min et Max caractères
    private static String GenerateSalt(int Min, int Max)
    {
        String Characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890+-*/*$!;,:&(-_)[]{}=%";
        
        Random R = new Random();
        int N = R.nextInt(Max - Min + 1) + Min;
        
        String Salt = "";
        for(; N > 0; --N)
            Salt += Characters.charAt(R.nextInt(Characters.length()));
        
        return Salt;
    }
}
