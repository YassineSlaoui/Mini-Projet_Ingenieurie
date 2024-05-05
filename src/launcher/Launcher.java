package launcher;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import guis.AuthGUI;
import guis.MainGUI;
import mimiTheme.Mimi;
import model.Chambre;
import model.Client;
import model.Reservation;
import model.TypeChambre;
import model.User;

@SuppressWarnings("unused")
public class Launcher {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/hotel";
    public static final String USER = "root";
    public static final String PASS = "toor";

    static public ArrayList<User> users = new ArrayList<>();
    static public ArrayList<Client> clients = new ArrayList<>();
    static public ArrayList<Chambre> chambres = new ArrayList<>();
    static public ArrayList<Reservation> reservations = new ArrayList<>();
    static public ArrayList<TypeChambre> typeChambres = new ArrayList<>();

    public Launcher() {
	if (!connectDB())
	    System.err.println("Failed connecting to dataBase!");
	else {
	    System.out.println("Connected to dataBase successfully.");
	    importFromDB();
	    AuthGUI gui = new AuthGUI();
	    gui.setVisible(true);
	}
//	maingui.setVisible(true);
    }

    private Boolean connectDB() {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);) {

	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    private void importFromDB() {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); Statement statement = connection.createStatement();) {
	    ResultSet rs = statement.executeQuery("SELECT * FROM Client");
	    while (rs.next()) {
		clients.add(new Client(rs.getInt("idClient"), rs.getString("nomClient"), rs.getString("numTel"), rs.getString("adresse"), rs.getString("nationalite")));
	    }
	    rs = statement.executeQuery("SELECT * FROM TypeCh");
	    while (rs.next()) {
		typeChambres.add(new TypeChambre(rs.getInt("idType"), rs.getString("typeCh"), rs.getInt("capacite")));
	    }
	    rs = statement.executeQuery("SELECT * FROM Chambre");
	    while (rs.next()) {
		chambres.add(new Chambre(rs.getInt("idChambre"), rs.getInt("Numero"), findTypeChambre(rs.getInt("idTypeCh"))));
	    }
	    rs = statement.executeQuery("SELECT * FROM Reservation");
	    while (rs.next()) {
		reservations.add(new Reservation(rs.getInt("idRes"), rs.getTimestamp("dateDebut"), rs.getTimestamp("dateFin"), findClient(rs.getInt("idClient")), findChambre(rs.getInt("idChambre"))));
	    }
	    rs = statement.executeQuery("SELECT * FROM User");
	    while (rs.next()) {
		users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    static public String etatChambre(Chambre ch) {
	Reservation res = Launcher.chambreDispo(ch, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
	if (res != null)
	    return (res.toString());
	else
	    return "Disponible";
    }

    static public Reservation chambreDispo(Chambre ch, Timestamp deb, Timestamp fin) {
	for (Reservation res : reservations)
	    if (res.getChambre().equals(ch))
		if ((deb.after(res.getDebut()) && deb.before(res.getFin())) || (fin.before(res.getFin()) && fin.after(res.getDebut())))
		    return res;
	return null;
    }

    static public User findUser(int aId) {
	for (Iterator<User> it = users.iterator(); it.hasNext();) {
	    User current = it.next();
	    if (current.getId() == aId)
		return current;
	}
	return null;
    }

    static public Client findClient(int aId) {
	for (Iterator<Client> it = clients.iterator(); it.hasNext();) {
	    Client current = it.next();
	    if (current.getId() == aId)
		return current;
	}
	return null;
    }

    static public Chambre findChambre(int aId) {
	for (Iterator<Chambre> it = chambres.iterator(); it.hasNext();) {
	    Chambre current = it.next();
	    if (current.getId() == aId)
		return current;
	}
	return null;
    }

    static public Reservation findReservation(int aId) {
	for (Iterator<Reservation> it = reservations.iterator(); it.hasNext();) {
	    Reservation current = it.next();
	    if (current.getId() == aId)
		return current;
	}
	return null;
    }

    static public TypeChambre findTypeChambre(int aId) {
	for (Iterator<TypeChambre> it = typeChambres.iterator(); it.hasNext();) {
	    TypeChambre current = it.next();
	    if (current.getId() == aId)
		return current;
	}
	return null;
    }

    static public int addUser(String aNom, String aLogin, String aPwd) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
		PreparedStatement pStatement = connection.prepareStatement("INSERT INTO User(nom, login, pwd) VALUES (?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
	    pStatement.setString(1, aNom);
	    pStatement.setString(2, aLogin);
	    pStatement.setString(3, aPwd);
	    pStatement.executeUpdate();
	    ResultSet rs = pStatement.getGeneratedKeys();
	    if (rs.next()) {
		int autoId = rs.getInt(1);
		users.add(new User(autoId, aNom, aLogin, aPwd));
		return autoId;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return -1;
	}
	return 0;
    }

    static public boolean delUser(int aId) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
		PreparedStatement pStatement = connection.prepareStatement("DELETE FROM User WHERE idUser = ?;")) {
	    User u = findUser(aId);
	    pStatement.setInt(1, aId);
	    pStatement.executeUpdate();
	    users.remove(u);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public boolean updUser(int aId, String aNom, String aLogin, String aPwd) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);) {
	    PreparedStatement pStatement = null;
	    User u = findUser(aId);
	    if (!aPwd.equals(u.getPwd())) {
		pStatement = connection.prepareStatement("UPDATE User SET nom = ?, login = ?, pwd = ? WHERE idUser = ?;");
		pStatement.setString(3, aPwd);
		pStatement.setInt(4, aId);
	    } else {
		pStatement = connection.prepareStatement("UPDATE User SET nom = ?, login = ? WHERE idUser = ?;");
		pStatement.setInt(4, aId);
	    }
	    pStatement.setString(1, aNom);
	    pStatement.setString(2, aLogin);
	    pStatement.executeUpdate();
	    pStatement.close();
	    u.update(aNom, aLogin, aPwd);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public int addClient(String aNom, String aTel, String aAdresse, String aNat) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
		PreparedStatement pStatement = connection.prepareStatement("INSERT INTO Client(nomClient, numTel, adresse, nationalite) VALUES (?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
	    pStatement.setString(1, aNom);
	    pStatement.setString(2, aTel);
	    pStatement.setString(3, aAdresse);
	    pStatement.setString(4, aNat);
	    pStatement.executeUpdate();
	    ResultSet rs = pStatement.getGeneratedKeys();
	    if (rs.next()) {
		int autoId = rs.getInt(1);
		clients.add(new Client(autoId, aNom, aTel, aAdresse, aNat));
		return autoId;
	    }
	} catch (Exception e) {
	    return -1;
	}
	return 0;
    }

    static public boolean delClient(int aId) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("DELETE FROM Client WHERE idClient = ?;")) {
	    Client cl = findClient(aId);
//	    for(Reservation res:reservations) {
//		if(res.getClient().equals(cl)) {
//		    delReservation(res.getId());
//		}
//	    }
	    for (int i = reservations.size() - 1; i >= 0; i--) {// on supprime toutes les reservations
		Reservation res = reservations.get(i);// faites par ce client
		if (res.getClient().equals(cl))
		    delReservation(res.getId());
	    }
	    pStatement.setInt(1, aId);
	    pStatement.executeUpdate();
	    clients.remove(cl);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public boolean updClient(int aId, String aNom, String aTel, String aAdresse, String aNat) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("UPDATE Client SET nomClient = ?, numTel = ?, adresse = ?, nationalite = ? WHERE idClient = ?;")) {
	    pStatement.setString(1, aNom);
	    pStatement.setString(2, aTel);
	    pStatement.setString(3, aAdresse);
	    pStatement.setString(4, aNat);
	    pStatement.setInt(5, aId);
	    pStatement.executeUpdate();
	    findClient(aId).update(aNom, aTel, aAdresse, aNat);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public int addTypeCh(String aType, int aCapacite) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("INSERT INTO TypeCh(typeCh,capacite) VALUES (?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
	    pStatement.setString(1, aType);
	    pStatement.setInt(2, aCapacite);
	    pStatement.executeUpdate();
	    ResultSet rs = pStatement.getGeneratedKeys();
	    if (rs.next()) {
		int autoId = rs.getInt(1);
		typeChambres.add(new TypeChambre(autoId, aType, aCapacite));
		return autoId;
	    }
	} catch (Exception e) {
	    return -1;
	}
	return 0;
    }

    static public boolean delTypeCh(int aId) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("DELETE FROM TypeCh WHERE idType = ?;")) {
	    TypeChambre type = findTypeChambre(aId);
//	    for(Chambre ch:chambres) {
//		if(ch.getType().equals(type)) {
//		    delChambre(ch.getId());
//		}
//	    }
	    for (int i = chambres.size() - 1; i >= 0; i--) {// on supprime toutes les chambres
		Chambre ch = chambres.get(i);// de ce type d'abord
		if (ch.getType().equals(type))
		    delChambre(ch.getId());
	    }
	    pStatement.setInt(1, aId);
	    pStatement.executeUpdate();
	    typeChambres.remove(type);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public boolean updTypeCh(int aId, String aType, int aCapacite) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("UPDATE TypeCh SET typeCh = ?,capacite = ? WHERE idType = ?;")) {
	    pStatement.setString(1, aType);
	    pStatement.setInt(2, aCapacite);
	    pStatement.setInt(3, aId);
	    pStatement.executeUpdate();
	    findTypeChambre(aId).update(aType, aCapacite);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public int addChambre(int aNum, TypeChambre aType) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("INSERT INTO Chambre(Numero, idTypeCh) VALUES (?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
	    pStatement.setInt(1, aNum);
	    pStatement.setInt(2, aType.getId());
	    pStatement.executeUpdate();
	    ResultSet rs = pStatement.getGeneratedKeys();
	    if (rs.next()) {
		int autoId = rs.getInt(1);
		chambres.add(new Chambre(autoId, aNum, aType));
		return autoId;
	    }
	} catch (Exception e) {
	    return -1;
	}
	return 0;
    }

    static public boolean delChambre(int aId) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("DELETE FROM Chambre WHERE idChambre = ?;")) {
	    Chambre ch = findChambre(aId);
//	    for(Reservation res:reservations) {
//		if(res.getChambre().equals(ch)) {
//		    delReservation(res.getId());
//		}
//	    }
	    for (int i = reservations.size() - 1; i >= 0; i--) {// on supprime toutes les reservations
		Reservation res = reservations.get(i);// faites dans cette chambre
		if (res.getChambre().equals(ch))
		    delReservation(res.getId());
	    }
	    pStatement.setInt(1, aId);
	    pStatement.executeUpdate();
	    chambres.remove(ch);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public boolean updChambre(int aId, int aNum, TypeChambre aType) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("UPDATE Chambre SET Numero = ?, idTypeCh = ? WHERE idChambre = ?;")) {
	    pStatement.setInt(1, aNum);
	    pStatement.setInt(2, aType.getId());
	    pStatement.setInt(3, aId);
	    pStatement.executeUpdate();
	    findChambre(aId).update(aNum, aType);
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public int addReservation(Timestamp aDateDeb, Timestamp aDateFin, Client aClient, Chambre aChambre) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
		PreparedStatement pStatement = connection.prepareStatement("INSERT INTO Reservation(dateDebut, dateFin, idClient, idChambre) VALUES (?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
	    pStatement.setTimestamp(1, aDateDeb);
	    pStatement.setTimestamp(2, aDateFin);
	    pStatement.setInt(3, aClient.getId());
	    pStatement.setInt(4, aChambre.getId());
	    pStatement.executeUpdate();
	    ResultSet rs = pStatement.getGeneratedKeys();
	    if (rs.next()) {
		int autoId = rs.getInt(1);
		reservations.add(new Reservation(autoId, aDateDeb, aDateFin, aClient, aChambre));
		return autoId;
	    }
	} catch (Exception e) {
	    return -1;
	}
	return 0;
    }

    static public boolean delReservation(int aId) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("DELETE FROM Reservation WHERE idRes = ?;")) {
	    pStatement.setInt(1, aId);
	    pStatement.executeUpdate();
	    reservations.remove(findReservation(aId));
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    static public boolean updReservation(int aId, Timestamp aDateDeb, Timestamp aDateFin, Client aClient, Chambre aChambre) {
	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS); PreparedStatement pStatement = connection.prepareStatement("UPDATE Reservation SET dateDebut = ?, dateFin = ?, idClient = ?, idChambre = ? WHERE idRes = ?;")) {
	    pStatement.setTimestamp(1, aDateDeb);
	    pStatement.setTimestamp(2, aDateFin);
	    pStatement.setInt(3, aClient.getId());
	    pStatement.setInt(4, aChambre.getId());
	    pStatement.setInt(5, aId);
	    pStatement.executeUpdate();
	    findReservation(aId).update(aDateDeb, aDateFin, aClient, aChambre);
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
	Mimi.setup();
	try {
	    UIManager.setLookAndFeel(new Mimi());
	} catch (Exception e) {
	    System.err.println("Failed to initialize LaF");
	}
	Launcher main = new Launcher();
    }

}
