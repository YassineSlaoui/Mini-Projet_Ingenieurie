package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private int id;
    private String nom;
    private String login;
    private String pwd;

    public User(int aId, String aNom, String aLogin, String aPwd) {
	this.id = aId;
	this.nom = aNom;
	this.login = aLogin;
	this.pwd = cryptPass(aPwd);
    }

    public int getId() {
	return this.id;
    }
    
    public String getNom() {
	return this.nom;
    }
    
    public void setNom(String aNom) {
	this.nom=aNom;
    }
    
    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPwd() {
	return pwd;
    }

    public void setPwd(String aPwd) {
	this.pwd = cryptPass(aPwd);
    }

    public Boolean seConnecter(String login, String pwd) {
	return (login.equals(this.login) && cryptPass(pwd).equals(this.pwd));
    }

    private static String cryptPass(String aPwd) {
	String Crypted = "";
	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(aPwd.getBytes());

	    byte byteData[] = md.digest();

	    // convertir le tableau de bits en format hexadecimal
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < byteData.length; i++) {
		sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    Crypted = sb.toString();
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	return Crypted;
    }
    
    public void update(String aNom, String aLogin, String aPwd) {
	this.nom=aNom;
	this.login=aLogin;
	if(!pwd.equals(aPwd))
	    setPwd(aPwd);
    }

}
