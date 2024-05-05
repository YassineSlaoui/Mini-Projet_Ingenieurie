package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.ui.FlatLineBorder;

import launcher.Launcher;
import model.TypeChambre;

@SuppressWarnings("serial")
public class AjoutChambre extends JDialog implements ActionListener {
    private JPanel contentPane = new JPanel();
    private JLabel numLabel = new JLabel("N° Chambre: ");
    private JTextField numField = new JTextField(10);
    private JLabel typeLabel = new JLabel("Type Chambre: ");
    private JComboBox<TypeChambre> typeBox = new JComboBox<>();
    private DefaultTableModel tModel = null;

    public AjoutChambre(DefaultTableModel tModel) {
	setModal(true);
	setTitle("Ajout de Chambres");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	getContentPane().add(contentPane);
	contentPane.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
	contentPane.setLayout(new BorderLayout(0, 0));

	this.tModel = tModel;

	JLabel northLabel = new JLabel("Saisir les informations suivantes:");
	northLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
	contentPane.add(northLabel, BorderLayout.NORTH);
	northLabel.setBorder(new EmptyBorder(new Insets(0, 0, 10, 0)));

	JPanel centerPanel = new JPanel();
	contentPane.add(centerPanel, BorderLayout.CENTER);
	centerPanel.setBorder(new FlatLineBorder(new Insets(10, 5, 10, 5), Color.gray));

	JPanel nomPanel = new JPanel();
	nomPanel.add(numLabel);
	nomPanel.add(numField);
	centerPanel.add(nomPanel);
	numField.addActionListener(this);

	JPanel typePanel = new JPanel();
	typePanel.add(typeLabel);
	for (TypeChambre type : Launcher.typeChambres)
	    typeBox.addItem(type);
	typePanel.add(typeBox);
	centerPanel.add(typePanel);

	JPanel southPanel = new JPanel();
	contentPane.add(southPanel, BorderLayout.SOUTH);
	JButton submit = new JButton("Soumettre");
	southPanel.add(submit);
	submit.addActionListener(this);

	pack();
	contentPane.grabFocus();
	setResizable(false);
	setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	int num=-1;
	try {
	    num=Integer.valueOf(numField.getText());
	} catch (Exception e2) {
	    
	}
	if (num<0) {
	    JOptionPane.showMessageDialog(contentPane, "Le numero doit etre numerique et positif!", "Attention", JOptionPane.WARNING_MESSAGE);
	} else {
	    TypeChambre selType = (TypeChambre)typeBox.getSelectedItem();
	    int id = Launcher.addChambre(num, selType);
	    if(id==-1) {
		    JOptionPane.showMessageDialog(contentPane, "Ce numero de chambre existe deja!", "Attention", JOptionPane.WARNING_MESSAGE);
	    }else {
		tModel.addRow(new Object[] { id, num, selType ,"Disponible"});
		numField.setText("");
	    }
	    
	}
    }
}
