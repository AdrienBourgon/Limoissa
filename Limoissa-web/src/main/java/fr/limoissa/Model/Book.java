/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Model;

/**
 *
 * @author Adrien
 */
public class Book {
    private int Id, Stock;
    private float Price;
    private String Title, Summary, PublicationDate;
    private Author Author;
    private Category Category;
    
    public Book()
    {
        this.Id = 0;
        this.Title = "";
        this.Summary = "";
        this.PublicationDate = "";
        this.Stock = 0;
        this.Price = 0f;
        this.Author = new Author();
    }
    public Book(String Title, String Summary, String PublicationDate, int Stock, float Price, Author Author, Category Category)
    {
        this();
        setTitle(Title);
        setSummary(Summary);
        setPublicationDate(PublicationDate);
        setStock(Stock);
        setPrice(Price);
        setAuthor(Author);
        setCategory(Category);
    }
    public Book(int Id, String Title, String Summary, String PublicationDate, int Stock, float Price, Author Author, Category Category)
    {
        this(Title, Summary, PublicationDate, Stock, Price, Author, Category);
        this.Id = Id;
    }

    public int getId() { return Id; }
    public void setId(int Id) { this.Id = Id; }

    public int getStock() { return Stock; }
    public void setStock(int Stock) { this.Stock = Stock; }

    public float getPrice() { return Price; }
    public void setPrice(float Price) { this.Price = Price; }

    public String getTitle() { return Title; }
    public void setTitle(String Title) { this.Title = Title; }

    public String getSummary() { return Summary; }
    public void setSummary(String Summary) { this.Summary = Summary; }

    public String getPublicationDate() { return PublicationDate; }
    public String getFormattedDate()
    {
        if(PublicationDate.length() < 8) return "";
        return this.PublicationDate.substring(0,4)+'-'+PublicationDate.substring(4, 6)+'-'+PublicationDate.substring(6,8);
    }
    public void setPublicationDate(String PublicationDate) { this.PublicationDate = PublicationDate.replace("-", ""); } 

    public Author getAuthor() { return Author; }
    public void setAuthor(Author Author) { this.Author = Author; }
    
    public Category getCategory() { return Category; }
    public void setCategory(Category Category) { this.Category = Category; }
    
    
}
