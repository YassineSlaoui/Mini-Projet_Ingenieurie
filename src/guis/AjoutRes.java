package guis;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXDatePicker;
import launcher.Launcher;
import model.Chambre;
import model.Client;
import model.Reservation;

@SuppressWarnings("serial")
public class AjoutRes extends JDialog {

    public AjoutRes(DefaultTableModel tModel) {
	setModal(true);
	setTitle("Création de Reservations");
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel contentPane = new JPanel();
	getContentPane().add(contentPane);
	contentPane.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
	contentPane.setLayout(new BorderLayout(0, 0));
	contentPane.setLayout(new BorderLayout());

	JLabel northLabel = new JLabel("Selectionner les informations suivantes:");
	northLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
	contentPane.add(northLabel, BorderLayout.NORTH);
	northLabel.setBorder(new EmptyBorder(new Insets(0, 0, 10, 0)));

	JPanel centerPanel = new JPanel(new GridLayout(4, 1));
	contentPane.add(centerPanel, BorderLayout.CENTER);

	JComboBox<Client> clBox = new JComboBox<>();
	clBox.setBorder(new TitledBorder("Client:"));
	for (Client cl : Launcher.clients)
	    clBox.addItem(cl);
	JPanel clPanel = new JPanel();
	clPanel.add(clBox);
	centerPanel.add(clPanel);

	JComboBox<Chambre> chBox = new JComboBox<>();
	chBox.setBorder(new TitledBorder("Chambre"));
	for (Chambre ch : Launcher.chambres)
	    chBox.addItem(ch);
	JPanel chPanel = new JPanel();
	chPanel.add(chBox);
	centerPanel.add(chPanel);

	JXDatePicker debDatePicker = new JXDatePicker();
	debDatePicker.setBorder(new TitledBorder("Date Debut"));
	JPanel debPanel = new JPanel();
	debPanel.add(debDatePicker);
	centerPanel.add(debPanel);

	JXDatePicker finDatePicker = new JXDatePicker();
	finDatePicker.setBorder(new TitledBorder("Date Fin"));
	JPanel finPanel = new JPanel();
	finPanel.add(finDatePicker);
	centerPanel.add(finPanel);

	JPanel southPanel = new JPanel();
	contentPane.add(southPanel, BorderLayout.SOUTH);
	JButton submit = new JButton("Soumettre");
	southPanel.add(submit);
	submit.addActionListener(new ActionListener() {

	    @SuppressWarnings("deprecation")
	    @Override
	    public void actionPerformed(ActionEvent e) {
		Reservation r;
		Chambre ch = (Chambre) chBox.getSelectedItem();
		Client cl = (Client) clBox.getSelectedItem();
		Date d = debDatePicker.getDate();
		Date f = finDatePicker.getDate();
		Timestamp deb = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(d.getYear() + 1900, d.getMonth() + 1, d.getDate()), LocalTime.of(0, 0)));
		Timestamp fin = Timestamp.valueOf(LocalDateTime.of(LocalDate.of(f.getYear() + 1900, f.getMonth() + 1, f.getDate()), LocalTime.of(0, 1)));
		if (deb.after(fin)) {
		    JOptionPane.showMessageDialog(contentPane, "La date de debut ne doit pas depasser la date fin!", "Attention", JOptionPane.WARNING_MESSAGE);
		} else if ((r = Launcher.chambreDispo(ch, deb, fin)) != null) {
		    if (!r.getClient().equals(cl))
			JOptionPane.showMessageDialog(contentPane, "Cette chambre est deja reservée dans cet intervalle, IdRes = " + r.getId() + ", par: " + r.getClient() + ".", "Attention", JOptionPane.WARNING_MESSAGE);
		    else
			JOptionPane.showMessageDialog(contentPane, "Cette chambre est deja reservée par ce meme client dans cet intervalle, IdRes = " + r.getId() + ".", "Attention", JOptionPane.WARNING_MESSAGE);
		} else {
		    int autoId = Launcher.addReservation(deb, fin, cl, ch);
		    tModel.addRow(new Object[] {autoId, ch, cl, deb, fin});
		}
	    }
	});

	pack();
	contentPane.grabFocus();
	setResizable(false);
	setLocationRelativeTo(null);
    }
}