/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Guest;
import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.exception.IllegalEntityException;
import cz.muni.fi.pv168.hotel.exception.ServiceFailureException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class CheckIn extends javax.swing.JFrame {

    private static final I18n I18N = new I18n(CheckIn.class);
    private final static Logger log = LoggerFactory.getLogger(CheckIn.class);
    private final GuestManager guestManager;
    private final GuestListModel model;

    /**
     * Creates new form CheckIn
     *
     * @param guestManager
     * @param model
     */
    public CheckIn(GuestManager guestManager, GuestListModel model) {
        this.guestManager = guestManager;
        this.model = model;
        initComponents();
        log.info("Window CheckIn activated.");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/pv168/hotel/gui/HotelBundle"); // NOI18N
        setTitle(bundle.getString("chekIn.title")); // NOI18N

        name.setToolTipText(bundle.getString("checkIn.name")); // NOI18N
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        phone.setToolTipText(bundle.getString("checkIn.phone")); // NOI18N

        confirm.setText(bundle.getString("chekIn.accommodate")); // NOI18N
        confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmActionPerformed(evt);
            }
        });

        jLabel1.setText(bundle.getString("chekIn.nameLabel")); // NOI18N

        jLabel2.setText(bundle.getString("chekIn.phoneLabel")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(confirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        ConfirmSwingWorker confirmSwingWorker = new ConfirmSwingWorker(nameText, phoneText);
        confirmSwingWorker.execute();

    }//GEN-LAST:event_confirmActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed
    private class ConfirmSwingWorker extends SwingWorker<ResultTextCheckIn, ResultTextCheckIn> {

        private final String name;
        private final String phone;
        private Guest guest = null;

        public ConfirmSwingWorker(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        @Override
        protected ResultTextCheckIn doInBackground() throws Exception {
            if (name == null || phone == null
                    || name.length() < 1 || phone.length() < 1) {
                log.error("phone or name is empty");
                return ResultTextCheckIn.EMPTY_FIELD;
            }
            try {
                guest = new Guest(null, name, phone);
                guestManager.createGuest(guest);
            } catch (ServiceFailureException e) {
                log.error("Cannot add guest", e);
                return ResultTextCheckIn.GUEST_NOT_CREATE;
            } catch (IllegalEntityException iee) {
                log.debug("all rooms are full");
                return ResultTextCheckIn.ALL_ROOMS_FULL;
            }
            log.info("New guest added.");
            return ResultTextCheckIn.ADD_GUEST;
        }

        @Override
        protected void done() {
            ResultTextCheckIn result = null;
            try {
                result = get();
            } catch (InterruptedException | ExecutionException ex) {
                java.util.logging.Logger.getLogger(AddRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result == ResultTextCheckIn.ADD_GUEST) {

                if (model != null) {
                    model.addGuest(guest);
                }
                setVisible(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, I18N.getString((ResultTextCheckIn) result));
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField name;
    private javax.swing.JTextField phone;
    // End of variables declaration//GEN-END:variables
}
