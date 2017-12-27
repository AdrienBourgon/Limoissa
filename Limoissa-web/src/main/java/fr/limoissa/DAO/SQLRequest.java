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
public class SQLRequest {
    
    public class Select
    {
        public static final String Roles_OB_Name = "SELECT * FROM Role ORDER BY Name;";
        
        public static final String Country_By_Name = "SELECT Id FROM Country WHERE Name LIKE ?;";
        public static final String Countries_OB_Name = "SELECT * FROM Country ORDER BY Name;";
        public static final String Country = "SELECT * FROM Country WHERE Id=?;";
        
        public static final String Member_Salt = "SELECT Salt FROM Member WHERE Mail = ?;";
        public static final String Member_By_Password = "SELECT M.*, R.* FROM Member M, Role R WHERE M.IdRole = R.Id AND Mail = ? AND Password = ?";
        
        private static final String Authors = "SELECT A.*, C.* FROM Author A JOIN Country C ON A.IdCountry = C.Id";
        public static final String Author_By_Name = "SELECT Id FROM Author WHERE FirstName LIKE ? AND LastName LIKE ? AND IdCountry = ?;";
        public static final String Author = Authors + " WHERE A.Id = ?;";
        public static final String Authors_OB_Name =  Authors + " ORDER BY A.LastName, A.FirstName;";
        public static final String Authors_OB_Name_DESC =  Authors + " ORDER BY A.LastName, A.FirstName DESC;";
        
        public static final String Book = "SELECT * FROM Book WHERE Id=?;";
        public static final String Books_Count = "SELECT COUNT(B.Id) as Count_Elements FROM Book B WHERE B.Title LIKE ?;";
        public static final String Books = "SELECT B.*, A.*, C.* FROM Author A, Country C, Book B LEFT JOIN Category Ca ON B.IdCategory = Ca.Id WHERE B.IdAuthor = A.Id AND A.IdCountry = C.Id AND B.Title LIKE ? %s LIMIT ?, ?;";
        
        public static final String Category_By_Id = "SELECT * FROM Category WHERE Id = ?;";  
        public static final String Category_By_Parent = "SELECT * FROM Category WHERE IdCategory = ? ORDER BY Name;";  
        public static final String Category_Without_Parent = "SELECT * FROM Category WHERE IdCategory IS NULL ORDER BY Name;";         
    }
    
    public class Insert
    {
        public static final String Country = "INSERT INTO Country(Name) VALUES(?);";
        
        public static final String Role = "INSERT INTO Role(Name, CanAdd, CanEdit, CanDelete) VALUES(?, ?, ?, ?);";
        
        public static final String Author = "INSERT INTO Author(FirstName, LastName, IdCountry, Disabled) VALUES(?, ?, ?, ?);";
        
        public static final String Book = "INSERT INTO Book(Title, Summary, Price, Stock, PublicationDate, IdAuthor, IdCategory) VALUES(?, ?, ?, ?, ?, ?, ?);";
        
        public static final String Member = "INSERT INTO Member(FirstName, LastName, Mail, Password, Salt, SignupDate) VALUES(?, ?, ?, ?, ?, ?);";
        
        public static final String Category = "INSERT INTO Category(Name, IdCategory, Disabled) VALUES (?, ?, ?);";
    }
    
    public class Update
    {
        public static final String Category = "UPDATE Category SET Name=?, IdCategory=?, Disabled=? WHERE Id=?;";
        
        public static final String Country = "UPDATE Country SET Name=? WHERE Id=?;";
        
        public static final String Author = "UPDATE Author SET FirstName=?, LastName=?, IdCountry=?, Disabled=? WHERE Id=?;";
        
        public static final String Book = "UPDATE Book SET Title=?, Summary=?, Price=?, Stock=?, PublicationDate=?, IdAuthor=?, IdCategory=? WHERE Id=?;";
    }
    
    public class Delete
    {
        public static final String Book = "DELETE FROM Book WHERE Id = ?;";
    }
}
