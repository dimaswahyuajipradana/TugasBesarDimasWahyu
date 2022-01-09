package views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class transaksiBarangMasuk extends javax.swing.JDialog {

    public final Connection conn = new koneksi().connect();

    private DefaultTableModel tabmode;
    private DefaultTableModel tabmode2;
    private DefaultTableModel tabmode3;

    JasperReport JasRep;
    JasperPrint JasPri;
    Map param = new HashMap();
    JasperDesign JasDes;

    // frame maks dan geser panel
    static boolean maximixed = true;
    int xMouse;
    int yMouse;

    private void aktif() {
        txtidbrgmasuk.setEnabled(true);
        txtsupplier.setEnabled(true);
        txtkdbrg.setEnabled(true);
        txtnmbrg.setEnabled(true);
        txtjumlahbrg.setEnabled(true);
        txtketerangan.setEnabled(true);
    }

    protected void kosong() {
        txtidbrgmasuk.setText(null);
        txtsupplier.setText(null);
        txtkdbrg.setText(null);
        txtnmbrg.setText(null);
        txtjumlahbrg.setText(null);
        txtketerangan.setText(null);
    }

    protected void kosong2() {
        txtkdbrg.setText(null);
        txtnmbrg.setText(null);
        txtjumlahbrg.setText(null);
        txtketerangan.setText(null);
    }

    public void noTable() {
        int Baris = tabmode.getRowCount();
        for (int a = 0; a < Baris; a++) {
            String nomor = String.valueOf(a + 1);
            tabmode.setValueAt(nomor + ".", a, 0);
        }
    }

    public void tanggal() {
        Date tgl = new Date();
        btnPilihTanggal.setDate(tgl);
    }

    private void autoIdBM() {
        try {
            Connection con = new koneksi().connect();
            java.sql.Statement stat = con.createStatement();
            String sql = "select max(right (id_bm,6)) as no from tb_brg_masuk";
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                if (res.first() == false) {
                    txtidbrgmasuk.setText("BM-000001");
                } else {
                    res.last();
                    int aut_id = res.getInt(1) + 1;
                    String no = String.valueOf(aut_id);
                    int no_jual = no.length();
                    // mengatur jumlah 0
                    for (int j = 0; j < 6 - no_jual; j++) {
                        no = "0" + no;
                    }
                    txtidbrgmasuk.setText("BM-" + no);
                }
            }
            res.close();
            stat.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
        }
    }

    private void autoIdBM_DT() {
        try {
            Connection con = new koneksi().connect();
            java.sql.Statement stat = con.createStatement();
            String sql_dt = "select max(right (id_detail_bm,4)) as no from tb_detail_brg_masuk";
            ResultSet res_dt = stat.executeQuery(sql_dt);
            while (res_dt.next()) {
                if (res_dt.first() == false) {
                    txtIdDetailBarangMasuk.setText("DTBM-0001");
                } else {
                    res_dt.last();
                    int aut_id_dt = res_dt.getInt(1) + 1;
                    String no_dt = String.valueOf(aut_id_dt);
                    int no_jual_dt = no_dt.length();
                    // mengatur jumlah 0
                    for (int j = 0; j < 4 - no_jual_dt; j++) {
                        no_dt = "0" + no_dt;
                    }
                    txtIdDetailBarangMasuk.setText("DTBM-" + no_dt);
                }
            }
            res_dt.close();
            stat.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan");
        }
    }
        
    public void dataTable() {
        Object[] Baris = {"No", "Tanggal","ID Detail BM", "ID BM", "Principal", "Kode Barang", "Nama Barang", "Jumlah", "Keterangan"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelBarangMasuk.setModel(tabmode);
        String sql = "select * from tb_detail_brg_masuk order tanggal asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String tanggal = hasil.getString("tanggal");
                String id_detail_bm = hasil.getString("id_detail_bm");
                String id_bm = hasil.getString("id_bm");
                String supplier = hasil.getString("supplier");
                String kode_brg = hasil.getString("kode_brg");
                String nama_brg = hasil.getString("nama_brg");
                String jumlah = hasil.getString("jumlah");
                String keterangan = hasil.getString("keterangan");
                String[] data = {"", tanggal, id_detail_bm, id_bm, supplier, kode_brg, nama_brg, jumlah, keterangan};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {
        }
    }

    public void dataTable2() {
        Object[] Baris = {"Kode Barang", "Nama Barang"};
        tabmode2 = new DefaultTableModel(null, Baris);
        tabelBarang.setModel(tabmode2);
        String sql = "select * from tb_barang order by kode_brg asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String kode_brg = hasil.getString("kode_brg");
                String nama_brg = hasil.getString("nama_brg");
                String[] data = {kode_brg, nama_brg};
                tabmode2.addRow(data);
            }
        } catch (Exception e) {
        }
    }

    public void dataTable3() {
        Object[] Baris = {"Kode Principal", "Nama Principal"};
        tabmode3 = new DefaultTableModel(null, Baris);
        tabelSupplier.setModel(tabmode3);
        String sql = "select * from tb_supplier order by kode_supplier asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String kode_supplier = hasil.getString("kode_supplier");
                String nama_supplier = hasil.getString("nama_supplier");
                String[] data = {kode_supplier, nama_supplier};
                tabmode3.addRow(data);
            }
        } catch (Exception e) {
        }
    }

    public void lebarKolom() {
        TableColumn column;
        tabelBarangMasuk.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tabelBarangMasuk.getColumnModel().getColumn(0);
        column.setPreferredWidth(40);
        column = tabelBarangMasuk.getColumnModel().getColumn(1);
        column.setPreferredWidth(100);
        column = tabelBarangMasuk.getColumnModel().getColumn(2);
        column.setPreferredWidth(100);
        column = tabelBarangMasuk.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = tabelBarangMasuk.getColumnModel().getColumn(4);
        column.setPreferredWidth(200);
        column = tabelBarangMasuk.getColumnModel().getColumn(5);
        column.setPreferredWidth(100);
        column = tabelBarangMasuk.getColumnModel().getColumn(6);
        column.setPreferredWidth(250);
        column = tabelBarangMasuk.getColumnModel().getColumn(7);
        column.setPreferredWidth(70);
        column = tabelBarangMasuk.getColumnModel().getColumn(8);
        column.setPreferredWidth(300);
    }

    public void lebarKolom2() {
        TableColumn column2;
        tabelBarang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column2 = tabelBarang.getColumnModel().getColumn(0);
        column2.setPreferredWidth(80);
        column2 = tabelBarang.getColumnModel().getColumn(1);
        column2.setPreferredWidth(215);
    }

    public void lebarKolom3() {
        TableColumn column3;
        tabelSupplier.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column3 = tabelSupplier.getColumnModel().getColumn(0);
        column3.setPreferredWidth(90);
        column3 = tabelSupplier.getColumnModel().getColumn(1);
        column3.setPreferredWidth(205);
    }

    public void pencarian(String sql) {
        Object[] Baris = {"No", "Tanggal", "ID Detail BM" ,"ID BM", "Principal", "Kode Barang", "Nama Barang", "Jumlah", "Keterangan"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelBarangMasuk.setModel(tabmode);
        int brs = tabelBarangMasuk.getRowCount();
        for (int i = 0; 1 < brs; i++) {
            tabmode.removeRow(1);
        }
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String tanggal = hasil.getString("tanggal");
                String id_detail_bm = hasil.getString("id_detail_bm");
                String id_bm = hasil.getString("id_bm");
                String supplier = hasil.getString("supplier");
                String kode_brg = hasil.getString("kode_brg");
                String nama_brg = hasil.getString("nama_brg");
                String jumlah = hasil.getString("jumlah");
                String keterangan = hasil.getString("keterangan");
                String[] data = {"", tanggal, id_detail_bm,id_bm ,supplier, kode_brg, nama_brg, jumlah, keterangan};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e) {

        }
    }

    public void pencarian2(String sql) {
        Object[] Baris2 = {"Kode Barang", "Nama Barang"};
        tabmode2 = new DefaultTableModel(null, Baris2);
        tabelBarang.setModel(tabmode2);
        int brs = tabelBarang.getRowCount();
        for (int i = 0; 1 < brs; i++) {
            tabmode2.removeRow(1);
        }
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String kode_brg = hasil.getString("kode_brg");
                String nama_brg = hasil.getString("nama_brg");
                String[] data = {kode_brg, nama_brg};
                tabmode2.addRow(data);
            }
        } catch (Exception e) {

        }
    }

    public void pencarian3(String sql) {
        Object[] Baris = {"Kode Principal", "Nama Principal"};
        tabmode3 = new DefaultTableModel(null, Baris);
        tabelSupplier.setModel(tabmode3);
        int brs = tabelSupplier.getRowCount();
        for (int i = 0; 1 < brs; i++) {
            tabmode3.removeRow(1);
        }
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String kode_supplier = hasil.getString("kode_supplier");
                String nama_supplier = hasil.getString("nama_supplier");
                String[] data = {kode_supplier, nama_supplier};
                tabmode3.addRow(data);
            }
        } catch (Exception e) {

        }
    }

    /**
     * Creates new form dataBarang
     */
    public transaksiBarangMasuk(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        aktif();
        autoIdBM();
        autoIdBM_DT();
        dataTable();
        dataTable2();
        dataTable3();
        tanggal();
        lebarKolom();
        lebarKolom2();
        lebarKolom3();
        txtidbrgmasuk.requestFocus();
        txtIdDetailBarangMasuk.setVisible(false);
        Locale local = new Locale("id","ID");
        Locale.setDefault(local);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelHeader = new javax.swing.JPanel();
        labelNama = new javax.swing.JLabel();
        lbTanggal = new javax.swing.JLabel();
        labelKodeBarang = new javax.swing.JLabel();
        labelkodebrg = new javax.swing.JLabel();
        labeljumlah = new javax.swing.JLabel();
        labelKeterangan = new javax.swing.JLabel();
        txtidbrgmasuk = new javax.swing.JTextField();
        btnPilihTanggal = new com.toedter.calendar.JDateChooser();
        txtkdbrg = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBarangMasuk = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBersih = new javax.swing.JButton();
        btncari = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        txtjumlahbrg = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtketerangan = new javax.swing.JTextArea();
        labelsupplier = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtsupplier = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelBarang = new javax.swing.JTable();
        txtcaribrg = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        labelnamabrg = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtnmbrg = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelSupplier = new javax.swing.JTable();
        txtcarisupplier = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        txtIdDetailBarangMasuk = new javax.swing.JTextField();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        panelHeader.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelHeader.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelHeaderMouseDragged(evt);
            }
        });
        panelHeader.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelHeaderMousePressed(evt);
            }
        });

        labelNama.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelNama.setText("Transaksi Barang Masuk PT.Rajawali Nusindo");

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(277, 277, 277)
                .addComponent(labelNama, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lbTanggal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTanggal.setText("Tanggal");
        lbTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelKodeBarang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelKodeBarang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelKodeBarang.setText("ID Barang Masuk");
        labelKodeBarang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelkodebrg.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelkodebrg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelkodebrg.setText("Kode Barang");
        labelkodebrg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labeljumlah.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labeljumlah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labeljumlah.setText("Jumlah");
        labeljumlah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelKeterangan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelKeterangan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelKeterangan.setText("Keterangan");
        labelKeterangan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtidbrgmasuk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidbrgmasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtidbrgmasukKeyPressed(evt);
            }
        });

        btnPilihTanggal.setDateFormatString("dd-MM-yyyy");
        btnPilihTanggal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPilihTanggal.setMaximumSize(new java.awt.Dimension(2147400000, 2147400000));
        btnPilihTanggal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPilihTanggalMouseClicked(evt);
            }
        });
        btnPilihTanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPilihTanggalKeyPressed(evt);
            }
        });

        txtkdbrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtkdbrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkdbrgKeyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(":");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(":");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText(":");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText(":");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText(":");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        tabelBarangMasuk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabelBarangMasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        tabelBarangMasuk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelBarangMasuk.setRowHeight(30);
        tabelBarangMasuk.setRowMargin(2);
        tabelBarangMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarangMasukMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBarangMasuk);

        btnSimpan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSimpan.setContentAreaFilled(false);
        btnSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpan.setOpaque(true);
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSimpanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSimpanMouseExited(evt);
            }
        });
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnUbah.setText("Edit");
        btnUbah.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUbah.setContentAreaFilled(false);
        btnUbah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUbah.setOpaque(true);
        btnUbah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUbahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUbahMouseExited(evt);
            }
        });
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnHapus.setContentAreaFilled(false);
        btnHapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapus.setOpaque(true);
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHapusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHapusMouseExited(evt);
            }
        });
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBersih.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnBersih.setText("Clear");
        btnBersih.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBersih.setContentAreaFilled(false);
        btnBersih.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBersih.setOpaque(true);
        btnBersih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBersihMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBersihMouseExited(evt);
            }
        });
        btnBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBersihActionPerformed(evt);
            }
        });

        btncari.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btncari.setText("Cari");
        btncari.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btncari.setContentAreaFilled(false);
        btncari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncari.setOpaque(true);
        btncari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btncariMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btncariMouseExited(evt);
            }
        });

        txtcari.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariKeyTyped(evt);
            }
        });

        txtjumlahbrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtjumlahbrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtjumlahbrgKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtjumlahbrgKeyTyped(evt);
            }
        });

        txtketerangan.setColumns(20);
        txtketerangan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtketerangan.setRows(5);
        jScrollPane2.setViewportView(txtketerangan);

        labelsupplier.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelsupplier.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelsupplier.setText("Principal");
        labelsupplier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText(":");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        txtsupplier.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtsupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtsupplierKeyPressed(evt);
            }
        });

        tabelBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabelBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tabelBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelBarang.setRowHeight(30);
        tabelBarang.setRowMargin(2);
        tabelBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarangMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelBarang);

        txtcaribrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcaribrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcaribrgKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Barang");
        jLabel2.setOpaque(true);

        labelnamabrg.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelnamabrg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelnamabrg.setText("Nama Barang");
        labelnamabrg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText(":");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        txtnmbrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnmbrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnmbrgKeyPressed(evt);
            }
        });

        tabelSupplier.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabelSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tabelSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelSupplier.setRowHeight(30);
        tabelSupplier.setRowMargin(2);
        tabelSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSupplierMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelSupplier);

        txtcarisupplier.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcarisupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcarisupplierKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Principal");
        jLabel3.setOpaque(true);

        btnTambah.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTambah.setText("Tambah ");
        btnTambah.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTambah.setContentAreaFilled(false);
        btnTambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambah.setOpaque(true);
        btnTambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTambahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTambahMouseExited(evt);
            }
        });
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnCetak.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnCetak.setText("Print");
        btnCetak.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCetak.setContentAreaFilled(false);
        btnCetak.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCetak.setOpaque(true);
        btnCetak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCetakMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCetakMouseExited(evt);
            }
        });
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        txtIdDetailBarangMasuk.setEditable(false);
        txtIdDetailBarangMasuk.setBackground(new java.awt.Color(255, 255, 255));
        txtIdDetailBarangMasuk.setBorder(null);

        btnClose.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnClose.setText("EXIT");
        btnClose.setContentAreaFilled(false);
        btnClose.setOpaque(true);
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCloseMouseExited(evt);
            }
        });
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelkodebrg, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labeljumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelnamabrg, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtIdDetailBarangMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtcari)
                            .addComponent(btnPilihTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtkdbrg)
                            .addComponent(txtjumlahbrg)
                            .addComponent(txtidbrgmasuk)
                            .addComponent(jScrollPane2)
                            .addComponent(txtsupplier)
                            .addComponent(txtnmbrg))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtcarisupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtcaribrg, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnBersih, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1093, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnPilihTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(txtidbrgmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(txtkdbrg))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnCetak, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                                            .addComponent(txtnmbrg)))
                                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtjumlahbrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labeljumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(txtIdDetailBarangMasuk)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lbTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(labelKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(labelsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(labelkodebrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(5, 5, 5)
                                    .addComponent(labelnamabrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcarisupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcaribrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1104, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCloseMouseEntered

    private void btnCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCloseMouseExited

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void panelHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMouseDragged
        if (maximixed) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            this.setLocation(x - xMouse, y - yMouse);
        }
    }//GEN-LAST:event_panelHeaderMouseDragged

    private void panelHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_panelHeaderMousePressed

    private void btnPilihTanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPilihTanggalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPilihTanggalMouseClicked

    private void btnPilihTanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPilihTanggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPilihTanggalKeyPressed

    private void tabelBarangMasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBarangMasukMouseClicked
        int bar = tabelBarangMasuk.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        String f = tabmode.getValueAt(bar, 5).toString();
        String g = tabmode.getValueAt(bar, 6).toString();
        String h = tabmode.getValueAt(bar, 7).toString();
        String i = tabmode.getValueAt(bar, 8).toString();

        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date dateValue = null;
        try {
            dateValue = date.parse((String) tabelBarangMasuk.getValueAt(bar, 1));
        } catch (ParseException ex) {
            Logger.getLogger(transaksiBarangMasuk.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnPilihTanggal.setDate(dateValue);
        txtIdDetailBarangMasuk.setText(c);
        txtidbrgmasuk.setText(d);
        txtsupplier.setText(e);
        txtkdbrg.setText(f);
        txtnmbrg.setText(g);
        txtjumlahbrg.setText(h);
        txtketerangan.setText(i);
        
    }//GEN-LAST:event_tabelBarangMasukMouseClicked

    private void btnSimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanMouseEntered

    private void btnSimpanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanMouseExited

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (txtidbrgmasuk.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ID BM tidak boleh kosong");
            txtidbrgmasuk.requestFocus();
        } else if (txtsupplier.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Principal tidak boleh kosong");
            txtsupplier.requestFocus();
        } else {
            String sql = "insert into tb_brg_masuk values (?,?,?,?)";
            String tampilan = "dd-MM-yyyy";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tanggal = String.valueOf(fm.format(btnPilihTanggal.getDate()));
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, tanggal.toString());
                stat.setString(2, txtidbrgmasuk.getText());
                stat.setString(3, txtsupplier.getText());
                stat.setString(4, txtketerangan.getText());
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                //            String refresh = "select * from tb_barang";
                kosong();
                autoIdBM();
                autoIdBM_DT();
                dataTable();
                lebarKolom();
                txtidbrgmasuk.setEnabled(true);
                txtsupplier.setEnabled(true);
                txtidbrgmasuk.requestFocus();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan" + e);
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUbahMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUbahMouseEntered

    private void btnUbahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUbahMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUbahMouseExited

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        String sql = "update tb_detail_brg_masuk set tanggal=?,id_detail_bm=?,id_bm=?,supplier=?,kode_brg=?,nama_brg=?,jumlah=?,keterangan=? where id_detail_bm='" + txtIdDetailBarangMasuk.getText() + "'";
        String tampilan = "dd-MM-yyyy";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal = String.valueOf(fm.format(btnPilihTanggal.getDate()));
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tanggal.toString());
            stat.setString(2, txtIdDetailBarangMasuk.getText());
            stat.setString(3, txtidbrgmasuk.getText());
            stat.setString(4, txtsupplier.getText());
            stat.setString(5, txtkdbrg.getText());
            stat.setString(6, txtnmbrg.getText());
            stat.setString(7, txtjumlahbrg.getText());
            stat.setString(8, txtketerangan.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            //            String refresh = "select * from tb_barang";
            kosong();
            autoIdBM();
            autoIdBM_DT();
            dataTable();
            lebarKolom();
            txtidbrgmasuk.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah" + e);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusMouseEntered

    private void btnHapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseExited
       // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusMouseExited

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
      int ok = JOptionPane.showConfirmDialog(null, " Apakah Anda Yakin Ingin "
           + "Menghapus Data", "Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
      if (ok == 0) {
            String sql = "delete from tb_detail_brg_masuk where id_bm='" + txtidbrgmasuk.getText() + "'";
            String sql2 = "delete from tb_brg_masuk where id_bm='" + txtidbrgmasuk.getText() + "'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                PreparedStatement stat2 = conn.prepareStatement(sql2);
                stat2.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                kosong();
                autoIdBM();
                autoIdBM_DT();
                dataTable();
               lebarKolom();
                txtidbrgmasuk.requestFocus();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus" + e);
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBersihMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBersihMouseEntered
       // TODO add your handling code here:
    }//GEN-LAST:event_btnBersihMouseEntered

    private void btnBersihMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBersihMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBersihMouseExited

    private void btnBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBersihActionPerformed
        autoIdBM();
        autoIdBM_DT();
        tanggal();
        txtidbrgmasuk.requestFocus();
        txtsupplier.setText(null);
        txtkdbrg.setText(null);
        txtnmbrg.setText(null);
        txtjumlahbrg.setText(null);
        txtketerangan.setText(null);
    }//GEN-LAST:event_btnBersihActionPerformed

    private void btncariMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncariMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btncariMouseEntered

    private void btncariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncariMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btncariMouseExited

    private void txtidbrgmasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidbrgmasukKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtsupplier.requestFocus();
        }
    }//GEN-LAST:event_txtidbrgmasukKeyPressed

    private void txtkdbrgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkdbrgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtnmbrg.requestFocus();
        }
    }//GEN-LAST:event_txtkdbrgKeyPressed

    private void txtjumlahbrgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahbrgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtketerangan.requestFocus();
        }
    }//GEN-LAST:event_txtjumlahbrgKeyPressed

    private void tabelBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBarangMouseClicked
        int bar = tabelBarang.getSelectedRow();
        String a = tabmode2.getValueAt(bar, 0).toString();
        String b = tabmode2.getValueAt(bar, 1).toString();
        txtkdbrg.setText(a);
        txtnmbrg.setText(b);
        txtjumlahbrg.requestFocus();
    }//GEN-LAST:event_tabelBarangMouseClicked

    private void txtcaribrgKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcaribrgKeyTyped
        String sqlPencarian2 = "select * from tb_barang where kode_brg like '%" + txtcaribrg.getText() + "%' or nama_brg like '%" + txtcaribrg.getText() + "%'";
        pencarian2(sqlPencarian2);
        lebarKolom2();
    }//GEN-LAST:event_txtcaribrgKeyTyped

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        String sqlPencarian = "select * from tb_detail_brg_masuk where id_bm like '%" + txtcari.getText() + "%'"
                + "or id_detail_bm like '%" + txtcari.getText() + "%'"
                + "or nama_brg like '%" + txtcari.getText() + "%'"
                + "or kode_brg like '%" + txtcari.getText() + "%'"
                + "or supplier like '%" + txtcari.getText() + "%'";
        pencarian(sqlPencarian);
        lebarKolom();
    }//GEN-LAST:event_txtcariKeyTyped

    private void txtnmbrgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnmbrgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtjumlahbrg.requestFocus();
        }
    }//GEN-LAST:event_txtnmbrgKeyPressed

    private void tabelSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSupplierMouseClicked
        int bar = tabelSupplier.getSelectedRow();
        String a = tabmode3.getValueAt(bar, 0).toString();
        String b = tabmode3.getValueAt(bar, 1).toString();
        txtsupplier.setText(b);
        txtkdbrg.requestFocus();
    }//GEN-LAST:event_tabelSupplierMouseClicked

    private void txtcarisupplierKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarisupplierKeyTyped
        String sqlPencarian3 = "select * from tb_supplier where kode_supplier like '%" + txtcarisupplier.getText() + "%' or nama_supplier like '%" + txtcarisupplier.getText() + "%'";
        pencarian3(sqlPencarian3);
        lebarKolom3();
    }//GEN-LAST:event_txtcarisupplierKeyTyped

    private void txtsupplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsupplierKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtkdbrg.requestFocus();
        }
    }//GEN-LAST:event_txtsupplierKeyPressed

    private void btnTambahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahMouseEntered

    private void btnTambahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahMouseExited

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if (txtidbrgmasuk.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "ID BM tidak boleh kosong");
            txtidbrgmasuk.requestFocus();
        } else if (txtsupplier.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Principal tidak boleh kosong");
            txtsupplier.requestFocus();
        } else if (txtkdbrg.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Kode Barang tidak boleh kosong");
            txtkdbrg.requestFocus();
        } else if (txtnmbrg.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nama Barang tidak boleh kosong");
            txtnmbrg.requestFocus();
        } else if (txtjumlahbrg.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Jumlah tidak boleh kosong");
            txtjumlahbrg.requestFocus();
        } else {
            String sql = "insert into tb_detail_brg_masuk values (?,?,?,?,?,?,?,?)";
            String tampilan = "dd-MM-yyyy";
            SimpleDateFormat fm = new SimpleDateFormat(tampilan);
            String tanggal = String.valueOf(fm.format(btnPilihTanggal.getDate()));
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, tanggal.toString());
                stat.setString(2, txtIdDetailBarangMasuk.getText());
                stat.setString(3, txtidbrgmasuk.getText());
                stat.setString(4, txtsupplier.getText());
                stat.setString(5, txtkdbrg.getText());
                stat.setString(6, txtnmbrg.getText());
                stat.setString(7, txtjumlahbrg.getText());
                stat.setString(8, txtketerangan.getText());
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                //            String refresh = "select * from tb_barang";
                //autoIdBM();
                autoIdBM_DT();
                kosong2();
                dataTable();
                lebarKolom();
                txtidbrgmasuk.setEnabled(false);
                txtsupplier.setEnabled(false);
                txtkdbrg.requestFocus();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan" + e);
            }
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnCetakMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCetakMouseEntered

    private void btnCetakMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCetakMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCetakMouseExited

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed

    }//GEN-LAST:event_btnCetakActionPerformed

    private void txtjumlahbrgKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahbrgKeyTyped
        char enter=evt.getKeyChar();
        if(!(Character.isDigit(enter)))
        {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Masukan Hanya Angka 0-9", "Input Qty", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtjumlahbrgKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(transaksiBarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksiBarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksiBarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksiBarangMasuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                transaksiBarangMasuk dialog = new transaksiBarangMasuk(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBersih;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHapus;
    private com.toedter.calendar.JDateChooser btnPilihTanggal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton btncari;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel labelKeterangan;
    private javax.swing.JLabel labelKodeBarang;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labeljumlah;
    private javax.swing.JLabel labelkodebrg;
    private javax.swing.JLabel labelnamabrg;
    private javax.swing.JLabel labelsupplier;
    private javax.swing.JLabel lbTanggal;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTable tabelBarang;
    private javax.swing.JTable tabelBarangMasuk;
    private javax.swing.JTable tabelSupplier;
    private javax.swing.JTextField txtIdDetailBarangMasuk;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcaribrg;
    private javax.swing.JTextField txtcarisupplier;
    private javax.swing.JTextField txtidbrgmasuk;
    private javax.swing.JTextField txtjumlahbrg;
    private javax.swing.JTextField txtkdbrg;
    private javax.swing.JTextArea txtketerangan;
    private javax.swing.JTextField txtnmbrg;
    private javax.swing.JTextField txtsupplier;
    // End of variables declaration//GEN-END:variables
}
