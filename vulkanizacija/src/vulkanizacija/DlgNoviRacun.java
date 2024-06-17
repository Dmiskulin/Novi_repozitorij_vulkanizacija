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

public class DlgNoviRacun extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField textFieldBroj;
    private JTextField textFieldDatum;
    private JComboBox<String> comboBoxDjelatnik;
    private JComboBox<String> comboBoxPlacanje;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DlgNoviRacun dialog = new DlgNoviRacun();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DlgNoviRacun() {
        setTitle("Novi račun");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Broj računa");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel.setBounds(23, 25, 95, 20);
        contentPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Djelatnik");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(23, 55, 95, 20);
        contentPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Vrsta plaćanja");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_2.setBounds(23, 85, 95, 20);
        contentPanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Datum");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_3.setBounds(23, 115, 95, 20);
        contentPanel.add(lblNewLabel_3);

        textFieldBroj = new JTextField();
        textFieldBroj.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldBroj.setBounds(190, 25, 100, 20);
        contentPanel.add(textFieldBroj);
        textFieldBroj.setColumns(10);

        comboBoxPlacanje = new JComboBox<>();
        comboBoxPlacanje.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBoxPlacanje.setBounds(190, 85, 200, 20);
        comboBoxPlacanje.addItem("Gotovinsko");
        comboBoxPlacanje.addItem("Kartično");
        contentPanel.add(comboBoxPlacanje);

        textFieldDatum = new JTextField();
        textFieldDatum.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textFieldDatum.setBounds(190, 115, 100, 20);
        contentPanel.add(textFieldDatum);
        textFieldDatum.setColumns(10);

        comboBoxDjelatnik = new JComboBox<>();
        comboBoxDjelatnik.setBounds(190, 55, 200, 20);
        contentPanel.add(comboBoxDjelatnik);
        
        JButton btnNewButton = new JButton("Dodaj artikle");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DlgArtikliRacuna dlg = new DlgArtikliRacuna();
				dlg.setVisible(true);
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton.setBounds(155, 181, 115, 25);
        contentPanel.add(btnNewButton);

        // COMBO BOX
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
                "user=dmiskulin&password=11");
            String sql = "SELECT IdDj, ime, prezime FROM Djelatnik";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idDj = rs.getInt("IdDj");
                String ime = rs.getString("ime");
                String prezime = rs.getString("prezime");
                comboBoxDjelatnik.addItem(idDj + " - " + ime + " " + prezime);
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

            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String brojRacuna = textFieldBroj.getText();
                    String vrstaPlacanja = (String) comboBoxPlacanje.getSelectedItem();
                    String datum = textFieldDatum.getText();
                    String djelatnik = (String) comboBoxDjelatnik.getSelectedItem();

                    if (djelatnik != null) {
                        String[] parts = djelatnik.split(" - ");
                        String idDj = parts[0];

                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                            Connection conn = DriverManager.getConnection(
                                "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
                                "user=dmiskulin&password=11");
                            String sql = "INSERT INTO Racun (Broj_racuna, IdDj, Datum_racuna, Vrsta_placanja) VALUES (?, ?, ?, ?)";
                            PreparedStatement stmt = conn.prepareStatement(sql);
                            stmt.setString(1, brojRacuna);
                            stmt.setString(2, idDj);
                            stmt.setString(3, datum);
                            stmt.setString(4, vrstaPlacanja);
                            stmt.execute();
                            conn.close();

                            textFieldBroj.setText("");
                            textFieldDatum.setText("");
                            comboBoxDjelatnik.setSelectedIndex(0);
                            comboBoxPlacanje.setSelectedIndex(0);

                            JOptionPane.showMessageDialog(null,
                                "Račun uspješno spremljen!", "Informacija",
                                JOptionPane.INFORMATION_MESSAGE);

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
