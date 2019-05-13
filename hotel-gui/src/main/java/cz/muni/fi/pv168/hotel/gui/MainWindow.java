/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.RoomManager;

import javax.swing.ListModel;

/**
 *
 * @author Lydie
 */
public class MainWindow extends javax.swing.JFrame {
    private final RoomManager roomManager;
    private final GuestManager guestManager;
    /**
     * Creates new form MainWindow
     */
    public MainWindow(RoomManager roomManager, GuestManager guestManager) {
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

        accommodation = new javax.swing.JButton();
        roomButton = new javax.swing.JButton();
        guestButton = new javax.swing.JButton();
        checkoutButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        showTextArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        selectionTabel = new javax.swing.JList<>();
        addRoom = new javax.swing.JButton();
        removeRoom = new javax.swing.JButton();
        findTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        accommodation.setText("Ubytovat");
        accommodation.setToolTipText("");
        accommodation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accommodationActionPerformed(evt);
            }
        });

        roomButton.setText("Pokoje");
        roomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomButtonActionPerformed(evt);
            }
        });

        guestButton.setText("Hoste");
        guestButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guestButtonMouseClicked(evt);
            }
        });
        guestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestButtonActionPerformed(evt);
            }
        });

        checkoutButton.setText("Odhlasit hosta");

        showTextArea.setColumns(20);
        showTextArea.setRows(5);
        jScrollPane1.setViewportView(showTextArea);

        selectionTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectionTabelMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(selectionTabel);

        jScrollPane3.setViewportView(jScrollPane2);

        addRoom.setText("+");
        addRoom.setFocusable(false);
        addRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRoomActionPerformed(evt);
            }
        });

        removeRoom.setText("-");
        removeRoom.setFocusable(false);

        findTextField.setText("Hledej...");
        findTextField.setToolTipText("Hledej...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(accommodation, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(roomButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(guestButton))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 181, Short.MAX_VALUE)
                                .addComponent(checkoutButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(findTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(accommodation, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roomButton)
                            .addComponent(guestButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRoom)
                    .addComponent(removeRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(findTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accommodationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accommodationActionPerformed
        CheckIn checkIn = new CheckIn(roomManager, guestManager);
        checkIn.setVisible(true);
    }//GEN-LAST:event_accommodationActionPerformed

    private void guestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestButtonActionPerformed
        // TODO add your handling code here:
         selectionTabel.setModel((ListModel<String>) new GuestListModel(guestManager.findAllGuest()));
        findTextField.setVisible(true);
        addRoom.setVisible(false);
        removeRoom.setVisible(false);
        
    }//GEN-LAST:event_guestButtonActionPerformed

    private void roomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomButtonActionPerformed
        // TODO add your handling code here:
          selectionTabel.setModel((ListModel<String>) new RoomListModel(roomManager.findAllRooms()));
        addRoom.setVisible(true);
        findTextField.setVisible(false);
        
                
    }//GEN-LAST:event_roomButtonActionPerformed

    private void selectionTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectionTabelMouseClicked
        // TODO add your handling code here:
        showTextArea.setText("");
        int indexOfValue = selectionTabel.getSelectedIndex();
        if ((selectionTabel.getModel() instanceof RoomListModel)) {
            
            showTextArea.append( "ID: " + roomManager.findAllRooms().get(indexOfValue).getId()+ "\n");
            showTextArea.append( "Číslo pokoje: " + roomManager.findAllRooms().get(indexOfValue).getNumber()+ "\n");
            showTextArea.append( "Cena: " + roomManager.findAllRooms().get(indexOfValue).getPrice()+ "\n");
            showTextArea.append( "Kapacita: " + roomManager.findAllRooms().get(indexOfValue).getCapacity()+ "\n");
            removeRoom.setVisible(true);
        }
        if ((selectionTabel.getModel() instanceof GuestListModel)) {
            showTextArea.append("ID: " + guestManager.findAllGuest().get(indexOfValue).getId() + "\n");
            showTextArea.append("Jméno: " + guestManager.findAllGuest().get(indexOfValue).getName() + "\n");
            showTextArea.append("Telefoní číslo: " + guestManager.findAllGuest().get(indexOfValue).getPhone() + "\n");
            showTextArea.append("Datum přihlášení: " + guestManager.findAllGuest().get(indexOfValue).getDateOfCheckIn() + "\n");
            showTextArea.append("Datum odhlášení: " + guestManager.findAllGuest().get(indexOfValue).getDateOfCheckOut() + "\n");
            showTextArea.append("Číslo pokoje: " + guestManager.findAllGuest().get(indexOfValue).getRoomId() + "\n");
        }
        
        
        
        
    }//GEN-LAST:event_selectionTabelMouseClicked

    private void addRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRoomActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_addRoomActionPerformed

    private void guestButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guestButtonMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_guestButtonMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        addRoom.setVisible(false);
        removeRoom.setVisible(false);
        findTextField.setVisible(false);
    }//GEN-LAST:event_formWindowActivated

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accommodation;
    private javax.swing.JButton addRoom;
    private javax.swing.JButton checkoutButton;
    private javax.swing.JTextField findTextField;
    private javax.swing.JButton guestButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton removeRoom;
    private javax.swing.JButton roomButton;
    private javax.swing.JList<String> selectionTabel;
    private javax.swing.JTextArea showTextArea;
    // End of variables declaration//GEN-END:variables
}
