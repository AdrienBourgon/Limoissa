/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.DAO;

/**
 *
 * @author Adrien
 */
public class Factory {
    private static Factory Instance;
    
    private final Book BookDAO;
    private final Author AuthorDAO;
    private final Country CountryDAO;
    private final Category CategoryDAO;
    
    private Factory()
    {
        BookDAO = new Book();
        AuthorDAO = new Author();
        CountryDAO = new Country();
        CategoryDAO = new Category();
    }

    public static Factory getInstance() {
        if(null == Instance)
        {
            Instance = new Factory();
        }
        return Instance;
    }

    public Book getBookDAO() { return BookDAO; }
    public Author getAuthorDAO() { return AuthorDAO; }
    public Country getCountryDAO() { return CountryDAO; }
    public Category getCategoryDAO() { return CategoryDAO; }    
}
