/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.limoissa.Model;

import java.io.Serializable;

/**
 *
 * @author Adrien
 */
public class Member implements Serializable {
    private int Id;
    private String FirstName, LastName, Mail, Password, Salt, SignupDate;
    private Role Role;
    
    public Member()
    {
        this.Id = 0;
        this.FirstName = "";
        this.LastName = "";
        this.Mail = "";
        this.Password = "";
        this.Salt = "";
        this.SignupDate = "";
        this.Role = new Role();
    }
    public Member(String FirstName, String LastName, String Mail, String Password)
    {
        this();
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Mail = Mail;
        this.Password = Password;
    }
    public Member(int Id, String FirstName, String LastName, String Mail, String Password, String Salt, String SignupDate, Role Role)
    {
        this(FirstName, LastName, Mail, Password);
        this.Id = Id;
        setSalt(Salt);
        setSignupDate(SignupDate);
        setRole(Role);
    }

    public int getId() { return Id; }

    public String getFirstName() { return FirstName; }
    public void setFirstName(String FirstName) { this.FirstName = FirstName; }

    public String getLastName() { return LastName; }
    public void setLastName(String LastName) { this.LastName = LastName; }

    public String getMail() { return Mail; }
    public void setMail(String Mail) { this.Mail = Mail; }

    public String getPassword() {  return Password; }
    public void setPassword(String Password) { this.Password = Password; }

    public String getSalt() { return Salt; }
    public void setSalt(String Salt) { this.Salt = Salt; }

    public String getSignupDate() { return SignupDate; }
    public void setSignupDate(String SignupDate) { this.SignupDate = SignupDate; }

    public Role getRole() { return Role;  }
    public void setRole(Role Role) { this.Role = Role; }
    
    public Boolean IsAdmin() { return Role.getCanAdd() || Role.getCanEdit() || Role.getCanDelete(); }
}
