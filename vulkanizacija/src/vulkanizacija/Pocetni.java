package vulkanizacija;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Pocetni {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pocetni window = new Pocetni();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Pocetni() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Vulkanizacija");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Artikli");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Pregled artikala");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Dodaj artikal");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgUnosArtikala dlg = new DlgUnosArtikala();
				dlg.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Djelatnici");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmUnosDjelatnika = new JMenuItem("Dodaj djelatnika");
		mntmUnosDjelatnika.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgUnosDjelatnika dlg = new DlgUnosDjelatnika();
				dlg.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmUnosDjelatnika);
		
		JMenuItem mntmPregledDjelatnika = new JMenuItem("Pregled djelatnika");
		mntmPregledDjelatnika.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgPregledDjelatnika dlg = new DlgPregledDjelatnika();
				dlg.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmPregledDjelatnika);
		
		JMenu mnNewMenu_2 = new JMenu("Dobavljači");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmUnosDobavljaca = new JMenuItem("Dodaj dobavljača");
		mntmUnosDobavljaca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgUnosDobavljaca dlg = new DlgUnosDobavljaca();
				dlg.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmUnosDobavljaca);
		
		JMenuItem mntmPregledDobavljaca = new JMenuItem("Pregled dobavljača");
		mntmPregledDobavljaca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgPregledDobavljaca dlg = new DlgPregledDobavljaca();
				dlg.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmPregledDobavljaca);
	}
}
