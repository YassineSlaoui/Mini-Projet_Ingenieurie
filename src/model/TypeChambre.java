package model;

public class TypeChambre {

    private int id;
    private String type;
    private int capacite;

    public TypeChambre(int aId, String aType, int aCapacite) {
	this.id = aId;
	this.type = aType;
	this.capacite = aCapacite;
    }

    public int getId() {
	return this.id;
    }

    public String getType() {
	return this.type;
    }

    public int getCapacite() {
	return this.capacite;
    }

    public void setType(String aType) {
	this.type = aType;
    }

    public void setCapacite(int aCapacite) {
	this.capacite = aCapacite;
    }

    public void update(String aType, int aCapcite) {
	this.type = aType;
	this.capacite = aCapcite;
    }
    
    @Override
    public String toString() {
        return this.type+" - "+this.capacite;
    }

}
