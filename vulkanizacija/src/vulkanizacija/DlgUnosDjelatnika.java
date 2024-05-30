package vulkanizacija;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.sql.Connection; // Dodan import
import java.sql.DriverManager; // Dodan import

import javax.swing.JOptionPane; // Dodan import

public class DlgUnosDjelatnika extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldIme;
	private JTextField textFieldPrezime;
	private JTextField textFieldIdDj;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgUnosDjelatnika dialog = new DlgUnosDjelatnika();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgUnosDjelatnika() {
		setTitle("Unos djelatnika");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ime djelatnika");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(24, 25, 120, 25);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Prezime djelatnika");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(24, 70, 120, 25);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("ID djelatnika");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(24, 115, 120, 25);
		contentPanel.add(lblNewLabel_2);
		
		textFieldIme = new JTextField();
		textFieldIme.setBounds(184, 25, 149, 25);
		contentPanel.add(textFieldIme);
		textFieldIme.setColumns(10);
		
		textFieldPrezime = new JTextField();
		textFieldPrezime.setBounds(184, 70, 149, 25);
		contentPanel.add(textFieldPrezime);
		textFieldPrezime.setColumns(10);
		
		textFieldIdDj = new JTextField();
		textFieldIdDj.setBounds(184, 115, 149, 25);
		contentPanel.add(textFieldIdDj);
		textFieldIdDj.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ime = textFieldIme.getText();
						String prezime = textFieldPrezime.getText();
						String idDj = textFieldIdDj.getText();
						
						try {						
						 	  Class.forName("com.mysql.cj.jdbc.Driver") .newInstance();
							  Connection conn = DriverManager.getConnection (
								"jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
								"user=dmiskulin&password=11");
							  
							  String sql = "INSERT INTO Djelatnik VALUES(?,?,?);";
							  
							  PreparedStatement stmt = conn.prepareStatement(sql);
					          stmt.setString(1, ime);
					          stmt.setString(2, prezime);
					          stmt.setString(3, idDj);
					          stmt.execute();
					          conn.close();
					          textFieldIme.setText("");
					          textFieldPrezime.setText("");
					          textFieldIdDj.setText("");
					          
						} catch(Exception ex) {
				            JOptionPane.showMessageDialog(null,
				            ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
				        }

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
	}
}
