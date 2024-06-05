package vulkanradnja;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class DlgUnosDobavljaca extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNaziv;
	private JTextField textFieldEmail;
	private JTextField textFieldIdDo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgUnosDobavljaca dialog = new DlgUnosDobavljaca();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgUnosDobavljaca() {
		setTitle("Unos dobavljaca");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Naziv dobavljaca");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel.setBounds(24, 25, 120, 25);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("email dobavljaca");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(24, 70, 120, 25);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("ID dobavljaca");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_2.setBounds(24, 115, 120, 25);
			contentPanel.add(lblNewLabel_2);
		}
		{
			textFieldNaziv = new JTextField();
			textFieldNaziv.setBounds(194, 27, 150, 25);
			contentPanel.add(textFieldNaziv);
			textFieldNaziv.setColumns(10);
		}
		{
			textFieldEmail = new JTextField();
			textFieldEmail.setBounds(194, 70, 150, 25);
			contentPanel.add(textFieldEmail);
			textFieldEmail.setColumns(10);
		}
		{
			textFieldIdDo = new JTextField();
			textFieldIdDo.setBounds(194, 115, 150, 25);
			contentPanel.add(textFieldIdDo);
			textFieldIdDo.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String naziv = textFieldNaziv.getText();
						String email = textFieldEmail.getText();
						String idDo = textFieldIdDo.getText();
						
						try {						
						 	  Class.forName("com.mysql.cj.jdbc.Driver") .newInstance();
							  Connection conn = DriverManager.getConnection (
									  "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
												"user=dmiskulin&password=11");
							  
							  String sql = "INSERT INTO Dobavljac VALUES(?,?,?);";
							  
							  PreparedStatement stmt = conn.prepareStatement(sql);
					          stmt.setString(1, naziv);
					          stmt.setString(2, email);
					          stmt.setString(3, idDo);
					          stmt.execute();
					          conn.close();
					          textFieldNaziv.setText("");
					          textFieldEmail.setText("");
					          textFieldIdDo.setText("");
					          
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
