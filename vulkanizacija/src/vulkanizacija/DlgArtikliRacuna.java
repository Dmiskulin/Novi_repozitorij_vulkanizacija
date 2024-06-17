package vulkanizacija2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.EmptyBorder;

public class DlgArtikliRacuna extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JComboBox<Integer> comboBoxBrojRacuna;
    private JList<String> listArtikli;
    private JTextField textFieldSifraArtikla;
    private JTextField textFieldKolicina;
    private JTextField textFieldCijena;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DlgArtikliRacuna dialog = new DlgArtikliRacuna();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DlgArtikliRacuna() {
        setTitle("Artikli raÄŤuna");
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblBrojRacuna = new JLabel("Broj raÄŤuna:");
        lblBrojRacuna.setBounds(20, 20, 100, 20);
        contentPanel.add(lblBrojRacuna);

        comboBoxBrojRacuna = new JComboBox<>();
        comboBoxBrojRacuna.setBounds(130, 20, 150, 20);
        contentPanel.add(comboBoxBrojRacuna);

        JLabel lblPopisArtikala = new JLabel("Popis artikala:");
        lblPopisArtikala.setBounds(20, 60, 150, 20);
        contentPanel.add(lblPopisArtikala);

        listArtikli = new JList<>();
        listArtikli.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listArtikli.setBounds(20, 90, 350, 200);
        contentPanel.add(listArtikli);

        JLabel lblSifraArtikla = new JLabel("Ĺ ifra artikla:");
        lblSifraArtikla.setBounds(380, 90, 70, 20);
        contentPanel.add(lblSifraArtikla);

        textFieldSifraArtikla = new JTextField();
        textFieldSifraArtikla.setBounds(450, 90, 100, 20);
        contentPanel.add(textFieldSifraArtikla);
        textFieldSifraArtikla.setColumns(10);

        JLabel lblKolicina = new JLabel("KoliÄŤina:");
        lblKolicina.setBounds(380, 120, 70, 20);
        contentPanel.add(lblKolicina);

        textFieldKolicina = new JTextField("1"); // PoÄŤetna vrijednost koliÄŤine postavljena na 1
        textFieldKolicina.setBounds(450, 120, 100, 20);
        contentPanel.add(textFieldKolicina);
        textFieldKolicina.setColumns(10);

        JLabel lblCijena = new JLabel("Cijena:");
        lblCijena.setBounds(380, 150, 70, 20);
        contentPanel.add(lblCijena);

        textFieldCijena = new JTextField();
        textFieldCijena.setBounds(450, 150, 100, 20);
        contentPanel.add(textFieldCijena);
        textFieldCijena.setColumns(10);

        // Load bill numbers into the comboBoxBrojRacuna
        loadBrojeviRacuna();

        // Load items into the listArtikli
        loadArtikli();

        // Listener for list selection to update Ĺ ifra artikla and Cijena
        listArtikli.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = listArtikli.getSelectedValue();
                    if (selectedValue != null) {
                        String[] parts = selectedValue.split(" - ");
                        String sifraArtikla = parts[0]; // Prva komponenta je Ĺˇifra artikla
                        String cijenaArtiklaStr = parts[parts.length - 1]; // Zadnja komponenta je cijena artikla
                        try {
                            double cijenaArtikla = Double.parseDouble(cijenaArtiklaStr);
                            textFieldSifraArtikla.setText(sifraArtikla);
                            updateCijena(cijenaArtikla); // AĹľuriranje cijene na temelju odabranog artikla
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Neispravni podaci o artiklu",
                                    "GreĹˇka", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        textFieldKolicina.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedValue = listArtikli.getSelectedValue();
                if (selectedValue != null) {
                    String[] parts = selectedValue.split(" - ");
                    String cijenaArtiklaStr = parts[parts.length - 1]; // Zadnja komponenta je cijena artikla
                    try {
                        double cijenaArtikla = Double.parseDouble(cijenaArtiklaStr);
                        updateCijena(cijenaArtikla); // AĹľuriranje cijene na temelju koliÄŤine
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Neispravni podaci o artiklu",
                                "GreĹˇka", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                spremiArtiklNaRacun();
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

    private void loadBrojeviRacuna() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
                            "user=dmiskulin&password=11");
            String sql = "SELECT Broj_racuna FROM Racun";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int brojRacuna = rs.getInt("Broj_racuna");
                comboBoxBrojRacuna.addItem(brojRacuna);
            }
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(), "GreĹˇka", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadArtikli() {
        DefaultListModel<String> model = new DefaultListModel<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
                            "user=dmiskulin&password=11");
            String sql = "SELECT Sifra_artikla, Model, Dimenzije, Tip, Cijena FROM Artikli_skl";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String sifra = rs.getString("Sifra_artikla");
                String modelArtikla = rs.getString("Model");
                String dimenzije = rs.getString("Dimenzije");
                String tip = rs.getString("Tip");
                double cijena = rs.getDouble("Cijena");
                model.addElement(sifra + " - " + modelArtikla + " - " + dimenzije + " - " + tip + " - " + cijena);
            }
            listArtikli.setModel(model);
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(), "GreĹˇka", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCijena(double cijenaArtikla) {
        try {
            int kolicina = Integer.parseInt(textFieldKolicina.getText().trim());
            double cijena = kolicina * cijenaArtikla;
            textFieldCijena.setText(String.valueOf(cijena));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Neispravna vrijednost za koliÄŤinu",
                    "GreĹˇka", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void spremiArtiklNaRacun() {
        try {
            int brojRacuna = (int) comboBoxBrojRacuna.getSelectedItem();
            int sifraArtikla = Integer.parseInt(textFieldSifraArtikla.getText().trim());
            int kolicina = Integer.parseInt(textFieldKolicina.getText().trim());
            double cijenaArtikla = Double.parseDouble(textFieldCijena.getText().trim());

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
                            "user=dmiskulin&password=11");

            String sql = "INSERT INTO Artikli_na_racunu (Broj_racuna, Sifra_artikla, Kolicina, Cijena_artikla) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, brojRacuna);
            stmt.setInt(2, sifraArtikla);
            stmt.setInt(3, kolicina);
            stmt.setDouble(4, cijenaArtikla);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Artikl uspjeĹˇno spremljen na raÄŤun!",
                    "Info", JOptionPane.INFORMATION_MESSAGE);

            conn.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Neispravni podaci za spremanje artikla",
                    "GreĹˇka", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(), "GreĹˇka", JOptionPane.ERROR_MESSAGE);
        }
    }
}

