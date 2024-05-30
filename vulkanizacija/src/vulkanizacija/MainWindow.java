package vulkanizacija;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Djelatnik");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnUnosDjelatnika = new JButton("Unos djelatnika");
		btnUnosDjelatnika.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUnosDjelatnika.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgUnosDjelatnika dlg = new DlgUnosDjelatnika();
				dlg.setVisible(true);
			}
		});
		btnUnosDjelatnika.setBounds(10, 20, 165, 80);
		frame.getContentPane().add(btnUnosDjelatnika);
		
		JButton btnPregledDjelatnika = new JButton("Pregled djelatnika");
		btnPregledDjelatnika.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgPregledDjelatnika dlg = new DlgPregledDjelatnika();
				dlg.setVisible(true);
			}
		});
		btnPregledDjelatnika.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPregledDjelatnika.setBounds(10, 130, 165, 80);
		frame.getContentPane().add(btnPregledDjelatnika);
	}
}
