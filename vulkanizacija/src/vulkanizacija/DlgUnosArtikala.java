package vulkanizacija;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DlgUnosArtikala extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldSifra_artikla;
    private JTextField textFieldModel;
    private JTextField textFieldDimenzije;
    private JTextField textFieldTip;
    private JTextField textFieldCijena;
    private JComboBox<String> comboBoxDobavljac;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DlgUnosArtikala dialog = new DlgUnosArtikala();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DlgUnosArtikala() {
        setTitle("Unos artikala");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Šifra artikla");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel.setBounds(10, 21, 100, 20);
        contentPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Model");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(10, 51, 100, 20);
        contentPanel.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Dimenzije");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_2.setBounds(10, 81, 100, 20);
        contentPanel.add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("Tip");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_3.setBounds(10, 111, 100, 20);
        contentPanel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Cijena");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_4.setBounds(10, 141, 100, 20);
        contentPanel.add(lblNewLabel_4);
        
        JLabel lblNewLabel_5 = new JLabel("Dobavljač");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_5.setBounds(10, 171, 100, 20);
        contentPanel.add(lblNewLabel_5);
        
        textFieldSifra_artikla = new JTextField();
        textFieldSifra_artikla.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldSifra_artikla.setBounds(150, 21, 200, 20);
        contentPanel.add(textFieldSifra_artikla);
        textFieldSifra_artikla.setColumns(10);
        
        textFieldModel = new JTextField();
        textFieldModel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldModel.setBounds(150, 51, 200, 20);
        contentPanel.add(textFieldModel);
        textFieldModel.setColumns(10);
        
        textFieldDimenzije = new JTextField();
        textFieldDimenzije.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldDimenzije.setBounds(150, 81, 200, 20);
        contentPanel.add(textFieldDimenzije);
        textFieldDimenzije.setColumns(10);
        
        textFieldTip = new JTextField();
        textFieldTip.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldTip.setBounds(150, 111, 200, 20);
        contentPanel.add(textFieldTip);
        textFieldTip.setColumns(10);
        
        textFieldCijena = new JTextField();
        textFieldCijena.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldCijena.setBounds(150, 141, 200, 20);
        contentPanel.add(textFieldCijena);
        textFieldCijena.setColumns(10);
        
        comboBoxDobavljac = new JComboBox<>();
        comboBoxDobavljac.setBounds(150, 171, 200, 20);
        contentPanel.add(comboBoxDobavljac);

        // Popunjavanje JComboBoxa podacima o dobavljačima
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
                "user=dmiskulin&password=11");
            String sql = "SELECT idDo, naziv FROM Dobavljac";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idDo");
                String naziv = rs.getString("naziv");
                comboBoxDobavljac.addItem(id + " - " + naziv);
            }
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
        
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String sifraArtikla = textFieldSifra_artikla.getText();
                        String model = textFieldModel.getText();
                        String dimenzije = textFieldDimenzije.getText();
                        String tip = textFieldTip.getText();
                        String cijena = textFieldCijena.getText();
                        String dobavljac = (String) comboBoxDobavljac.getSelectedItem();
                        
                        if (dobavljac != null) {
                            String[] parts = dobavljac.split(" - ");
                            String idDo = parts[0];
                            
                            try {
                                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                                Connection conn = DriverManager.getConnection(
                                    "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
                                    "user=dmiskulin&password=11");
                                String sql = "INSERT INTO Artikli_skl (Sifra_artikla, Model, Dimenzije, Tip, Cijena, idDo) VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement stmt = conn.prepareStatement(sql);
                                stmt.setString(1, sifraArtikla);
                                stmt.setString(2, model);
                                stmt.setString(3, dimenzije);
                                stmt.setString(4, tip);
                                stmt.setString(5, cijena);
                                stmt.setString(6, idDo);
                                stmt.execute();
                                conn.close();
                                
                                textFieldSifra_artikla.setText("");
                                textFieldModel.setText("");
                                textFieldDimenzije.setText("");
                                textFieldTip.setText("");
                                textFieldCijena.setText("");
                                comboBoxDobavljac.setSelectedIndex(0);
                                
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null,
                                    ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                            }
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
