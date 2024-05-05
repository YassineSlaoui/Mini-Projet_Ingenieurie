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

@SuppressWarnings({ "serial", "unused" })
public class MainGUI extends JFrame {

    static public Boolean exited = false;

    public MainGUI(User u) {
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	JPanel c = new JPanel();
	this.getContentPane().add(c);
	c.setLayout(new BorderLayout());
	c.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
	this.setTitle("Espace de Gestion de l'Hotel");
	c.setLayout(new BorderLayout());

	JPanel northPanel = new JPanel();
	northPanel.setLayout(new FlowLayout());
	JLabel welcomingLabel = new JLabel("Bienvenue, " + u.getNom());
	welcomingLabel.setFont(new Font("Serif", Font.BOLD, 24));
	northPanel.add(welcomingLabel);
	c.add(northPanel);

	JMenuBar mBar = new JMenuBar();
	JMenu menu = new JMenu("Deconnexion");
	mBar.add(menu);
	menu.addMouseListener(new MouseListener() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		AuthGUI.loggedOut = true;
		dispose();
	    }
	});
	this.setJMenuBar(mBar);

	JPanel centerPanel = new JPanel();
	centerPanel.setLayout(new GridLayout(7, 1, 0, 5));
	TitledBorder tBorder = new TitledBorder(new FlatLineBorder(new Insets(2, 5, 2, 5), Color.gray), "BIENVENUE DANS L'ESPACE DE GESTION DE L'HOTEL!");
	tBorder.setTitleFont(new Font("Tahoma", Font.PLAIN, 14));
	centerPanel.setBorder(tBorder);
	JLabel infoLbl1 = new JLabel("Vous pouvez dans cet espace:");
	JLabel infoLbl2 = new JLabel("     -Gerer les Chambres");
	JLabel infoLbl3 = new JLabel("     -Gerer les Clients");
	JLabel infoLbl4 = new JLabel("     -Gerer les Reservations");
	JLabel infoLbl5 = new JLabel("     -Gerer les Types des Chambres de l'Hotel");
	JLabel infoLbl6 = new JLabel("     -Voir des statistiques sur les clients et les chambres");
	JLabel infoLbl7 = new JLabel("     -Gerer les utilisateur de cette application");
	centerPanel.add(infoLbl1);
	centerPanel.add(infoLbl2);
	centerPanel.add(infoLbl3);
	centerPanel.add(infoLbl4);
	centerPanel.add(infoLbl5);
	centerPanel.add(infoLbl6);
	centerPanel.add(infoLbl7);
	c.add(centerPanel, BorderLayout.CENTER);

	JButton chambresBut = new JButton("Chambres");
	chambresBut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		ChambresGUI gui = new ChambresGUI();
		gui.setVisible(true);
		gui.addWindowListener(new WindowListener() {

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

		    }

		    @Override
		    public void windowClosing(WindowEvent e) {
		    }

		    @Override
		    public void windowClosed(WindowEvent e) {
			if (exited) {
			    setVisible(true);
			    c.grabFocus();
			    exited = !exited;
			}
		    }

		    @Override
		    public void windowActivated(WindowEvent e) {

		    }
		});
	    }
	});

	JButton clientBut = new JButton("Clients");
	clientBut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		ClientsGUI gui = new ClientsGUI();
		gui.setVisible(true);
		gui.addWindowListener(new WindowListener() {

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

		    }

		    @Override
		    public void windowClosing(WindowEvent e) {
		    }

		    @Override
		    public void windowClosed(WindowEvent e) {
			setVisible(true);
			c.grabFocus();
		    }

		    @Override
		    public void windowActivated(WindowEvent e) {

		    }
		});
	    }
	});

	JButton resBut = new JButton("Reservations");
	resBut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		ResGUI gui = new ResGUI();
		gui.setVisible(true);
		gui.addWindowListener(new WindowListener() {

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

		    }

		    @Override
		    public void windowClosing(WindowEvent e) {
		    }

		    @Override
		    public void windowClosed(WindowEvent e) {
			setVisible(true);
			c.grabFocus();
		    }

		    @Override
		    public void windowActivated(WindowEvent e) {

		    }
		});
	    }
	});

	JButton typesBut = new JButton("Types Chambres");
	typesBut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		TypesGUI gui = new TypesGUI();
		gui.setVisible(true);
		gui.addWindowListener(new WindowListener() {

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

		    }

		    @Override
		    public void windowClosing(WindowEvent e) {
		    }

		    @Override
		    public void windowClosed(WindowEvent e) {
			setVisible(true);
			c.grabFocus();
		    }

		    @Override
		    public void windowActivated(WindowEvent e) {

		    }
		});
	    }
	});

	JButton statBut = new JButton("Statistiques");
	statBut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		StatsGUI gui = new StatsGUI();
		gui.setVisible(true);
		gui.addWindowListener(new WindowListener() {

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

		    }

		    @Override
		    public void windowClosing(WindowEvent e) {
		    }

		    @Override
		    public void windowClosed(WindowEvent e) {
			setVisible(true);
			c.grabFocus();
		    }

		    @Override
		    public void windowActivated(WindowEvent e) {

		    }
		});
	    }
	});

	JButton userButton = new JButton("Utilisateurs");
	userButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		UserGUI gui = new UserGUI();
		gui.setVisible(true);
		gui.addWindowListener(new WindowListener() {

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

		    }

		    @Override
		    public void windowClosing(WindowEvent e) {

		    }

		    @Override
		    public void windowClosed(WindowEvent e) {
			setVisible(true);
			c.grabFocus();
		    }

		    @Override
		    public void windowActivated(WindowEvent e) {

		    }
		});
	    }
	});

	JPanel southPanel = new JPanel();
	southPanel.setLayout(new FlowLayout());
	southPanel.add(chambresBut);
	southPanel.add(clientBut);
	southPanel.add(resBut);
	southPanel.add(typesBut);
	southPanel.add(statBut);
	southPanel.add(userButton);

	c.add(northPanel, BorderLayout.NORTH);
	c.add(southPanel, BorderLayout.SOUTH);
	pack();
	setResizable(false);
	getContentPane().requestFocus();
	setLocationRelativeTo(null);
    }
}
