package guis;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import org.jdesktop.swingx.*;
import launcher.Launcher;
import model.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.formdev.flatlaf.ui.FlatLineBorder;

@SuppressWarnings({ "unused", "serial" })
public class ClientsStatsGUI extends JFrame {

    public ClientsStatsGUI() {
	setTitle("Statistiques des Clients");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel contentPane = new JPanel();
	getContentPane().add(contentPane);
	contentPane.setBorder(new EmptyBorder(new Insets(15, 40, 15, 40)));
	contentPane.setLayout(new BorderLayout());

	JPanel northPanel = new JPanel();
	contentPane.add(northPanel, BorderLayout.NORTH);
	JLabel headerLabel = new JLabel("Classement des Clients:");
	headerLabel.setFont(new Font("Times new roman", Font.BOLD, 24));
	northPanel.add(headerLabel);

	JPanel centerPanel = new JPanel();
	contentPane.add(centerPanel, BorderLayout.CENTER);
	String columnNames[] = { "Id", "Nom", "Tel","Adresse","Nationalité","Classement" };
	DefaultTableModel tModel = new DefaultTableModel(columnNames, 0);

	JXTable table = new JXTable(tModel) {

	    @Override
	    public boolean isCellEditable(int row, int column) {
		return false;
	    }
	   
	};

	try (Connection connection = DriverManager.getConnection(Launcher.DB_URL, Launcher.USER, Launcher.PASS); 
		Statement statement = connection.createStatement();) {
	    ResultSet rs = statement.executeQuery("SELECT cl.idClient, sum((r.dateFin - r.dateDebut)) AS Periode\r\n"
                                    	    	+ "FROM Reservation r, Client cl\r\n"
                                    	    	+ "WHERE r.idClient = cl.idClient\r\n"
                                    	    	+ "AND UPPER(cl.nomClient) NOT LIKE '%HOTEL%'\r\n"
                                    	    	+ "GROUP BY cl.idClient\r\n"
                                    	    	+ "ORDER BY periode DESC;");
	    int d=0;
	    while (rs.next()) {
		Client cl=Launcher.findClient(rs.getInt(1));
		tModel.addRow(new Object[] { cl.getId(),cl.getNom(),cl.getTel(),cl.getAdresse(),cl.getNat(), ++d});
	    }
	}catch (Exception e) {
	    e.printStackTrace();
	}
	
	table.getTableHeader().setReorderingAllowed(false);
	table.packAll();

	JScrollPane scrollPane = new JScrollPane(table);
	table.setFillsViewportHeight(true);
	centerPanel.add(scrollPane);

	pack();
	contentPane.grabFocus();
	setResizable(false);
	setLocationRelativeTo(null);
    }
}
