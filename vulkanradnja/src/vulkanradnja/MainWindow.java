package vulkanradnja;

import java.awt.EventQueue;

import javax.swing.JFrame;

import vulkanradnja.DlgPregledDobavljaca;

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
		frame.setTitle("Dobavljac");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnUnosDobavljaca = new JButton("Unos dobavljaca");
		btnUnosDobavljaca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUnosDobavljaca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgUnosDobavljaca dlg = new DlgUnosDobavljaca();
				dlg.setVisible(true);
	
			}
		});
		btnUnosDobavljaca.setBounds(25, 29, 160, 80);
		frame.getContentPane().add(btnUnosDobavljaca);
		
		JButton btnPregledDobavljaca = new JButton("Pregled dobavljaca");
		btnPregledDobavljaca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgPregledDobavljaca dlg = new DlgPregledDobavljaca();
				dlg.setVisible(true);
			}
		});
		btnPregledDobavljaca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPregledDobavljaca.setBounds(25, 153, 160, 80);
		frame.getContentPane().add(btnPregledDobavljaca);
	}

}
