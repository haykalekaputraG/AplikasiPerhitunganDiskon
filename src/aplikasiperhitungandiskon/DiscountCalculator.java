package aplikasiperhitungandiskon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

public class DiscountCalculator extends JFrame {
    private JTextField tfHargaAwal, tfHargaAkhir, tfPenghematan;
    private JComboBox<String> cbPersen;
    private JButton btnHitung;

    public DiscountCalculator() {
        super("Aplikasi Perhitungan Diskon");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Komponen
        JLabel lbHarga = new JLabel("Harga Awal (Rp):");
        tfHargaAwal = new JTextField(12);

        JLabel lbPersen = new JLabel("Persentase Diskon:");
        String[] pilihan = {"0%", "5%", "10%", "15%", "20%", "25%", "30%", "40%", "50%"};
        cbPersen = new JComboBox<>(pilihan);

        btnHitung = new JButton("Hitung");

        JLabel lbHasil = new JLabel("Harga Akhir:");
        tfHargaAkhir = new JTextField(12);
        tfHargaAkhir.setEditable(false);

        JLabel lbHemat = new JLabel("Jumlah Penghematan:");
        tfPenghematan = new JTextField(12);
        tfPenghematan.setEditable(false);

        // Layout menggunakan GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; panel.add(lbHarga, c);
        c.gridx = 1; panel.add(tfHargaAwal, c);

        c.gridx = 0; c.gridy = 1; panel.add(lbPersen, c);
        c.gridx = 1; panel.add(cbPersen, c);

        c.gridx = 0; c.gridy = 2; panel.add(btnHitung, c);

        c.gridx = 0; c.gridy = 3; panel.add(lbHasil, c);
        c.gridx = 1; panel.add(tfHargaAkhir, c);

        c.gridx = 0; c.gridy = 4; panel.add(lbHemat, c);
        c.gridx = 1; panel.add(tfPenghematan, c);

        add(panel);

        // Action listener
        btnHitung.addActionListener(e -> hitungDiskon());
        // Optional: hitung juga ketika pemilihan persen berubah
        cbPersen.addActionListener(e -> {
            // jika ingin langsung menghitung tanpa klik tombol, aktifkan baris di bawah:
            // hitungDiskon();
        });
    }

    private void hitungDiskon() {
        String hargaText = tfHargaAwal.getText().trim();
        if (hargaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan harga awal terlebih dahulu.", "Input kosong", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hapus karakter non-digit (mis. titik, koma, spasi) jika user mengetik formatting
        String raw = hargaText.replaceAll("[^0-9]", "");
        if (raw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harga tidak valid. Masukkan angka saja.", "Input salah", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double hargaAwal = Double.parseDouble(raw);
            if (hargaAwal < 0) {
                JOptionPane.showMessageDialog(this, "Harga tidak boleh negatif.", "Input salah", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String persenStr = (String) cbPersen.getSelectedItem(); // contoh "10%"
            int persen = Integer.parseInt(persenStr.replace("%", ""));

            double penghematan = hargaAwal * persen / 100.0;
            double hargaAkhir = hargaAwal - penghematan;

            // Format mata uang (Indonesia)
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            tfPenghematan.setText(nf.format(penghematan));
            tfHargaAkhir.setText(nf.format(hargaAkhir));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengolah angka.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Jalankan GUI di EDT
        SwingUtilities.invokeLater(() -> {
            // Set look and feel system (opsional)
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new DiscountCalculator().setVisible(true);
        });
    }
}
