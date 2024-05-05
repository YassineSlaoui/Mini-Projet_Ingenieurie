package model;

import java.sql.*;

public class Reservation {
    private int id;
    private Timestamp dateDeb;
    private Timestamp dateFin;
    private Client client;
    private Chambre chambre;
    
    public Reservation(int aId, Timestamp aDateDeb,Timestamp aDateFin,Client aClient,Chambre aChambre) {
	this.id=aId;
	this.dateDeb=aDateDeb;
	this.dateFin=aDateFin;
	this.client=aClient;
	this.chambre=aChambre;
    }
    
    public void setDebut(Timestamp aDateDeb) {
	this.dateDeb=aDateDeb;
    }
    
    public void setFin(Timestamp aDateFin) {
	this.dateFin=aDateFin;
    }
    
    public void setClient(Client aClient) {
	this.client=aClient;
    }
    
    public void setChambre(Chambre aChambre) {
	this.chambre=aChambre;
    }
    
    public int getId() {
	return this.id;
    }
    
    public Timestamp getDebut() {
	return this.dateDeb;
    }
    
    public Timestamp getFin() {
	return this.dateFin;
    }
    
    public Client getClient() {
	return this.client;
    }
    
    public Chambre getChambre() {
	return this.chambre;
    }
    
    public void update(Timestamp aDateDeb,Timestamp aDateFin,Client aClient,Chambre aChambre) {
	this.dateDeb=aDateDeb;
	this.dateFin=aDateFin;
	this.client=aClient;
	this.chambre=aChambre;
    }
    
    @Override
    public String toString() {
	if(client.getNom().toUpperCase().contains("HOTEL") && client.getNat().equals("")&&client.getTel().equals("")&&client.getAdresse().equals(""))
	    return "Reservée pour Maintenance, Renovation ou Autre";
        return "Reservée par: "+this.client+", IdRes = "+this.id;
    }
    
}
