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
import com.formdev.flatlaf.ui.FlatLineBorder;

@SuppressWarnings({ "unused", "serial" })
public class ClientsGUI extends JFrame {

    public ClientsGUI() {
	setTitle("Gestion des Clients");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel contentPane = new JPanel();
	getContentPane().add(contentPane);
	contentPane.setBorder(new EmptyBorder(new Insets(15, 40, 15, 40)));
	contentPane.setLayout(new BorderLayout());

	JPanel northPanel = new JPanel(new GridLayout(3,1));
	contentPane.add(northPanel, BorderLayout.NORTH);
	JLabel headerLabel = new JLabel("Clients de l'Hotel:");
	headerLabel.setFont(new Font("Times new roman", Font.BOLD, 24));
	headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
	JLabel noteLabel1=new JLabel("Les clients avec noms contenant le mot clef \"hotel\" ne seront pas");
	JLabel noteLabel2=new JLabel("comptés clients, mais staff de l'hotel.");
	noteLabel1.setForeground(Color.red);
	noteLabel2.setForeground(Color.red);
	noteLabel1.setHorizontalAlignment(SwingConstants.CENTER);
	noteLabel2.setHorizontalAlignment(SwingConstants.CENTER);
	northPanel.add(headerLabel);
	northPanel.add(noteLabel1);
	northPanel.add(noteLabel2);

	JPanel centerPanel = new JPanel();
	contentPane.add(centerPanel, BorderLayout.CENTER);
	String columnNames[] = { "Id", "Nom", "Tel","Adresse","Nationalité" };
	DefaultTableModel tModel = new DefaultTableModel(columnNames, 0);

	JXTable table = new JXTable(tModel) {

	    @Override
	    public boolean isCellEditable(int row, int column) {
		if (column == 0)
		    return false;
		return true;
	    }
	   
	};

	for (Client cl : Launcher.clients) {
	    tModel.addRow(new Object[] { cl.getId(),cl.getNom(),cl.getTel(),cl.getAdresse(),cl.getNat() });
	}
	
	table.getTableHeader().setReorderingAllowed(false);
	table.packAll();

	JScrollPane scrollPane = new JScrollPane(table);
	table.setFillsViewportHeight(true);
	centerPanel.add(scrollPane);

	JPanel southPanel = new JPanel();
	contentPane.add(southPanel, BorderLayout.SOUTH);
	JButton add = new JButton("Ajouter");
	JButton del = new JButton("Supprimer");
	southPanel.add(add);
	southPanel.add(del);

	tModel.addTableModelListener(new TableModelListener() {

	    @Override
	    public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.UPDATE) {
		    int col = e.getColumn();
		    int row = e.getFirstRow();
		    int id = (int) table.getValueAt(row, 0);
		    String nom = table.getValueAt(row, 1).toString();
		    String tel = table.getValueAt(row, 2).toString();
		    String adr = table.getValueAt(row, 3).toString();
		    String nat = table.getValueAt(row, 4).toString();
		    Client cl=Launcher.findClient(id);
		    if (nom.trim().equals("")) {
			JOptionPane.showMessageDialog(contentPane, "Le nom du client ne peut pas etre vide!", "Attention", JOptionPane.WARNING_MESSAGE);
			table.setValueAt(cl.getNom(), row, col);
		    } 
		    Launcher.updClient(id, nom, tel, adr, nat);
		}

	    }
	});

	add.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		AjoutClient gui = new AjoutClient(tModel);
		gui.setVisible(true);
	    }
	});

	del.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int rows[] = table.getSelectedRows();
		if (rows.length == 0) {
		    JOptionPane.showMessageDialog(centerPanel, "Veuillez selectioner les Clients a supprimer.", "Information", JOptionPane.INFORMATION_MESSAGE);
		} else {
		    for (int i = rows.length - 1; i >= 0; i--) {
			Launcher.delClient((int) tModel.getValueAt(rows[i], 0));
			tModel.removeRow(rows[i]);
		    }
		}
	    }
	});

	addWindowListener(new WindowListener() {
	    
	    @Override
	    public void windowOpened(WindowEvent e) {
		
	    }
	    
	    @Override
	    public void windowIconified(WindowEvent e) {
		
	    }
	    
	    @Override
	    public void windowDeiconified(WindowEvent e) {
		
	    }
	    
	    @Override
	    public void windowDeactivated(WindowEvent e) {
		MainGUI.exited=true;
		
	    }
	    
	    @Override
	    public void windowClosing(WindowEvent e) {
		
		
	    }
	    
	    @Override
	    public void windowClosed(WindowEvent e) {
		
	    }
	    
	    @Override
	    public void windowActivated(WindowEvent e) {
		
	    }
	});
	
	pack();
	contentPane.grabFocus();
	setResizable(false);
	setLocationRelativeTo(null);
    }
}
