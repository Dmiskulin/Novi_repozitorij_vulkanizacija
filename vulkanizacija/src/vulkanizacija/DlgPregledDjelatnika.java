package vulkanizacija;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgPregledDjelatnika extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea textAreaPregled;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgPregledDjelatnika dialog = new DlgPregledDjelatnika();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgPregledDjelatnika() {
		setTitle("Pregled djelatnika");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 416, 212);
		contentPanel.add(scrollPane);
		
		textAreaPregled = new JTextArea();
		scrollPane.setViewportView(textAreaPregled);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		selectPregledDjelatnika();
	}
	private void selectPregledDjelatnika() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
				                                   "user=dmiskulin&password=11");
			
			String sql = "SELECT * FROM Djelatnik";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String tekst = "";
			while (rs.next()) {
				tekst += "Ime: "+rs.getString("ime")+"\t";				
				tekst += "Prezime: "+rs.getString("prezime")+"\t"+"\t";
				tekst += "ID: "+rs.getString("IdDj")+"\n";
				
			}	
			textAreaPregled.setText(tekst);
			conn.close();
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Gre�ka", JOptionPane.ERROR_MESSAGE);
		}
	
	}
}
