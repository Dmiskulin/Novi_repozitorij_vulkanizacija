package vulkanizacija;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;

public class DlgPopisArtikala extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            DlgPopisArtikala dialog = new DlgPopisArtikala();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public DlgPopisArtikala() {
        setTitle("Popis artikala");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        
        // Create the list model and JList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        
        // Add the list to a scroll pane
        JScrollPane scrollPane = new JScrollPane(list);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load the data from the database and populate the list
        loadArtikli(listModel);
        
        // Create the button panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> dispose());
            buttonPane.add(closeButton);
        }
    }
    
    private void loadArtikli(DefaultListModel<String> listModel) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://ucka.veleri.hr/dmiskulin?user=dmiskulin&password=11");

            String sql = "SELECT Sifra_artikla, Model, Dimenzije, Tip, Cijena, idDo FROM Artikli_skl";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                int sifraArtikla = rs.getInt("Sifra_artikla");
                String model = rs.getString("Model");
                String dimenzije = rs.getString("Dimenzije");
                String tip = rs.getString("Tip");
                double cijena = rs.getDouble("Cijena");
                int idDo = rs.getInt("idDo");

                String item = String.format("Šifra: %d, Model: %s, Dimenzije: %s, Tip: %s, Cijena: %.2f, ID dobavljača: %d",
                                            sifraArtikla, model, dimenzije, tip, cijena, idDo);
                listModel.addElement(item);
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
