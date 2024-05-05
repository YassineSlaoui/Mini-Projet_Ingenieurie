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
public class TypesGUI extends JFrame{

    public TypesGUI() {
	setTitle("Gestion des Types des chambres");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel contentPane = new JPanel();
	getContentPane().add(contentPane);
	contentPane.setBorder(new EmptyBorder(new Insets(15, 40, 15, 40)));
	contentPane.setLayout(new BorderLayout());

	JPanel northPanel = new JPanel();
	contentPane.add(northPanel, BorderLayout.NORTH);
	JLabel headerLabel = new JLabel("Types de Chambres de l'Hotel:");
	headerLabel.setFont(new Font("Times new roman", Font.BOLD, 24));
	northPanel.add(headerLabel);

	JPanel centerPanel = new JPanel();
	contentPane.add(centerPanel, BorderLayout.CENTER);
	String columnNames[] = { "Id", "Type", "Capacité" };
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
		if (column == 2)
		    return Integer.class;
		return super.getColumnClass(column);
	    }
	};

	for (TypeChambre type : Launcher.typeChambres) {
	    tModel.addRow(new Object[] { type.getId(), type.getType(), type.getCapacite() });
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
		    String type = table.getValueAt(row, 1).toString();
		    int cap = (int) table.getValueAt(row, 2);
		    TypeChambre tc=Launcher.findTypeChambre(id);
		    if (cap < 0) {
			JOptionPane.showMessageDialog(contentPane, "La capacité d'un type de chambre ne peut pas etre negative!", "Attention", JOptionPane.WARNING_MESSAGE);
			table.setValueAt(tc.getCapacite(), row, col);
		    } else 
			Launcher.updTypeCh(id, type, cap);
		}

	    }
	});

	add.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		AjoutTypeCh gui = new AjoutTypeCh(tModel);
		gui.setVisible(true);
	    }
	});

	del.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int rows[] = table.getSelectedRows();
		if (rows.length == 0) {
		    JOptionPane.showMessageDialog(centerPanel, "Veuillez selectioner les Types de Chambres a supprimer.", "Information", JOptionPane.INFORMATION_MESSAGE);
		} else {
		    for (int i = rows.length - 1; i >= 0; i--) {
			Launcher.delTypeCh((int) tModel.getValueAt(rows[i], 0));
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
