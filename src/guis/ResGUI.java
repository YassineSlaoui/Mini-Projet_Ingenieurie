package guis;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.DateFormatter;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.calendar.DatePickerFormatter;
import org.jdesktop.swingx.table.DatePickerCellEditor;

import launcher.Launcher;
import model.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.formdev.flatlaf.ui.FlatLineBorder;

@SuppressWarnings({ "unused", "serial" })
public class ResGUI extends JFrame {

    public ResGUI() {
	setTitle("Gestion des Reservations");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel contentPane = new JPanel();
	getContentPane().add(contentPane);
	contentPane.setBorder(new EmptyBorder(new Insets(15, 40, 15, 40)));
	contentPane.setLayout(new BorderLayout());

	JPanel northPanel = new JPanel();
	contentPane.add(northPanel, BorderLayout.NORTH);
	JLabel headerLabel = new JLabel("Reservations de l'Hotel:");
	headerLabel.setFont(new Font("Times new roman", Font.BOLD, 24));
	northPanel.add(headerLabel);

	JPanel centerPanel = new JPanel();
	contentPane.add(centerPanel, BorderLayout.CENTER);
	String columnNames[] = { "Id", "Chambre - Type - Capacité", "Client", "Date Debut", "Date Fin" };
	DefaultTableModel tModel = new DefaultTableModel(columnNames, 0);

	JXTable table = new JXTable(tModel) {

	    @Override
	    public boolean isCellEditable(int row, int column) {
		if (column == 0)
		    return false;
		return true;
	    }

	    @Override
	    public Class<?> getColumnClass(int column) {
		if (column > 2)
		    return Timestamp.class;
		return super.getColumnClass(column);
	    }
	};

	for (Reservation res : Launcher.reservations) {
	    tModel.addRow(new Object[] { res.getId(), res.getChambre(), res.getClient(), res.getDebut(), res.getFin() });
	}

	JComboBox<Chambre> chBox = new JComboBox<>();
	for (Chambre ch : Launcher.chambres)
	    chBox.addItem(ch);
	JComboBox<Client> clBox = new JComboBox<>();
	for (Client cl : Launcher.clients)
	    clBox.addItem(cl);
	table.getColumn(1).setCellEditor(new DefaultCellEditor(chBox));
	table.getColumn(2).setCellEditor(new DefaultCellEditor(clBox));
	table.getColumn(3).setCellEditor(new DatePickerCellEditor());
	table.getColumn(4).setCellEditor(new DatePickerCellEditor());

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

	    @SuppressWarnings("deprecation")
	    @Override
	    public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.UPDATE) {
		    int col = e.getColumn();
		    int row = e.getFirstRow();
		    int id = (int) table.getValueAt(row, 0);
		    Chambre ch = (Chambre) table.getValueAt(row, 1);
		    Client cl = (Client) table.getValueAt(row, 2);
		    Date d = (Date) table.getValueAt(row, 3);
		    Date f = (Date) table.getValueAt(row, 4);
		    Timestamp deb = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(d.getYear() + 1900, d.getMonth() + 1, d.getDate()), LocalTime.of(0, 0)));
		    Timestamp fin = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(f.getYear() + 1900, f.getMonth() + 1, f.getDate()), LocalTime.of(0, 1)));
		    Reservation res = Launcher.findReservation(id);
		    Reservation r;
		    if (deb.after(fin)) {
			JOptionPane.showMessageDialog(contentPane, "La date de debut ne doit pas depasser la date fin!", "Attention", JOptionPane.WARNING_MESSAGE);
			if (col == 3)
			    table.setValueAt(res.getDebut(), row, 3);
			else
			    table.setValueAt(res.getFin(), row, 4);
		    } else if (((r = Launcher.chambreDispo(ch, deb, fin)) != null) && !r.getClient().equals(cl) && ((col == 3) || (col == 4))) {
			JOptionPane.showMessageDialog(contentPane, "Cette chambre est deja reservée dans cet intervalle, IdRes = " + r.getId() + ", par: " + r.getClient() + ".", "Attention", JOptionPane.WARNING_MESSAGE);
			if (col == 3)
			    table.setValueAt(res.getDebut(), row, 3);
			else
			    table.setValueAt(res.getFin(), row, 4);
		    } else
			Launcher.updReservation(id, deb, fin, cl, ch);
		}

	    }
	});

	add.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		AjoutRes gui = new AjoutRes(tModel);
		gui.setVisible(true);
	    }
	});

	del.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int rows[] = table.getSelectedRows();
		if (rows.length == 0) {
		    JOptionPane.showMessageDialog(centerPanel, "Veuillez selectioner les Reservations a supprimer.", "Information", JOptionPane.INFORMATION_MESSAGE);
		} else {
		    for (int i = rows.length - 1; i >= 0; i--) {
			Launcher.delReservation((int) tModel.getValueAt(rows[i], 0));
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
