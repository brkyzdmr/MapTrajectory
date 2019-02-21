/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapclient;

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author berka
 */
public class MainFrame extends javax.swing.JFrame {

    private ArrayList<Point> points; // Dosyadan okunan ham verileri saklar.
    private String API_KEY = "AIzaSyB8WNqID5oeMT5VePIpT9Ig_uPcXd0BVdA";
    private boolean isFileLoaded = false;
    private int zoomAmount = 17;
    private static String mapUrl; // Ham veriler ile oluşturulan veriyi saklar.
    private ServiceFrame sf = ServiceFrame.getInstance(); // ServiceFrame ile haberleşmek için
    private SearchFrame rf = SearchFrame.getInstance(); // SearchFrame ile haberleşmek için
    private static String redMapUrl; // İndirgenen için oluşturulan urlyi saklar.
    private static String fieldMapUrl;
    private static String rFieldMapUrl;
    private static MainFrame instance = new MainFrame();

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        LookNFeel();    // Form tasarımını değiştirmek için
        initComponents();
    }

    public static MainFrame getInstance() {
        return instance;
    }

    public static String getMapUrl() {
        return mapUrl;
    }

    public static void setMapUrl(String mapUrl) {
        MainFrame.mapUrl = mapUrl;
    }

    public static String getRedMapUrl() {
        return redMapUrl;
    }

    public static void setRedMapUrl(String redMapUrl) {
        MainFrame.redMapUrl = redMapUrl;
    }

    public int getZoomAmount() {
        return zoomAmount;
    }
    
    public ArrayList<Point> getPoints() {
        return points;
    }

    public static String getFieldMapUrl() {
        return fieldMapUrl;
    }

    public static void setFieldMapUrl(String fieldMapUrl) {
        MainFrame.fieldMapUrl = fieldMapUrl;
    }

    public static String getrFieldMapUrl() {
        return rFieldMapUrl;
    }

    public static void setrFieldMapUrl(String rFieldMapUrl) {
        MainFrame.rFieldMapUrl = rFieldMapUrl;
    }
      

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnKoordinatEkle = new javax.swing.JButton();
        btnAlanSorgusu = new javax.swing.JButton();
        btnIndirgemeYap = new javax.swing.JButton();
        lbl_MapImage = new javax.swing.JLabel();
        btnZoomOut = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        txtMerkezNokta = new javax.swing.JTextField();
        txtEpsilon = new javax.swing.JTextField();
        btnIndirgemeSorgusu = new javax.swing.JButton();
        txtMerkezNokta2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gezinge Verisi İşleme - Ana Ekran");
        setLocation(new java.awt.Point(250, 250));
        setName("mainFrame"); // NOI18N
        setResizable(false);

        btnKoordinatEkle.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btnKoordinatEkle.setText("Koordinatları Ekle");
        btnKoordinatEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKoordinatEkleActionPerformed(evt);
            }
        });

        btnAlanSorgusu.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btnAlanSorgusu.setText("Alan Sorgusu");
        btnAlanSorgusu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlanSorgusuActionPerformed(evt);
            }
        });

        btnIndirgemeYap.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btnIndirgemeYap.setText("İndirgeme Yap");
        btnIndirgemeYap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndirgemeYapActionPerformed(evt);
            }
        });

        btnZoomOut.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        btnZoomOut.setText("-");
        btnZoomOut.setPreferredSize(new java.awt.Dimension(70, 50));
        btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomOutActionPerformed(evt);
            }
        });

        btnZoomIn.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        btnZoomIn.setText("+");
        btnZoomIn.setPreferredSize(new java.awt.Dimension(70, 50));
        btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomInActionPerformed(evt);
            }
        });

        txtMerkezNokta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMerkezNokta.setToolTipText("Ham veri üzerinde dikdörtgen alan taraması için gereken bilgiler(MerkezNoktaLat, MerkezNoktaLong, KenarX, KenarY)");
        txtMerkezNokta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMerkezNoktaMouseClicked(evt);
            }
        });

        txtEpsilon.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEpsilon.setToolTipText("İndirgeme yapılabilmesi için gereken epsilon değeri(double)");
        txtEpsilon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEpsilonMouseClicked(evt);
            }
        });

        btnIndirgemeSorgusu.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btnIndirgemeSorgusu.setText("İndirgeme Sorgusu");
        btnIndirgemeSorgusu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndirgemeSorgusuActionPerformed(evt);
            }
        });

        txtMerkezNokta2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMerkezNokta2.setToolTipText("İndirgeme verisi üzerinde dikdörtgen alan taraması için gereken bilgiler(Epsilon, MerkezNoktaLat, MerkezNoktaLong, KenarX, KenarY)");
        txtMerkezNokta2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMerkezNokta2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIndirgemeYap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKoordinatEkle, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(btnAlanSorgusu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnZoomIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnZoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMerkezNokta)
                    .addComponent(txtEpsilon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(btnIndirgemeSorgusu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMerkezNokta2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_MapImage, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_MapImage, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnKoordinatEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(txtEpsilon, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnIndirgemeYap, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMerkezNokta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlanSorgusu, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMerkezNokta2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnIndirgemeSorgusu, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnZoomIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnZoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbl_MapImage.getAccessibleContext().setAccessibleName("lbl_MapImage");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * KoordinatEkle butonuna basıldığında, trajectory verilerini bir metin 
     * dosyasından okumak için File Dialog penceresi açar.
     * 
     * @param evt 
     */
    private void btnKoordinatEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKoordinatEkleActionPerformed
        if (!isFileLoaded) {
            openFileDialog();
        } else {
            int reply = JOptionPane.showConfirmDialog(null,
                    "Yeni bir veri seti eklemek istediğinize emin misiniz?",
                    "Uyarı", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                openFileDialog();
            }
        }

    }//GEN-LAST:event_btnKoordinatEkleActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomInActionPerformed
        if (isFileLoaded) {
            System.out.println("Zoom In");
            zoomIn();
        } else {
            // Buton çalışması durdurulur.
        }
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomOutActionPerformed
        if (isFileLoaded) {
            System.out.println("Zoom Out");
            zoomOut();
        } else {
            // Buton çalışması durdurulur.
        }
    }//GEN-LAST:event_btnZoomOutActionPerformed

    private void btnIndirgemeYapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndirgemeYapActionPerformed
        String value = txtEpsilon.getText();

        if ("".equals(value)) {
            JOptionPane.showMessageDialog(null, "Lütfen epsilon değerini girin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                mapserver.IncomingData incomingData;
                OutgoingData outgoingData = new OutgoingData("indirgeme", points, value);
                incomingData = Client.sendMapData(outgoingData);
                System.out.println("İndirgendi!!");

                if (incomingData != null) {
                    redMapUrl = Map.createUrl(incomingData.getProcessedData(), "blue", "0x0000FFFF");
                    redMapUrl = Map.changeZoomAmount(redMapUrl, zoomAmount);
                    sf.reloadMap(redMapUrl, "redmapcanvas");
                    sf.getTxtRedRate().setText(incomingData.getReductionRate() + "");
                    sf.getTxtThTime().setText(incomingData.getThreadTime());
                    sf.getTxtNumOfPoint().setText(incomingData.getProcessedData().size() + "");
                    sf.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "İndirgeme yapılamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NullPointerException ex) {
                System.out.println("Hatalı texbox verisi: " + ex);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnIndirgemeYapActionPerformed

    private void btnAlanSorgusuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlanSorgusuActionPerformed

        String value = txtMerkezNokta.getText();

        if ("".equals(value)) {
            JOptionPane.showMessageDialog(null, "Lütfen merkez nokta koordinatlarını girin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                mapserver.IncomingData incomingData;
                OutgoingData outgoingData = new OutgoingData("alansorgusu", points, value);
                incomingData = Client.sendMapData(outgoingData);               

                if(incomingData != null) {
                    System.out.println("İndirgendi!!");
                    fieldMapUrl = Map.createUrlWithSearchField(incomingData.getProcessedData(), "blue", value);
                    fieldMapUrl = Map.changeZoomAmount(fieldMapUrl, zoomAmount);
                    rf.reloadMap(fieldMapUrl, "fieldmapcanvas");
                    rf.getTxtThTime().setText(incomingData.getThreadTime());
                    rf.getTxtNumOfPoint().setText(incomingData.getProcessedData().size() + "");
                    rf.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "İndirgenme taraması yapılamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NullPointerException ex) {
                System.out.println("Hatalı texbox verisi: " + ex);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnAlanSorgusuActionPerformed

    private void txtEpsilonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEpsilonMouseClicked
        txtEpsilon.setText("");
    }//GEN-LAST:event_txtEpsilonMouseClicked

    private void txtMerkezNoktaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMerkezNoktaMouseClicked
        txtMerkezNokta.setText("");
    }//GEN-LAST:event_txtMerkezNoktaMouseClicked

    private void btnIndirgemeSorgusuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndirgemeSorgusuActionPerformed

                String value = txtMerkezNokta2.getText();
                String[] values = value.split(",");
                String fValue = values[1]+ "," + values[2] + "," + values[3] + "," + values[4];

        if ("".equals(value)) {
            JOptionPane.showMessageDialog(null, "Lütfen merkez nokta koordinatlarını girin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                mapserver.IncomingData incomingData;
                OutgoingData outgoingData = new OutgoingData("indirgemesorgusu", points, value);
                incomingData = Client.sendMapData(outgoingData);

                if(incomingData != null) {
                    rFieldMapUrl = Map.createUrlWithSearchField(incomingData.getProcessedData(), "blue", fValue);
                    rFieldMapUrl = Map.changeZoomAmount(rFieldMapUrl, zoomAmount);
                    rf.reloadMap(rFieldMapUrl, "rfieldmapcanvas");
                    rf.getTxtThTime().setText(incomingData.getThreadTime());
                    rf.getTxtNumOfPoint().setText(incomingData.getProcessedData().size() + "");
                    rf.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Alan taraması yapılamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NullPointerException ex) {
                System.out.println("Hatalı texbox verisi: " + ex);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
    }//GEN-LAST:event_btnIndirgemeSorgusuActionPerformed

    private void txtMerkezNokta2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMerkezNokta2MouseClicked
        txtMerkezNokta2.setText("");
    }//GEN-LAST:event_txtMerkezNokta2MouseClicked

    private void zoomIn() {
        mapUrl = Map.changeZoomAmount(mapUrl, ++zoomAmount);
        reloadMap(mapUrl, "maincanvas");
    }

    private void zoomOut() {
        mapUrl = Map.changeZoomAmount(mapUrl, --zoomAmount);
        reloadMap(mapUrl, "maincanvas");
    }

    private void openFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
        int result = fileChooser.showOpenDialog(this);
        String filePath;

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePath = selectedFile.getAbsolutePath();
            try {
                readFromFile(filePath);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
        sf.setVisible(false);
    }

    /**
     * Açılan dosya içerisinde satır satır aldığı verileri virgüle göre
     * keserek bir string diziye atar. Bu dizideki ilk veri enlem, ikinci
     * veri ise boylam bilgisini verir. Bu enlem ve boylam bilgisi kullanı-
     * larak Point tipinde nesneler oluşturulur ve bu nesneler points
     * adındaki listeye eklenir. Oluşturulan liste ile harita için bir url
     * oluşturulur daha sonra harita ekrana çizdirilir.
     * 
     * @param filePath Açılan dosyanın yolu
     * @throws IOException 
     */
    private void readFromFile(String filePath) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        points = new ArrayList<>();
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Point newPoint = new Point(Double.parseDouble(values[0]),
                        Double.parseDouble(values[1]));
                points.add(newPoint);
            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Dosya string kesme işlemi hatası: " + ex);
        } finally {
            br.close();
        }
        try {
            mapUrl = Map.createUrl(points, "red", "0xFF0000FF");
            reloadMap(mapUrl, "maincanvas");
            isFileLoaded = true;
        } catch (NullPointerException ex) {
            System.out.println("Giriş map yükleme hatası: " + ex);
        }

    }

    /**
     * mapUrl ile Google sunucularına istek yollar ve dönen resmi ekrana basar.
     * 
     * @param mapUrl Ekrana çizdirilecek resim için Google sunucularına 
     * gönderilecek url
     * @param imageName Proje dosyasında bulunan resmin adı
     */
    private void reloadMap(String mapUrl, String imageName) {
        try {
            Map.getImageFromUrl(mapUrl, imageName);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Gelen resmin çerçeveye sığma ve yerleştirilme işlemleri
        ImageIcon icon = new ImageIcon(imageName + ".jpg");
        Image img = icon.getImage().getScaledInstance(lbl_MapImage.getWidth(),
                lbl_MapImage.getHeight(), Image.SCALE_SMOOTH);
        lbl_MapImage.setIcon(new ImageIcon(img));
    }
    
    private void LookNFeel() {
        try {
            // Klasik Windows form stili vermek için
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public static void main(String args[]) {                   

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlanSorgusu;
    private javax.swing.JButton btnIndirgemeSorgusu;
    private javax.swing.JButton btnIndirgemeYap;
    private javax.swing.JButton btnKoordinatEkle;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JLabel lbl_MapImage;
    private javax.swing.JTextField txtEpsilon;
    private javax.swing.JTextField txtMerkezNokta;
    private javax.swing.JTextField txtMerkezNokta2;
    // End of variables declaration//GEN-END:variables
}