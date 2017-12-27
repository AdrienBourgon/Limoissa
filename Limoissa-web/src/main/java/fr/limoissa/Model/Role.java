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
public class Role {
    
    private int Id;
    private String Name;
    private Boolean CanAdd, CanEdit, CanDelete;
    
    public Role()
    {
        Id = 0;
        Name = "";
        CanAdd = false;
        CanEdit = false;
        CanDelete = false;
    }
    public Role(int Id, String Name, Boolean CanAdd, Boolean CanEdit, Boolean CanDelete)
    {
        this();
        this.Id = Id;
        this.Name = Name;
        this.CanAdd = CanAdd;
        this.CanEdit = CanEdit;
        this.CanDelete = CanDelete;
    }       

    public int getId() { return Id; }
    public void setId(int Id) { this.Id = Id; }

    public String getName() { return Name; }
    public void setName(String Name) { this.Name = Name; }

    public Boolean getCanAdd() { return CanAdd; }
    public void setCanAdd(Boolean CanAdd) { this.CanAdd = CanAdd;  }

    public Boolean getCanEdit() { return CanEdit; }
    public void setCanEdit(Boolean CanEdit) { this.CanEdit = CanEdit;  }

    public Boolean getCanDelete() { return CanDelete; }
    public void setCanDelete(Boolean CanDelete) { this.CanDelete = CanDelete;  }
}
