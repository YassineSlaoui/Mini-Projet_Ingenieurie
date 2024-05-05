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
public class StatsGUI extends JFrame{

    public StatsGUI() {
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	JPanel c = new JPanel();
	this.getContentPane().add(c);
	c.setLayout(new BorderLayout());
	c.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
	this.setTitle("Espace des Statistiques de l'Hotel");
	c.setLayout(new BorderLayout());

	JPanel northPanel = new JPanel();
	northPanel.setLayout(new FlowLayout());
	JLabel welcomingLabel = new JLabel("Bienvenue au Statistiques");
	welcomingLabel.setFont(new Font("Serif", Font.BOLD, 24));
	northPanel.add(welcomingLabel);
	c.add(northPanel);

	JPanel centerPanel = new JPanel();
	centerPanel.setLayout(new GridLayout(3, 1, 0, 5));
	TitledBorder tBorder = new TitledBorder(new FlatLineBorder(new Insets(2, 5, 2, 5), Color.gray), "BIENVENUE DANS L'ESPACE DES STATISTIQUES DE L'HOTEL");
	tBorder.setTitleFont(new Font("Tahoma", Font.PLAIN, 14));
	centerPanel.setBorder(tBorder);
	JLabel infoLbl1 = new JLabel("Vous pouvez dans cet espace:");
	JLabel infoLbl2 = new JLabel("     -Consulter les statistiques des chambres");
	JLabel infoLbl3 = new JLabel("     -Consulter les statistiques des Clients");
	centerPanel.add(infoLbl1);
	centerPanel.add(infoLbl2);
	centerPanel.add(infoLbl3);
	c.add(centerPanel, BorderLayout.CENTER);

	JButton chambresBut = new JButton("Chambres");
	chambresBut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
		ChambresStatGUI gui = new ChambresStatGUI();
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

	JButton clientBut = new JButton("Clients");
	clientBut.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		setVisible(false);
		ClientsStatsGUI gui = new ClientsStatsGUI();
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

	c.add(northPanel, BorderLayout.NORTH);
	c.add(southPanel, BorderLayout.SOUTH);
	pack();
	setSize((int)(getWidth()*1.5),getHeight());
	setResizable(false);
	getContentPane().requestFocus();
	setLocationRelativeTo(null);
    }
}
