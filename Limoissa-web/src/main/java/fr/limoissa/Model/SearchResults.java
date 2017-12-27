/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Model;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Adrien
 */
public class SearchResults {
    
    private int Pages;
    private Collection<Book> Books;
    
    public SearchResults()
    {
        Pages = 0;
        Books = new ArrayList<>();
    }
    public SearchResults(int Pages, Collection<Book> Books)
    {
        setPages(Pages);
        setBooks(Books);
    }
    
    public int getPages() { return this.Pages; }
    public void setPages(int Pages) { this.Pages = Pages; }
    
    public Collection<Book> getBooks() { return this.Books; }
    public void setBooks(Collection<Book> Books) { this.Books = Books; }
    
}
