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
public class Country {
    private int Id;
    private String Name;
    
    public Country()
    {
        this.Id = 0;
        this.Name = "";
    }
    public Country(int Id) { this(); setId(Id); }
    public Country(String Name) { this(); setName(Name); }
    public Country(int Id, String Name) { this(Id); setName(Name); }
    

    public int getId() { return Id; }
    public void setId(int Id) { this.Id = Id; }

    public String getName() { return Name; }
    public void setName(String Name) { this.Name = Name; }   
}
