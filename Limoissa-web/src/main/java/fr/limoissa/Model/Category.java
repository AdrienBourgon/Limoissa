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
public class Category {
    private int Id;
    private String Name;
    private Boolean Disabled;
    private Collection<Category> Children;
    private Category Parent;
    
    public Category()
    {
        this.Id = 0;
        this.Name = "";
        this.Disabled = false;
        this.Children = new ArrayList<>();
        Parent = null;
    }
    public Category(int Id) { this(); this.Id = Id; }
    public Category(String Name, Boolean Disabled)
    {
        this();
        setName(Name);
        setDisabled(Disabled);
    }
    public Category(int Id, String Name, Boolean Disabled)
    {
        this(Name, Disabled);
        this.Id = Id;
    }

    public int getId() { return Id; }

    public String getName() { return Name; }
    public void setName(String Name) { this.Name = Name; }
    
    public Boolean getDisabled() { return Disabled; }
    public void setDisabled(Boolean Disabled) { this.Disabled = Disabled; }

    public Collection<Category> getChildren() { return this.Children; }
    public void setChildren(Collection<Category> Children) { this.Children = Children; Children.forEach(c->c.setParent(this));}
    
    public Category getParent() { return this.Parent; }
    public void setParent(Category Parent) { this.Parent = Parent; }
}
