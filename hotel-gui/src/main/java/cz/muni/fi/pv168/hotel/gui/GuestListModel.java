package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Guest;
import java.time.LocalDate;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Lýdie Hemalová 433757
 */
public class GuestListModel extends AbstractListModel{

    @Override
    public ListDataListener[] getListDataListeners() {
        return super.getListDataListeners(); //To change body of generated methods, choose Tools | Templates.
    }
     

   private List<Guest> guestList;

    public GuestListModel(List<Guest> guestList) {
        this.guestList = guestList;
     
    }

  public int getSize() {
    return guestList.size();
  }

  public Object getElementAt(int index) {
    return guestList.get(index).getName();
  }
//
//    @Override
//    public int getSize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Object getElementAt(int index) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    private enum Column {
//
//        NAME("Name", String.class) {
//            @Override
//            Object getValue(Guest guest) {
//                return guest.getName();
//            }  
//        },
//        PHONE("Phone", String.class){
//            @Override
//            Object getValue(Guest guest) {
//                return guest.getPhone();
//            }  
//        },
//        DATE_OF_CHECK_IN("Check in", LocalDate.class){
//            @Override
//            Object getValue(Guest guest) {
//                return guest.getDateOfCheckIn();
//            }
//        },
//        ROOM("Room", Long.class){
//            @Override
//            Object getValue(Guest guest) {
//                return guest.getRoomId();
//            }
//        };
//        
//        private final String label;
//        private final Class<?> type;
//
//        private Column(String label, Class<?> type) {
//            this.label = label;
//            this.type = type;
//        }
//
//        abstract Object getValue(Guest guest);
//    } 
//    
//    private List<Guest> guestList;
//
//    public GuestListModel(List<Guest> guestList) {
//        this.guestList = guestList;
//    }
//    
//    @Override
//    public int getRowCount() {
//        return guestList.size();
//    }
//
//    @Override
//    public int getColumnCount() {
//        return Column.values().length;
//    }
//
//    @Override
//    public Object getValueAt(int rowIndex, int columnIndex) {
//        Guest currentGuest = guestList.get(rowIndex);
//        return Column.values()[columnIndex].getValue(currentGuest);
//    }
//
//    @Override
//    public String getColumnName(int columnIndex) {
//        return Column.values()[columnIndex].label;
//    }
//
//    @Override
//    public Class<?> getColumnClass(int columnIndex) {
//        return Column.values()[columnIndex].type;
//    }
}
