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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class ChambresGUI extends JFrame {

    public ChambresGUI() {
	setTitle("Gestion des Chambres");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel contentPane = new JPanel();
	getContentPane().add(contentPane);
	contentPane.setBorder(new EmptyBorder(new Insets(15, 40, 15, 40)));
	contentPane.setLayout(new BorderLayout());

	JPanel northPanel = new JPanel();
	contentPane.add(northPanel, BorderLayout.NORTH);
	JLabel headerLabel = new JLabel("Chambres de l'Hotel:");
	headerLabel.setFont(new Font("Times new roman", Font.BOLD, 24));
	northPanel.add(headerLabel);

	JPanel centerPanel = new JPanel();
	contentPane.add(centerPanel, BorderLayout.CENTER);
	String columnNames[] = { "Id", "Numero", "Type - Capacité", "Etat Actuel (Le "+LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+")" };
	DefaultTableModel tModel = new DefaultTableModel(columnNames, 0);

	JXTable table = new JXTable(tModel) {

	    @Override
	    public boolean isCellEditable(int row, int column) {
		if (column == 0 || column == 3)
		    return false;
		return true;
	    }

	    @Override
	    public Class<?> getColumnClass(int column) {
		if (column == 1)
		    return Integer.class;
		return super.getColumnClass(column);
	    }
	};

	for (Chambre ch : Launcher.chambres)
	    tModel.addRow(new Object[] { ch.getId(), ch.getNum(), ch.getType(), Launcher.etatChambre(ch) });
	
	JComboBox<TypeChambre> typeBox = new JComboBox<TypeChambre>();
	for (TypeChambre type : Launcher.typeChambres)
	    typeBox.addItem(type);
	table.getColumn(2).setCellEditor(new DefaultCellEditor(typeBox));
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
		    int num = (int) table.getValueAt(row, 1);
		    TypeChambre type = (TypeChambre) table.getValueAt(row, 2);
		    Chambre ch = Launcher.findChambre(id);
		    if (num < 0) {
			JOptionPane.showMessageDialog(contentPane, "Le numero de Chambre ne peut pas etre negatif!", "Attention", JOptionPane.WARNING_MESSAGE);
			table.setValueAt(ch.getNum(), row, col);
		    } else if (!Launcher.updChambre(id, num, type)) {
			JOptionPane.showMessageDialog(contentPane, "Ce numero de chambre existe deja!", "Attention", JOptionPane.WARNING_MESSAGE);
			table.setValueAt(ch.getNum(), row, col);
		    }
		}

	    }
	});

	add.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		AjoutChambre gui = new AjoutChambre(tModel);
		gui.setVisible(true);
	    }
	});

	del.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int rows[] = table.getSelectedRows();
		if (rows.length == 0) {
		    JOptionPane.showMessageDialog(centerPanel, "Veuillez selectioner les Chambres a supprimer.", "Information", JOptionPane.INFORMATION_MESSAGE);
		} else {
		    for (int i = rows.length - 1; i >= 0; i--) {
			Launcher.delChambre((int) tModel.getValueAt(rows[i], 0));
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
