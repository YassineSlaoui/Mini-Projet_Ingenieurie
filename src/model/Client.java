package model;

public class Client {
    
    private int id;
    private String nom;
    private String tel;
    private String adresse;
    private String nat;
    
    public Client(int aId, String aNom, String aTel, String aAdresse, String aNat) {
	this.id=aId;
	this.nom=aNom;
	this.tel=aTel;
	this.adresse=aAdresse;
	this.nat=aNat;
    }
    
    public void setNom(String aNom) {
	this.nom=aNom;
    }
    
    public void setTel(String aTel) {
	this.tel=aTel;
    }
    
    public void setAdresse(String aAdresse) {
	this.adresse=aAdresse;
    }
    
    public void setNat(String aNat) {
	this.nat=aNat;
    }
    
    public int getId() {
	return id;
    }
    
    public String getNom() {
	return nom;
    }
    
    public String getTel() {
	return tel;
    }
    
    public String getAdresse() {
	return adresse;
    }
    
    public String getNat() {
	return nat;
    }
    
    public void update(String aNom, String aTel, String aAdresse, String aNat) {
	setNom(aNom);
	setTel(aTel);
	setAdresse(aAdresse);
	setNat(aNat);
    }
    
    @Override
    public String toString() {
        return this.nom;
    }
    
}
