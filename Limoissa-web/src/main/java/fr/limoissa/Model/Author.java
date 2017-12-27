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
public class Author {
    private int Id;
    private String FirstName, LastName;
    private Country Country;
    private Boolean Disabled;
    
    public Author()
    {
        this.Id = 0;
        this.FirstName = "";
        this.LastName = "";
        this.Country = null;
        this.Disabled = false;
    }
    public Author(int Id) { this(); this.Id = Id; }
    public Author(String FirstName, String LastName, Country Country, Boolean Disabled)
    {
        this();
        setFirstName(FirstName);
        setLastName(LastName);
        setCountry(Country);
        setDisabled(Disabled);
    }
    public Author(int Id, String FirstName, String LastName, Country Country, Boolean Disabled)
    {
        this(FirstName, LastName, Country, Disabled);
        this.Id = Id;
    }
    
    public int getId() { return Id; }
    public void setId(int Id) { this.Id = Id; }

    public String getFirstName() { return FirstName; }
    public void setFirstName(String FirstName) { this.FirstName = FirstName; }

    public String getLastName() { return LastName; }
    public void setLastName(String LastName) { this.LastName = LastName;}

    public Country getCountry() { return Country; }
    public void setCountry(Country Country) { this.Country = Country;}  
    
    public Boolean getDisabled() { return Disabled; }
    public void setDisabled(Boolean Disabled) { this.Disabled = Disabled;}
}
