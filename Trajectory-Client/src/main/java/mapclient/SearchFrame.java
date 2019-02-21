
package mapclient;

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SearchFrame extends javax.swing.JFrame {
    private String mapUrl;
    private MainFrame mf = MainFrame.getInstance();
    private static SearchFrame instance = new SearchFrame();
    private int zoomAmount = 17;

    public static SearchFrame getInstance() {
        return instance;
    }
        
    public SearchFrame() {
        LookNFeel();
        initComponents();
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public JLabel getTxtNoktaSayisi() {
        return txtNoktaSayisi;
    }

    public JLabel getTxtNumOfPoint() {
        return txtNumOfPoint;
    }

    public JLabel getTxtThTime() {
        return txtThTime;
    }

    public JLabel getTxtThreadZamani() {
        return txtThreadZamani;
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_SearchMap = new javax.swing.JLabel();
        txtNoktaSayisi = new javax.swing.JLabel();
        txtThreadZamani = new javax.swing.JLabel();
        txtNumOfPoint = new javax.swing.JLabel();
        txtThTime = new javax.swing.JLabel();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gezinge Verisi İşleme - Arama Ekranı");
        setResizable(false);

        txtNoktaSayisi.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtNoktaSayisi.setText("Nokta Sayısı            : ");

        txtThreadZamani.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        txtThreadZamani.setText("Thread Zamanı       :");

        txtNumOfPoint.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txtNumOfPoint.setText("00");

        txtThTime.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txtThTime.setText("0:1564 sn");

        btnZoomIn.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        btnZoomIn.setText("+");
        btnZoomIn.setPreferredSize(new java.awt.Dimension(70, 50));
        btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomInActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNoktaSayisi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtThreadZamani, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumOfPoint, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThTime, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnZoomIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnZoomOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_SearchMap, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_SearchMap, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNoktaSayisi)
                            .addComponent(txtNumOfPoint)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(631, 631, 631)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnZoomIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnZoomOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(655, 655, 655)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtThreadZamani)
                            .addComponent(txtThTime))))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomInActionPerformed
        zoomIn();
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomOutActionPerformed
        zoomOut();
    }//GEN-LAST:event_btnZoomOutActionPerformed

     public void reloadMap(String mapUrl, String imageName) {
        try {
            Map.getImageFromUrl(mapUrl, imageName);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon icon = new ImageIcon(imageName + ".jpg");
        Image img = icon.getImage().getScaledInstance(lbl_SearchMap.getWidth(),
                lbl_SearchMap.getHeight(), Image.SCALE_SMOOTH);
        lbl_SearchMap.setIcon(new ImageIcon(img));
    }
    
    private void zoomIn() {
        System.out.println("@>1 : " + mf.getrFieldMapUrl());
        mapUrl = Map.changeZoomAmount(mf.getrFieldMapUrl(), ++zoomAmount);

        reloadMap(mapUrl, "rfieldmapcanvas");
        mf.setrFieldMapUrl(mapUrl);
    }
    
    private void zoomOut() {
        mapUrl = Map.changeZoomAmount(mf.getrFieldMapUrl(), --zoomAmount);
        reloadMap(mapUrl, "rfieldmapcanvas");
        mf.setrFieldMapUrl(mapUrl);
    }
    
    private void LookNFeel() {
        try {
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JLabel lbl_SearchMap;
    private javax.swing.JLabel txtNoktaSayisi;
    private javax.swing.JLabel txtNumOfPoint;
    private javax.swing.JLabel txtThTime;
    private javax.swing.JLabel txtThreadZamani;
    // End of variables declaration//GEN-END:variables
}
