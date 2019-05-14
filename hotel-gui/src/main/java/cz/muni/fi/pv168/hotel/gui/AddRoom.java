/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Room;
import cz.muni.fi.pv168.hotel.RoomManager;
import cz.muni.fi.pv168.hotel.exception.ServiceFailureException;
import cz.muni.fi.pv168.hotel.exception.ValidationException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lydie
 */
public class AddRoom extends javax.swing.JFrame {

    private final static Logger log = LoggerFactory.getLogger(AddRoom.class);
    private final RoomManager roomManager;

    /**
     * Creates new form AddRoom
     */
    public AddRoom(RoomManager roomManager) {
        this.roomManager = roomManager;
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

        comfirm = new javax.swing.JButton();
        number = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        capacity = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("pokoj"); // NOI18N

        comfirm.setText("pridat pokoj");
        comfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comfirmActionPerformed(evt);
            }
        });

        number.setToolTipText("číslo pokoje");

        price.setToolTipText("cena");

        capacity.setToolTipText("capacita");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(capacity, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(price)
                    .addComponent(number))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(comfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(capacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(comfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comfirmActionPerformed
        String numberText = number.getText();
        String priceText = price.getText();
        String capacityText = capacity.getText();

        ConfirmSwingWorker confirmSwingWorker = new ConfirmSwingWorker(numberText, capacityText, priceText, roomManager);
        confirmSwingWorker.execute();

    }//GEN-LAST:event_comfirmActionPerformed
    private class ConfirmSwingWorker extends SwingWorker<Integer, Integer> {

        private final String number;
        private final String price;
        private final String capacity;
        private final RoomManager roomManager;

        public ConfirmSwingWorker(String number, String price, String capacity, RoomManager roomManager) {
            this.number = number;
            this.price = price;
            this.capacity = capacity;
            this.roomManager = roomManager;
        }

        @Override
        protected Integer doInBackground() throws Exception {
            int price_int;
            try {
                price_int = Integer.valueOf(price);
            } catch (NumberFormatException e) {
                log.debug("form data invalid - price can not parse");
                return 4;

            } catch (NullPointerException e) {
                log.debug("form data invalid - price is empty");
                return 2;
            }
            if (price_int <= 0) {
                log.debug("form data invalid - price is negative");
                return 4;
            }

            int capacity_int;
            try {
                capacity_int = Integer.parseInt(capacity);
            } catch (NumberFormatException e) {
                log.debug("form data invalid - capacity can not parse");
                return 3;
            } catch (NullPointerException e) {
                log.debug("form data invalid - capacity is empty");
                return 2;
            }
            if (capacity_int <= 0) {
                log.debug("form data invalid - capacity is negative");
                return 3;
            }

            int number_int;
            try {
                number_int = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                log.debug("form data invalid - number can not parse");
                return 5;
            } catch (NullPointerException e) {
                log.debug("form data invalid - number is empty");
                return 2;
            }
            if (number_int <= 0) {
                log.debug("form data invalid - number is negative");
                return 5;
            }
            try {
                Room room = new Room(price_int, capacity_int, number_int);
                roomManager.createRoom(room);
                //redirect-after-POST protects from multiple submission
                log.debug("redirecting after POST");

            } catch (ServiceFailureException e) {
                log.error("Cannot add room", e);
                return null;
            } catch (ValidationException e) {
                log.debug("Room with" + String.valueOf(number) + " exists");
                return 7;
            }
            return 1;
        }

        @Override
        protected void done() {
            int result = 0;
            try {
                result = get();
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
                    JOptionPane.showMessageDialog(null, "Je nutné vyplnit všechny hodnoty ");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Kapacita není validní číslo nebo je zaporná ");
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Cena není validní číslo nebo je zaporná ");
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Cislo pokoje není validní číslo nebo je zaporná ");
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "pokoj nelze pridat");
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Již existuje pokoj se stejným číslem");
                    break;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField capacity;
    private javax.swing.JButton comfirm;
    private javax.swing.JTextField number;
    private javax.swing.JTextField price;
    // End of variables declaration//GEN-END:variables
}
