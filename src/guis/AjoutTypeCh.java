package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
public class AjoutTypeCh extends JDialog implements ActionListener {
    private JPanel contentPane = new JPanel();
    private JLabel typeLabel = new JLabel("Nouveau Type: ");
    private JTextField typeField = new JTextField(10);
    private JLabel capLabel = new JLabel("Capacité:    ");
    private JTextField capField = new JTextField(10);
    private DefaultTableModel tModel = null;

    public AjoutTypeCh(DefaultTableModel tModel) {
	setModal(true);
	setTitle("Ajout de Types de Chambres");
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

	JPanel typePanel = new JPanel();
	typePanel.add(typeLabel);
	typePanel.add(typeField);
	centerPanel.add(typePanel);
	typeField.addActionListener(this);

	JPanel capPanel = new JPanel();
	capPanel.add(capLabel);
	capPanel.add(capField);
	centerPanel.add(capPanel);
	capField.addActionListener(this);

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
	int cap = -1;
	try {
	    cap = Integer.valueOf(capField.getText());
	} catch (Exception e2) {

	}
	String type=typeField.getText().trim();
	if (cap < 0) {
	    JOptionPane.showMessageDialog(contentPane, "La capacité doit etre numerique et positive!", "Attention", JOptionPane.WARNING_MESSAGE);
	}else if (type.equals("")) {
	    JOptionPane.showMessageDialog(contentPane, "Le type doit avoir un nom!", "Attention", JOptionPane.WARNING_MESSAGE);
	} else {
	    int id = Launcher.addTypeCh(type, cap);
	    tModel.addRow(new Object[] { id, type, cap });
	    typeField.setText("");
	    capField.setText("");
	}
    }

}
