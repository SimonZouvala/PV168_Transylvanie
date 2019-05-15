/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Guest;
import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.Room;
import cz.muni.fi.pv168.hotel.RoomManager;
import cz.muni.fi.pv168.hotel.exception.IllegalEntityException;
import cz.muni.fi.pv168.hotel.exception.ServiceFailureException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lydie
 */
public class CheckIn extends javax.swing.JFrame {

    private final static Logger log = LoggerFactory.getLogger(CheckIn.class);
    private final RoomManager roomManager;
    private final GuestManager guestManager;

    /**
     * Creates new form CheckIn
     */
    public CheckIn(RoomManager roomManager, GuestManager guestManager) {
        this.roomManager = roomManager;
        this.guestManager = guestManager;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name = new javax.swing.JTextField();
        phone = new javax.swing.JTextField();
        confirm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        name.setText("jmeno");
        name.setToolTipText("jmeno");

        phone.setText("phone");
        phone.setToolTipText("telefon");

        confirm.setText("ubytovat");
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(phone)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(confirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmActionPerformed
        String nameText = name.getText();
        String phoneText = phone.getText();
        ConfirmSwingWorker confirmSwingWorker = new ConfirmSwingWorker(nameText, phoneText, roomManager, guestManager);
        confirmSwingWorker.execute();

    }//GEN-LAST:event_confirmActionPerformed
    private class ConfirmSwingWorker extends SwingWorker {

        private final String name;
        private final String phone;
        private final RoomManager roomManager;
        private final GuestManager guestManager;

        public ConfirmSwingWorker(String name, String phone, RoomManager roomManager, GuestManager guestManager) {
            this.name = name;
            this.phone = phone;
            this.roomManager = roomManager;
            this.guestManager = guestManager;
        }

        @Override
        protected Integer doInBackground() throws Exception {
            try {
                Guest guest = new Guest(null, name, phone);
                guestManager.createGuest(guest);
            } catch (ServiceFailureException e) {
                log.error("Cannot add guest", e);
                return 3;
            } catch (IllegalEntityException iee) {
                log.debug("all rooms are full");
                return 2;
            }
            return 1;
        }
        @Override
        protected void done() {
            int result = 0;
            try {
                result = (int) get();
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(AddRoom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                java.util.logging.Logger.getLogger(AddRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
            switch (result) {
                case 1:
                    setVisible(false); //you can't see me!
                    dispose();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Všechny pokoje jsou plné ");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Nejde přidat hosta");
                    break;
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirm;
    private javax.swing.JTextField name;
    private javax.swing.JTextField phone;
    // End of variables declaration//GEN-END:variables
}
