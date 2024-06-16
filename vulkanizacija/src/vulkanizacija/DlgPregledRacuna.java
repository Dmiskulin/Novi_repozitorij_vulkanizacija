package vulkanizacija;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class DlgPregledRacuna extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JList<String> listRacuni;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DlgPregledRacuna dialog = new DlgPregledRacuna();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DlgPregledRacuna() {
        setTitle("Pregled računa");
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 564, 290);
        contentPanel.add(scrollPane);

        listRacuni = new JList<>();
        scrollPane.setViewportView(listRacuni);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        okButton.addActionListener(e -> dispose());
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPane.add(cancelButton);

        // Load racuni and artikli data
        loadRacuniArtikli();
    }

    private void loadRacuniArtikli() {
        DefaultListModel<String> model = new DefaultListModel<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://ucka.veleri.hr/dmiskulin?" +
                            "user=dmiskulin&password=11");

            String sql = "SELECT r.Broj_racuna, r.Datum_racuna, r.Vrsta_placanja, a.Sifra_artikla, a.Kolicina, a.Cijena_artikla " +
                    "FROM Racun r " +
                    "JOIN Artikli_na_racunu a ON r.Broj_racuna = a.Broj_racuna " +
                    "ORDER BY r.Broj_racuna";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            Map<Integer, Double> ukupniIznosi = new HashMap<>();
            int currentBrojRacuna = -1;

            while (rs.next()) {
                int brojRacuna = rs.getInt("Broj_racuna");
                String datumRacuna = rs.getString("Datum_racuna");
                String vrstaPlacanja = rs.getString("Vrsta_placanja");
                int sifraArtikla = rs.getInt("Sifra_artikla");
                int kolicina = rs.getInt("Kolicina");
                double cijenaArtikla = rs.getDouble("Cijena_artikla");
                double ukupnaCijena = kolicina * cijenaArtikla;

                // Dodavanje ukupne cijene artikla za svaki račun
                ukupniIznosi.put(brojRacuna, ukupniIznosi.getOrDefault(brojRacuna, 0.0) + ukupnaCijena);

                // Provjera je li broj računa promijenjen kako bi dodali prazan red
                if (currentBrojRacuna != -1 && currentBrojRacuna != brojRacuna) {
                    model.addElement("Ukupan iznos za račun " + currentBrojRacuna + ": " + ukupniIznosi.get(currentBrojRacuna) + " HRK");
                    model.addElement(""); // Dodavanje praznog reda između različitih računa
                }
                currentBrojRacuna = brojRacuna;

                String entry = "Broj računa: " + brojRacuna + ", Datum: " + datumRacuna + ", Vrsta plaćanja: " + vrstaPlacanja +
                        ", Šifra artikla: " + sifraArtikla + ", Količina: " + kolicina + ", Cijena: " + cijenaArtikla;
                model.addElement(entry);
            }

            // Dodavanje ukupnog iznosa za zadnji račun
            if (currentBrojRacuna != -1) {
                model.addElement("Ukupan iznos za račun " + currentBrojRacuna + ": " + ukupniIznosi.get(currentBrojRacuna) + " HRK");
                model.addElement(""); // Dodavanje praznog reda nakon ukupnog iznosa za zadnji račun
            }

            listRacuni.setModel(model);
            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
