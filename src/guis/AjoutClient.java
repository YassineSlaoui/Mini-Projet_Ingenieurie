package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.ui.FlatLineBorder;
import launcher.Launcher;

@SuppressWarnings("serial")
public class AjoutClient extends JDialog implements ActionListener {
    private JPanel contentPane = new JPanel();
    private JLabel nomLabel = new JLabel("Nom Client: ");
    private JTextField nomField = new JTextField(10);
    private JLabel numLabel = new JLabel("Telephone:   ");
    private JTextField numField = new JTextField(10);
    private JLabel adrLabel = new JLabel("Adresse:       ");
    private JTextField adrArea = new JTextField(10);
    private JLabel natLabel = new JLabel("Nationalité:  ");
    private JTextField natField = new JTextField(10);
    private DefaultTableModel tModel = null;

    public AjoutClient(DefaultTableModel tModel) {
	setModal(true);
	setTitle("Ajout de Clients");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	getContentPane().add(contentPane);
	contentPane.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
	contentPane.setLayout(new BorderLayout(0, 0));

	this.tModel = tModel;

	JLabel northLabel = new JLabel("Saisir les informations suivantes:");
	northLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
	contentPane.add(northLabel, BorderLayout.NORTH);
	northLabel.setBorder(new EmptyBorder(new Insets(0, 0, 10, 0)));

	JPanel centerPanel = new JPanel(new GridLayout(4, 1));
	contentPane.add(centerPanel, BorderLayout.CENTER);
	centerPanel.setBorder(new FlatLineBorder(new Insets(10, 5, 10, 5), Color.gray));

	JPanel nomPanel = new JPanel();
	nomPanel.add(nomLabel);
	nomPanel.add(nomField);
	centerPanel.add(nomPanel);
	nomField.addActionListener(this);

	JPanel numPanel = new JPanel();
	numPanel.add(numLabel);
	numPanel.add(numField);
	centerPanel.add(numPanel);
	numField.addActionListener(this);

	JPanel adrPanel = new JPanel();
	adrPanel.add(adrLabel);
	adrPanel.add(adrArea);
	centerPanel.add(adrPanel);
	adrArea.addActionListener(this);

	JPanel natPanel = new JPanel();
	natPanel.add(natLabel);
	natPanel.add(natField);
	centerPanel.add(natPanel);
	natField.addActionListener(this);

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
	String nom = nomField.getText();
	String num = numField.getText();
	String adr = adrArea.getText();
	String nat = natField.getText();
	if (nom.trim().equals(""))
	    JOptionPane.showMessageDialog(contentPane, "Le nom du client ne peut pas etre vide!", "Attention", JOptionPane.WARNING_MESSAGE);
	else {
	    int id = Launcher.addClient(nom, num, adr, nat);
	    tModel.addRow(new Object[] { id, nom, num, adr, nat });
	    nomField.setText("");
	    numField.setText("");
	    adrArea.setText("");
	    natField.setText("");
	}

    }
}
