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
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class AddRoom extends javax.swing.JFrame {
    private static final I18n I18N = new I18n(AddRoom.class);
    private final static Logger log = LoggerFactory.getLogger(AddRoom.class);
    private final RoomManager roomManager;
    private final RoomListModel model;

    /**
     * Creates new form AddRoom
     */
    public AddRoom(RoomManager roomManager, RoomListModel model) {
        this.roomManager = roomManager;
        this.model = model;
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/pv168/hotel/gui/HotelBundle"); // NOI18N
        setTitle(bundle.getString("addRoom.title")); // NOI18N
        setName("pokoj"); // NOI18N

        comfirm.setText(bundle.getString("addRoom.addButton")); // NOI18N
        comfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comfirmActionPerformed(evt);
            }
        });

        number.setToolTipText(bundle.getString("AddRoom.number")); // NOI18N

        price.setToolTipText(bundle.getString("AddRoom.price")); // NOI18N

        capacity.setToolTipText(bundle.getString("AddRoom.capacity")); // NOI18N

        jLabel1.setText(bundle.getString("addRoom.numberLabel")); // NOI18N

        jLabel2.setText(bundle.getString("addRoom.priceLabel")); // NOI18N

        jLabel3.setText(bundle.getString("addRoom.capacityLabel")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(number, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(comfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(capacity, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                                .addComponent(price))
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(capacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
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
    private class ConfirmSwingWorker extends SwingWorker<ResultText, ResultText> {

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
        protected ResultText doInBackground() throws Exception {
            if(number == null || number.length()<1){
                log.error("form data invalid - number is empty");
                return ResultText.NUMBER_EMPTY;
                    } 
            if(price == null || price.length()<1){
                log.error("form data invalid - price is empty");
                return ResultText.PRICE_EMPTY;
                    } 
            if(capacity == null || capacity.length()<1){
                log.error("form data invalid - capacity is empty");
                return ResultText.CAPACITY_EMPTY;
                    } 
            int number_int;
            try {
                number_int = Integer.parseInt(number);
            }  catch (NumberFormatException e) {
                log.debug("form data invalid - number can not parse");
                return ResultText.NUMBER_INVALID;
            } 
            if (number_int <= 0) {
                log.debug("form data invalid - number is negative");
                return ResultText.NUMBER_NEGATIVE;
            }
            int price_int;
            try {
                price_int = Integer.valueOf(price);
            } catch (NumberFormatException e) {
                log.debug("form data invalid - price can not parse");
                return ResultText.PRICE_INVALID;
            } 
            if (price_int <= 0) {
                log.debug("form data invalid - price is negative");
                return ResultText.PRICE_NEGATIVE;
            }
            int capacity_int;
            try {
                capacity_int = Integer.parseInt(capacity);
            } catch (NumberFormatException e) {
                log.debug("form data invalid - capacity can not parse");
                return ResultText.CAPACITY_INVALID;
            } 
            if (capacity_int <= 0) {
                log.debug("form data invalid - capacity is negative");
                return ResultText.CAPACITY_NEGATIVE;
            }

            try {
                Room room = new Room(price_int, capacity_int, number_int);
                roomManager.createRoom(room);
                //redirect-after-POST protects from multiple submission
                log.debug("redirecting after POST");
            } catch (ServiceFailureException e) {
                log.error("Cannot add room", e);
                return ResultText.ROOM_NOT_CREATE;
            } catch (ValidationException e) {
                log.debug("Room with number " + String.valueOf(number) + " exists");
                return ResultText.ROOM_WITH_SAME_NUMBER;
            }
            return ResultText.ROOM_ADD;
        }

        @Override
        protected void done() {
            ResultText result = null;
            try {
                result = get();
                
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(AddRoom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                java.util.logging.Logger.getLogger(AddRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result.equals(ResultText.ROOM_ADD)) {
                model.addRoom(new Room(Integer.parseInt(price), Integer.parseInt(capacity), Integer.parseInt(number)));
                setVisible(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, I18N.getString((ResultText) result));
            }
        }
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField capacity;
    private javax.swing.JButton comfirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField number;
    private javax.swing.JTextField price;
    // End of variables declaration//GEN-END:variables
}
