/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.DAO;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Adrien
 */
public class XML {
        
    public static int ParseXML(InputStream IS)
    {
        int Insertions = 0;        
        
        Factory Factory = fr.limoissa.DAO.Factory.getInstance();
        
        try {
            DocumentBuilder DB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document Doc = DB.parse(IS);
            
            /* Parcours des livres */
            NodeList Books = Doc.getDocumentElement().getChildNodes();
            for(int i = 0; i < Books.getLength(); ++i)
            {
                Node Book = Books.item(i);
                
                // Titre
                Node NTitle = Book.getFirstChild();
                String Title = NTitle.getTextContent();
                
                // Auteur
                Node NAuthor = NTitle.getNextSibling();
                int Author = CheckAuthor(NAuthor);
                
                // Résumé
                Node NSummary = NAuthor.getNextSibling();
                String Summary = NSummary.getTextContent();
                
                // Date de publication
                Node NPublicationDate = NSummary.getNextSibling().getNextSibling();
                String PublicationDate = NPublicationDate.getTextContent().replace("-", "");
                
                // Stock
                int Stock = 0;
                
                // Prix
                Node NPrice = NPublicationDate.getNextSibling();
                float Price = 0f;
                
                try { Price = Float.parseFloat(NPrice.getTextContent()); }
                catch (NumberFormatException e) {}
                
                /* Insertion du livre dans la base de données */
                /* Test des valeurs */
                if(!Title.isEmpty() && Author > 0)
                {
                    if(Factory.getBookDAO().add(new fr.limoissa.Model.Book(
                            Title, Summary, PublicationDate, Stock, Price, Factory.getAuthorDAO().get(Author), Factory.getCategoryDAO().get(-1))))
                        ++Insertions;
                }
            }
        }
        catch (SAXException | IOException | ParserConfigurationException ex)
        {
        }
        
        
        return Insertions;
    }
    
    private static int CheckAuthor(Node A)
    {
        Node NName = A.getFirstChild();
        Node NCountry = NName.getNextSibling().getNextSibling();
        
        String FirstName = "", LastName = "", Name = NName.getTextContent();
        
        if(Name.length() > 0)
        {
            FirstName = Name.split(" ")[0];
            if(Name.length() > FirstName.length())
                LastName = Name.substring(FirstName.length() + 1);
        }
        
        int Country = CheckCountry(NCountry.getTextContent());
        
        return Factory.getInstance().getAuthorDAO().CreateIfNotExists(new fr.limoissa.Model.Author(
            FirstName, LastName, new fr.limoissa.Model.Country(Country), false));
    }
    
    private static int CheckCountry(String C)
    {
        return Factory.getInstance().getCountryDAO().CreateIfNotExists(new fr.limoissa.Model.Country(C));
    }
}
