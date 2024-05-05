CREATE DATABASE Hotel;
USE HOTEL;

CREATE TABLE Client(
	idClient INT NOT NULL AUTO_INCREMENT,
    nomClient VARCHAR(45) NOT NULL,
    numTel VARCHAR(8),
    adresse VARCHAR(45),
    nationalite VARCHAR(45),
    PRIMARY KEY (idClient));
    
CREATE TABLE typeCh(
	idType INT NOT NULL AUTO_INCREMENT,
    typeCh VARCHAR(45) NOT NULL,
    capacite INT,
    PRIMARY KEY (idType));
    
CREATE TABLE Chambre(
	idChambre INT NOT NULL AUTO_INCREMENT,
    numero INT UNIQUE NOT NULL,
    idTypeCh INT,
    FOREIGN KEY (idTypeCh) REFERENCES typeCh(idType),
    PRIMARY KEY (idChambre));
    
CREATE TABLE Reservation(
	idRes INT NOT NULL AUTO_INCREMENT,
    dateDebut DATETIME NOT NULL,
    dateFin DATETIME NOT NULL,
    idClient INT,
    idChambre INT,
    CONSTRAINT CHECK (dateDebut<dateFin),
    FOREIGN KEY (idClient) REFERENCES Client(idClient),
    FOREIGN KEY (idChambre) REFERENCES Chambre(idChambre),
    PRIMARY KEY (idRes));
    
CREATE TABLE User (
  idUser INT NOT NULL AUTO_INCREMENT,
  nom VARCHAR(45) NOT NULL,
  login VARCHAR(45) UNIQUE NOT NULL,
  pwd VARCHAR(45) NOT NULL,
  PRIMARY KEY (idUsers));