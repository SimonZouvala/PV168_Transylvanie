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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import javax.swing.ListModel;
import javax.swing.SwingWorker;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class MainWindow extends javax.swing.JFrame {

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);
    private static final I18n I18N = new I18n(MainWindow.class);
    private final RoomManager roomManager;
    private final GuestManager guestManager;
    private Guest guest;
    private boolean wasWindowActivatedBefore = false;
    private boolean guestTableActivatedBefore = false;

    /**
     * Creates new form MainWindow
     */
    public MainWindow(RoomManager roomManager, GuestManager guestManager) {
        this.roomManager = roomManager;
        this.guestManager = guestManager;
        initComponents();
        log.info("MainWindow activated");

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
        findButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/pv168/hotel/gui/HotelBundle"); // NOI18N
        setTitle(bundle.getString("main.title")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        accommodation.setText(bundle.getString("main.accomodation")); // NOI18N
        accommodation.setToolTipText("");
        accommodation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accommodationActionPerformed(evt);
            }
        });

        roomButton.setText(bundle.getString("main.rooms")); // NOI18N
        roomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomButtonActionPerformed(evt);
            }
        });

        guestButton.setText(bundle.getString("main.guests")); // NOI18N
        guestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestButtonActionPerformed(evt);
            }
        });

        checkoutButton.setText(bundle.getString("main.checkOut")); // NOI18N
        checkoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutButtonActionPerformed(evt);
            }
        });

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
        removeRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRoomActionPerformed(evt);
            }
        });

        findTextField.setToolTipText("Hledej...");

        findButton.setText(bundle.getString("main.findGuest")); // NOI18N
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(accommodation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(roomButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(guestButton))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(checkoutButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(findTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(findButton)
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
                            .addComponent(guestButton)))
                    .addComponent(checkoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRoom)
                    .addComponent(removeRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(findTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void accommodationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accommodationActionPerformed
        AccommodationSwingWorker accommodationSwingWorker = new AccommodationSwingWorker();
        accommodationSwingWorker.execute();
    }//GEN-LAST:event_accommodationActionPerformed

    private void guestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestButtonActionPerformed
        guestTableActivatedBefore = true;
        GuestListSwingWorker guestListSwingWorker = new GuestListSwingWorker();
        guestListSwingWorker.execute();

        showTextArea.setText("");
        selectionTabel.setVisible(true);
        findTextField.setVisible(true);
        findButton.setVisible(true);
        addRoom.setVisible(false);
        removeRoom.setVisible(false);


    }//GEN-LAST:event_guestButtonActionPerformed

    private void roomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomButtonActionPerformed
        guestTableActivatedBefore = false;
        RoomListSwingWorker roomListSwingWorker = new RoomListSwingWorker();
        roomListSwingWorker.execute();

        showTextArea.setText("");
        selectionTabel.setVisible(true);
        addRoom.setVisible(true);
        removeRoom.setVisible(true);
        removeRoom.setEnabled(false);
        findTextField.setVisible(false);
        findButton.setVisible(false);
        checkoutButton.setEnabled(false);


    }//GEN-LAST:event_roomButtonActionPerformed

    private void selectionTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectionTabelMouseClicked
        // TODO add your handling code here:
        showTextArea.setText("");

        PrintSwingWorker printSwingWorker;
        int indexOfValue = selectionTabel.getSelectedIndex();
        if ((selectionTabel.getModel() instanceof GuestListModel)) {
            printSwingWorker = new PrintSwingWorker(guestManager, indexOfValue);

        } else {
            printSwingWorker = new PrintSwingWorker(roomManager, indexOfValue);

        }
        printSwingWorker.execute();

    }//GEN-LAST:event_selectionTabelMouseClicked

    private void addRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRoomActionPerformed
        AddRoom room = new AddRoom(roomManager, (RoomListModel) selectionTabel.getModel());
        room.setVisible(true);


    }//GEN-LAST:event_addRoomActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        if (!wasWindowActivatedBefore) {
            addRoom.setVisible(false);
            removeRoom.setVisible(false);
            findTextField.setVisible(false);
            findButton.setVisible(false);
            selectionTabel.setVisible(false);
            showTextArea.setText("");
            wasWindowActivatedBefore = true;
            checkoutButton.setEnabled(false);
        } else {
            removeRoom.setEnabled(false);
            selectionTabel.clearSelection();
            showTextArea.setText("");
            checkoutButton.setEnabled(false);
        }
    }//GEN-LAST:event_formWindowActivated

    private void checkoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkoutButtonActionPerformed
        CheckOut checkOut = new CheckOut(guestManager, guest, (GuestListModel) selectionTabel.getModel());
        checkOut.setVisible(true);
        selectionTabel.setVisible(false);
        showTextArea.setText("");
    }//GEN-LAST:event_checkoutButtonActionPerformed

    private void removeRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeRoomActionPerformed
        // TODO add your handling code here:
        int indexOfValue = selectionTabel.getSelectedIndex();
        RemoveRoomSwingWorker removeRoomSwingWorker = new RemoveRoomSwingWorker(indexOfValue);
        removeRoomSwingWorker.execute();


    }//GEN-LAST:event_removeRoomActionPerformed

    private void findButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findButtonActionPerformed
        showTextArea.setText("");
        FindGuestSwingWorker findGuestSwingWorker = new FindGuestSwingWorker(findTextField.getText());
        findGuestSwingWorker.execute();

    }//GEN-LAST:event_findButtonActionPerformed

    private class FindGuestSwingWorker extends SwingWorker<Guest, Guest> {

        private final String name;

        public FindGuestSwingWorker(String name) {
            this.name = name;
        }

        @Override
        protected Guest doInBackground() throws Exception {
            Guest guest = guestManager.findGuestByName(name);
            return guest;
        }

        @Override
        protected void done() {
            Guest result = null;
            try {
                result = get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                showTextArea.append(result.toString());
            } catch (IllegalArgumentException | NullPointerException eae) {
                log.error("None guest with this name is in hotel.");
                JOptionPane.showMessageDialog(null, I18N.getString("find"));
            }

        }
    }

    private class RemoveRoomSwingWorker extends SwingWorker<Room, Room> {

        private final int index;

        public RemoveRoomSwingWorker(int index) {

            this.index = index;

        }

        @Override
        protected Room doInBackground() throws Exception {
            Room room = roomManager.findAllRooms().get(index);
            if (guestManager.findGuestByRoom(room) != null) {
                log.error("Deleted room is full.");
                return null;
            }
            roomManager.deleteRoom(room);
            return room;
        }

        @Override
        protected void done() {
            Room result = null;
            try {
                result = get();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result != null) {
                RoomListModel model = (RoomListModel) selectionTabel.getModel();
                model.deleteRoom(result);
                removeRoom.setEnabled(false);
                showTextArea.setText("");
            } else {
                JOptionPane.showMessageDialog(null, I18N.getString((ResultTextAddRoom) ResultTextAddRoom.ROOM_IS_FULL));
            }
        }

    }

    private class PrintSwingWorker extends SwingWorker<Object, Object> {

        private final RoomManager roomManager;
        private final GuestManager guestManager;
        private final int index;

        public PrintSwingWorker(GuestManager guestManager, int index) {
            this.guestManager = guestManager;
            this.index = index;
            this.roomManager = null;
        }

        public PrintSwingWorker(RoomManager roomManager, int index) {
            this.roomManager = roomManager;
            this.index = index;
            this.guestManager = null;
        }

        @Override
        protected Object doInBackground() throws Exception {
            try {
                if (guestManager != null) {
                    guest = guestManager.findAllGuest().get(index);
                    return guest;

                } else {
                    removeRoom.setEnabled(true);
                    return roomManager.findAllRooms().get(index);
                }

            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                showTextArea.setText("");
                log.error("Nothing selection");
                removeRoom.setEnabled(false);
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                showTextArea.append(get().toString());
                if (guest == get()) {
                    if (guest.getRoomId() != 0) {
                        checkoutButton.setEnabled(true);
                    } else {
                        checkoutButton.setEnabled(false);
                    }
                }

            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                showTextArea.setText("");
                log.error("Nothing selection");
                removeRoom.setEnabled(false);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private class RoomListSwingWorker extends SwingWorker<List<Room>, List<Room>> {

        public RoomListSwingWorker() {
        }

        @Override
        protected List<Room> doInBackground() throws Exception {
            List<Room> roomList = roomManager.findAllRooms();
            return roomList;
        }

        @Override
        protected void done() {
            try {
                selectionTabel.setModel((ListModel<String>) new RoomListModel(get()));
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class GuestListSwingWorker extends SwingWorker<List<Guest>, List<Guest>> {

        public GuestListSwingWorker() {
        }

        @Override
        protected List<Guest> doInBackground() throws Exception {
            List<Guest> guestList = guestManager.findAllGuest();
            return guestList;
        }

        @Override
        protected void done() {
            try {
                selectionTabel.setModel((ListModel<String>) new GuestListModel(get()));
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class AccommodationSwingWorker extends SwingWorker<ResultTextCheckIn, ResultTextCheckIn> {

        public AccommodationSwingWorker() {

        }

        @Override
        protected ResultTextCheckIn doInBackground() throws Exception {
            List<Room> freeRooms = guestManager.freeRooms();
            if (freeRooms.size() == 0) {
                log.info("Rooms are full");
                return ResultTextCheckIn.ALL_ROOMS_FULL;
            }
            return ResultTextCheckIn.ADD_GUEST;
        }

        @Override
        protected void done() {
            ResultTextCheckIn result = null;
            try {
                result = get();
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(AddRoom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                java.util.logging.Logger.getLogger(AddRoom.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result.equals(ResultTextCheckIn.ADD_GUEST)) {
                CheckIn checkIn;
                if (guestTableActivatedBefore) {
                    checkIn = new CheckIn(guestManager, (GuestListModel) selectionTabel.getModel());
                } else {
                    checkIn = new CheckIn(guestManager, null);
                }
                checkIn.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, I18N.getString((ResultTextCheckIn) result));
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accommodation;
    private javax.swing.JButton addRoom;
    private javax.swing.JButton checkoutButton;
    private javax.swing.JButton findButton;
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
