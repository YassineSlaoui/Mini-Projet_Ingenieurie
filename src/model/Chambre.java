package model;

public class Chambre {

    private int id;
    private int num;
    private TypeChambre type;
    
    public Chambre(int aId, int aNum, TypeChambre aType) {
	this.id=aId;
	this.num=aNum;
	this.type=aType;
    }
    
    public int getId() {
	return this.id;
    }
    
    public int getNum() {
	return this.num;
    }
    
    public TypeChambre getType() {
	return this.type;
    }
    
    public void setNum(int aNum) {
	this.num=aNum;
    }
    
    public void setType(TypeChambre aType) {
	this.type=aType;
    }
    
    public void update(int aNum, TypeChambre aType) {
	this.num=aNum;
	this.type=aType;
    }
    
    @Override
    public String toString() {
        return "Chambre n°"+this.num+" - Type: "+this.type.getType()+" - Cap: "+this.type.getCapacite();
    }
    
}
