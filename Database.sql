CREATE  DATABASE Limoissa_AdrienB;
USE Limoissa_AdrienB;
SET character_set_server = 'UTF8';

DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Author;
DROP TABLE IF EXISTS Country;
DROP TABLE IF EXISTS Category;

CREATE TABLE Role(
	Id INTEGER AUTO_INCREMENT PRIMARY KEY,
	Name VARCHAR(255) NOT NULL,
	CanAdd TinyInt DEFAULT 0,
	CanEdit TinyInt DEFAULT 0,
	CanDelete TinyInt DEFAULT 0
	) ENGINE = INNODB;
CREATE TABLE Country(
	Id INTEGER AUTO_INCREMENT PRIMARY KEY,
	Name VARCHAR(255) NOT NULL
	) ENGINE = INNODB;
CREATE TABLE Category(
	Id INTEGER AUTO_INCREMENT PRIMARY KEY,
	Name VARCHAR(255) NOT NULL,
	IdCategory INTEGER,
	Disabled TINYINT DEFAULT 0,
	FOREIGN KEY (IdCategory) REFERENCES Category(Id)
	) ENGINE = INNODB;
CREATE TABLE Author(
	Id INTEGER AUTO_INCREMENT PRIMARY KEY,
	FirstName VARCHAR(80) NOT NULL,
	LastName VARCHAR(80) NOT NULL,
	IdCountry INTEGER NOT NULL,
	Disabled TINYINT DEFAULT 0,
	FOREIGN KEY (IdCountry) REFERENCES Country(Id)
	) ENGINE = INNODB;
CREATE TABLE Member(
	Id INTEGER AUTO_INCREMENT PRIMARY KEY,
	FirstName VARCHAR(80) NOT NULL,
	LastName VARCHAR(80) NOT NULL,
	Mail VARCHAR(255) NOT NULL,
	Password CHAR(64),
	Salt VARCHAR(20),
	SignUpDate CHAR(12) NOT NULL,
	IdRole INTEGER NOT NULL DEFAULT 2,
	FOREIGN KEY (IdRole) REFERENCES Role(Id)
	) ENGINE = INNODB;
CREATE TABLE Book(
	Id INTEGER AUTO_INCREMENT PRIMARY KEY,
	Title VARCHAR(255) NOT NULL,
	Summary TEXT,
	Price DECIMAL(5,2) NOT NULL DEFAULT 0.0,
	Stock INTEGER NOT NULL DEFAULT 0,
	PublicationDate CHAR(12),
	IdAuthor INTEGER NOT NULL,
	IdCategory INTEGER,
	FOREIGN KEY (IdAuthor) REFERENCES Author(Id),
	FOREIGN KEY (IdCategory) REFERENCES Category(Id)
	) ENGINE = INNODB;
	
	/* Users */
GRANT SELECT, INSERT, UPDATE, DELETE ON Limoissa_AdrienB.* TO Admin@localhost IDENTIFIED BY 'HardPass';
GRANT SELECT ON Limoissa_AdrienB.* TO User@localhost IDENTIFIED BY 'SimplePass';
GRANT INSERT ON Limoissa_AdrienB.* TO InsertUser@localhost IDENTIFIED BY 'SOMETHINGSOMETHING';
GRANT SELECT, UPDATE ON Limoissa_AdrienB.* TO UpdateUser@localhost IDENTIFIED BY 'PinkFluffyUnicornDancingOnRainbows';

	/* Inserts */
INSERT INTO Role(Name, CanAdd, CanEdit, CanDelete) VALUES ('Administrateur', 1, 1, 1), ('Utilisateur', 0, 0, 0);
INSERT INTO Member(FirstName, LastName, Mail, Password, Salt, SignUpDate, IdRole) VALUES('Martin', 'Dupont', 'martin.dupont@limoissa.fr',  '8IXVnRyVg3t1y6IYJNVzYkFPS2fnaCxG8UZi5NDxLVU=', 'h7MSiQ%IJ%', '20171227', 1);

	/* Administrateur
		Mail : martin.dupont@limoissa.fr
		Mot de passe : martindupont
	*/