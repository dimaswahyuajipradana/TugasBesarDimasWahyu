package views;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import koneksi.koneksi;
import static views.menuUtama.maximixed;
/**
 *
 * 
 */
public class dataBarang extends javax.swing.JDialog {
    
    public final Connection conn = new koneksi().connect();

    private DefaultTableModel tabmode;
    private DefaultTableModel tabmode2;
    
   
    static boolean minimixed = true;
    int xMouse;
    int yMouse;
    
    private void aktif(){
        txtkdbrg.setEnabled(true);
        txtnamabrg.setEnabled(true);
        txtkategoribrg.setEnabled(true);
        txtjumlah.setEnabled(true);
        txtketerangan.setEnabled(true);
    }
    
    protected void kosong(){
        txtkdbrg.setText(null);
        txtnamabrg.setText(null);
        txtkategoribrg.setText(null);
        txtjumlah.setText(null);
        txtketerangan.setText(null);
    }
    
    public void noTable(){
        int Baris = tabmode.getRowCount();
        for (int a=0; a<Baris; a++)
        {
            String nomor = String.valueOf(a+1);
            tabmode.setValueAt(nomor +".",a,0);
        }
    }
    
    public void tanggal(){
        Date tgl = new Date();
        btnpilihtgl.setDate(tgl);
    }
    
    
    public void dataTable(){
        Object[] Baris = {"No","Tanggal","Kode Barang","Nama Barang","Kategori","Jumlah","Keterangan"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelbarang.setModel(tabmode);
        String sql = "select * from tb_barang order by kode_brg asc";
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String tanggal = hasil.getString("tanggal");
                String kode_brg = hasil.getString("kode_brg");
                String nama_brg = hasil.getString("nama_brg");
                String kategori = hasil.getString("kategori");
                String jumlah = hasil.getString("jumlah");
                String keterangan = hasil.getString("keterangan");
                String[] data = {"",tanggal,kode_brg,nama_brg,kategori,jumlah,keterangan};
                tabmode.addRow(data);
                noTable();
            }
        } catch (Exception e){
        }
    }
    
    public void dataTable2(){
        Object[] Baris2 = {"Kode Kategori","Nama Kategori"};
        tabmode2 = new DefaultTableModel(null, Baris2);
        tabelkategori.setModel(tabmode2);
        String sql = "select * from tb_kategori order by kode_kategori asc";
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String kode_kategori = hasil.getString("kode_kategori");
                String nama_kategori = hasil.getString("nama_kategori");
                String[] data = {kode_kategori,nama_kategori};
                tabmode2.addRow(data);
            }
        } catch (Exception e){
        }
    }
    
    
    public void lebarKolom(){
        TableColumn column;
        tabelbarang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column = tabelbarang.getColumnModel().getColumn(0);
        column.setPreferredWidth(40);
        column = tabelbarang.getColumnModel().getColumn(1);
        column.setPreferredWidth(150);
        column = tabelbarang.getColumnModel().getColumn(2);
        column.setPreferredWidth(150);
        column = tabelbarang.getColumnModel().getColumn(3);
        column.setPreferredWidth(300);
        column = tabelbarang.getColumnModel().getColumn(4);
        column.setPreferredWidth(150);
        column = tabelbarang.getColumnModel().getColumn(5);
        column.setPreferredWidth(100);
        column = tabelbarang.getColumnModel().getColumn(6);
        column.setPreferredWidth(290);
    }
    
    public void lebarKolom2(){
        TableColumn column2;
        tabelkategori.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        column2 = tabelkategori.getColumnModel().getColumn(0);
        column2.setPreferredWidth(100);
        column2 = tabelkategori.getColumnModel().getColumn(1);
        column2.setPreferredWidth(200);
    }
    
    public void pencarian(String sql){
        Object[] Baris = {"No","Tanggal","Kode Barang","Nama Barang","Kategori","Jumlah","Keterangan"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelbarang.setModel(tabmode);
        int brs = tabelbarang.getRowCount();
        for (int i = 0; 1 < brs; i++){
            tabmode.removeRow(1);
        }
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String tanggal = hasil.getString("tanggal");
                String kode_brg = hasil.getString("kode_brg");
                String nama_brg = hasil.getString("nama_brg");
                String kategori = hasil.getString("kategori");
                String jumlah = hasil.getString("jumlah");
                String keterangan = hasil.getString("keterangan");
                String[] data = {"",tanggal,kode_brg,nama_brg,kategori,jumlah,keterangan};
                tabmode.addRow(data);
                noTable();
            }
        } catch(Exception e){
        }
    }
    
    public void pencarian2(String sql){
        Object[] Baris2 = {"Kode Kategori","Nama Kategori"};
        tabmode2 = new DefaultTableModel(null, Baris2);
        tabelkategori.setModel(tabmode2);
        int brs = tabelkategori.getRowCount();
        for (int i = 0; 1 < brs; i++){
            tabmode2.removeRow(1);
        }
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                String kode_kategori = hasil.getString("kode_kategori");
                String nama_kategori = hasil.getString("nama_kategori");
                String[] data = {kode_kategori,nama_kategori};
                tabmode2.addRow(data);
            }
        } catch(Exception e){
            
        }
    }

    /**
     * Creates new form dataBarang
     */
    public dataBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        aktif();
        dataTable();
        tanggal();
        lebarKolom();
        dataTable2();
        lebarKolom2();
        txtkdbrg.requestFocus();
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
        labelNamaBarang = new javax.swing.JLabel();
        labelKategori = new javax.swing.JLabel();
        labelKeterangan = new javax.swing.JLabel();
        txtkdbrg = new javax.swing.JTextField();
        btnpilihtgl = new com.toedter.calendar.JDateChooser();
        txtnamabrg = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelbarang = new javax.swing.JTable();
        btnsimpan = new javax.swing.JButton();
        btnubah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btnbersih = new javax.swing.JButton();
        btncari = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        txtkategoribrg = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtketerangan = new javax.swing.JTextArea();
        labeljumlah = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtjumlah = new javax.swing.JTextField();
        txtcarikategori = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelkategori = new javax.swing.JTable();
        btnkeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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
        labelNama.setText("Data Barang PT.Rajawali Nusindo");

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelNama, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(220, 220, 220))
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNama, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
        );

        lbTanggal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTanggal.setText("Tanggal");
        lbTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelKodeBarang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelKodeBarang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelKodeBarang.setText("Kode Barang");
        labelKodeBarang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelNamaBarang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelNamaBarang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNamaBarang.setText("Nama Barang");
        labelNamaBarang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelKategori.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelKategori.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelKategori.setText("Kategori");
        labelKategori.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        labelKeterangan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelKeterangan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelKeterangan.setText("Keterangan");
        labelKeterangan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtkdbrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtkdbrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkdbrgKeyPressed(evt);
            }
        });

        btnpilihtgl.setDateFormatString("dd-MM-yyyy");
        btnpilihtgl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnpilihtgl.setMaximumSize(new java.awt.Dimension(2147400000, 2147400000));
        btnpilihtgl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnpilihtglMouseClicked(evt);
            }
        });
        btnpilihtgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnpilihtglKeyPressed(evt);
            }
        });

        txtnamabrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnamabrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamabrgKeyPressed(evt);
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

        tabelbarang.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tabelbarang.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelbarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelbarang.setRowHeight(30);
        tabelbarang.setRowMargin(2);
        tabelbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelbarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelbarang);

        btnsimpan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnsimpan.setText("Simpan");
        btnsimpan.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnsimpan.setContentAreaFilled(false);
        btnsimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsimpan.setOpaque(true);
        btnsimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnsimpanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnsimpanMouseExited(evt);
            }
        });
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnubah.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnubah.setText("Edit");
        btnubah.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnubah.setContentAreaFilled(false);
        btnubah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnubah.setOpaque(true);
        btnubah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnubahMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnubahMouseExited(evt);
            }
        });
        btnubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnubahActionPerformed(evt);
            }
        });

        btnhapus.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnhapus.setText("Hapus");
        btnhapus.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnhapus.setContentAreaFilled(false);
        btnhapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnhapus.setOpaque(true);
        btnhapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhapusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhapusMouseExited(evt);
            }
        });
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnbersih.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnbersih.setText("Clear");
        btnbersih.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnbersih.setContentAreaFilled(false);
        btnbersih.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnbersih.setOpaque(true);
        btnbersih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnbersihMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnbersihMouseExited(evt);
            }
        });
        btnbersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbersihActionPerformed(evt);
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
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        txtcari.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariKeyTyped(evt);
            }
        });

        txtkategoribrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtkategoribrg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkategoribrgKeyPressed(evt);
            }
        });

        txtketerangan.setColumns(20);
        txtketerangan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtketerangan.setRows(5);
        jScrollPane2.setViewportView(txtketerangan);

        labeljumlah.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labeljumlah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labeljumlah.setText("Jumlah");
        labeljumlah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText(":");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        txtjumlah.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtjumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtjumlahKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtjumlahKeyTyped(evt);
            }
        });

        txtcarikategori.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcarikategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcarikategoriKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Kategori");
        jLabel3.setOpaque(true);

        tabelkategori.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabelkategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tabelkategori.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelkategori.setRowHeight(30);
        tabelkategori.setRowMargin(2);
        tabelkategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkategoriMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelkategori);

        btnkeluar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnkeluar.setText("EXIT");
        btnkeluar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnkeluar.setContentAreaFilled(false);
        btnkeluar.setOpaque(true);
        btnkeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnkeluarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnkeluarMouseExited(evt);
            }
        });
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHeader, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labeljumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnpilihtgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtkdbrg)
                            .addComponent(txtnamabrg)
                            .addComponent(txtkategoribrg)
                            .addComponent(txtjumlah)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtcarikategori, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(lbTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(labelKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(labelNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnpilihtgl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtcarikategori, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtkdbrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(txtnamabrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtkategoribrg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labeljumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(btnkeluar)
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(btnubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnkeluarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkeluarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnkeluarMouseEntered

    private void btnkeluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnkeluarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnkeluarMouseExited

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void panelHeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMouseDragged
        if(maximixed){
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            this.setLocation(x - xMouse, y - yMouse);
        }
    }//GEN-LAST:event_panelHeaderMouseDragged

    private void panelHeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_panelHeaderMousePressed

    private void btnpilihtglMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnpilihtglMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnpilihtglMouseClicked

    private void btnpilihtglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnpilihtglKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnpilihtglKeyPressed

    private void tabelbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelbarangMouseClicked
        int bar = tabelbarang.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        String f = tabmode.getValueAt(bar, 5).toString();
        String g = tabmode.getValueAt(bar, 6).toString();

        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date dateValue = null;
        try{
            dateValue = date.parse((String)tabelbarang.getValueAt(bar, 1));
        } catch (ParseException ex){
            Logger.getLogger(dataBarang.class.getName()).log(Level.SEVERE, null, ex);
        }

        btnpilihtgl.setDate(dateValue);
        txtkdbrg.setText(c);
        txtnamabrg.setText(d);
        txtkategoribrg.setText(e);
        txtjumlah.setText(f);
        txtketerangan.setText(g);
    }//GEN-LAST:event_tabelbarangMouseClicked

    private void btnsimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsimpanMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsimpanMouseEntered

    private void btnsimpanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsimpanMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsimpanMouseExited

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        if(txtkdbrg.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Kode Barang tidak boleh kosong");
        } else if (txtnamabrg.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Nama Barang tidak boleh kosong");
        } else if (txtkategoribrg.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Nama Kategori tidak boleh kosong");
        } else if (txtjumlah.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Jumlah tidak boleh kosong");
        }  else {
        String sql = "insert into tb_barang values (?,?,?,?,?,?)";
        String tampilan = "dd-MM-yyyy";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal = String.valueOf(fm.format(btnpilihtgl.getDate()));
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tanggal.toString());
            stat.setString(2, txtkdbrg.getText());
            stat.setString(3, txtnamabrg.getText());
            stat.setString(4, txtkategoribrg.getText());
            stat.setString(5, txtjumlah.getText());
            stat.setString(6, txtketerangan.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null,"Data Berhasil Disimpan");
            //            String refresh = "select * from tb_barang";
            kosong();
            dataTable();
            lebarKolom();
            txtkdbrg.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan"+e);
        }
        }
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btnubahMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnubahMouseEntered
       // TODO add your handling code here:
    }//GEN-LAST:event_btnubahMouseEntered

    private void btnubahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnubahMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnubahMouseExited

    private void btnubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnubahActionPerformed
        String sql = "update tb_barang set tanggal=?,kode_brg=?,nama_brg=?,kategori=?,jumlah=?,keterangan=? where kode_brg='"+txtkdbrg.getText()+"'";
        String tampilan = "dd-MM-yyyy";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal = String.valueOf(fm.format(btnpilihtgl.getDate()));
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tanggal.toString());
            stat.setString(2, txtkdbrg.getText());
            stat.setString(3, txtnamabrg.getText());
            stat.setString(4, txtkategoribrg.getText());
            stat.setString(5, txtjumlah.getText());
            stat.setString(6, txtketerangan.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            //            String refresh = "select * from tb_barang";
            kosong();
            dataTable();
            lebarKolom();
            txtkdbrg.requestFocus();
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah"+e);
        }
    }//GEN-LAST:event_btnubahActionPerformed

    private void btnhapusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhapusMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnhapusMouseEntered

    private void btnhapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhapusMouseExited
      // TODO add your handling code here:
    }//GEN-LAST:event_btnhapusMouseExited

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        int ok = JOptionPane.showConfirmDialog (null," Apakah Anda Yakin Ingin "
            + "Menghapus Data","Konfirmasi Dialog", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from tb_barang where kode_brg='"+txtkdbrg.getText()+"'";
            try {
                PreparedStatement stat=conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
                kosong();
                dataTable();
                lebarKolom();
                txtkdbrg.requestFocus();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus"+e);
            }
        }
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnbersihMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbersihMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnbersihMouseEntered

    private void btnbersihMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbersihMouseExited
       // TODO add your handling code here:
    }//GEN-LAST:event_btnbersihMouseExited

    private void btnbersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbersihActionPerformed
        tanggal();
        txtkdbrg.requestFocus();
        txtkdbrg.setText(null);
        txtnamabrg.setText(null);
        txtkategoribrg.setText(null);
        txtjumlah.setText(null);
        txtketerangan.setText(null);
    }//GEN-LAST:event_btnbersihActionPerformed

    private void btncariMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncariMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btncariMouseEntered

    private void btncariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncariMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btncariMouseExited

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed
           // TODO add your handling code here: 
    }//GEN-LAST:event_btncariActionPerformed

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        String sqlPencarian = "select * from tb_barang where kode_brg like '%"+txtcari.getText()+"%' or nama_brg like '%"+txtcari.getText()+"%'";
        pencarian(sqlPencarian);
        lebarKolom();
    }//GEN-LAST:event_txtcariKeyTyped

    private void txtkdbrgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkdbrgKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            txtnamabrg.requestFocus();
        }
    }//GEN-LAST:event_txtkdbrgKeyPressed

    private void txtnamabrgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamabrgKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            txtkategoribrg.requestFocus();
        }
    }//GEN-LAST:event_txtnamabrgKeyPressed

    private void txtkategoribrgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkategoribrgKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            txtjumlah.requestFocus();
        }
    }//GEN-LAST:event_txtkategoribrgKeyPressed

    private void txtjumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahKeyPressed
        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
            txtketerangan.requestFocus();
        }
    }//GEN-LAST:event_txtjumlahKeyPressed

    private void txtcarikategoriKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarikategoriKeyTyped
        String sqlPencarian2 = "select * from tb_kategori where kode_kategori like '%"+txtcarikategori.getText()+"%' or nama_kategori like '%"+txtcarikategori.getText()+"%'";
        pencarian2(sqlPencarian2);
        lebarKolom2();
    }//GEN-LAST:event_txtcarikategoriKeyTyped

    private void tabelkategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelkategoriMouseClicked
        int bar = tabelkategori.getSelectedRow();
        String a = tabmode2.getValueAt(bar, 0).toString();
        String b = tabmode2.getValueAt(bar, 1).toString();
        txtkategoribrg.setText(b);
        txtjumlah.requestFocus();
    }//GEN-LAST:event_tabelkategoriMouseClicked

    private void txtjumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahKeyTyped
        char enter=evt.getKeyChar();
        if(!(Character.isDigit(enter)))
        {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Masukan Hanya Angka 0-9", "Input Qty", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtjumlahKeyTyped

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
            java.util.logging.Logger.getLogger(dataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dataBarang dialog = new dataBarang(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnbersih;
    private javax.swing.JButton btncari;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private com.toedter.calendar.JDateChooser btnpilihtgl;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btnubah;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel labelKategori;
    private javax.swing.JLabel labelKeterangan;
    private javax.swing.JLabel labelKodeBarang;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelNamaBarang;
    private javax.swing.JLabel labeljumlah;
    private javax.swing.JLabel lbTanggal;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTable tabelbarang;
    private javax.swing.JTable tabelkategori;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcarikategori;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtkategoribrg;
    private javax.swing.JTextField txtkdbrg;
    private javax.swing.JTextArea txtketerangan;
    private javax.swing.JTextField txtnamabrg;
    // End of variables declaration//GEN-END:variables
}
