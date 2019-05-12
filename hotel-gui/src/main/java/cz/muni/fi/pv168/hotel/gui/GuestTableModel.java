package cz.muni.fi.pv168.hotel.gui

import cz.muni.fi.pv168.hotel.Guest;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lýdie Hemalová 433757
 */
public class GuestTableModel extends AbstractTableModel{
    
    private enum Column {

        NAME("Name", String.class) {
            @Override
            Object getValue(Guest guest) {
                return guest.getName();
            }  
        },
        PHONE("Phone", String.class){
            @Override
            Object getValue(Guest guest) {
                return guest.getPhone();
            }  
        },
        DATE_OF_CHECK_IN("Check in", LocalDate.class){
            @Override
            Object getValue(Guest guest) {
                return guest.getDateOfCheckIn();
            }
        },
        ROOM("Room", Long.class){
            @Override
            Object getValue(Guest guest) {
                return guest.getRoomId();
            }
        };
        
        private final String label;
        private final Class<?> type;

        private Column(String label, Class<?> type) {
            this.label = label;
            this.type = type;
        }

        abstract Object getValue(Guest guest);
    } 
    
    private List<Guest> guestList;

    public GuestTableModel(List<Guest> guestList) {
        this.guestList = guestList;
    }
    
    @Override
    public int getRowCount() {
        return guestList.size();
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Guest currentGuest = guestList.get(rowIndex);
        return Column.values()[columnIndex].getValue(currentGuest);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return Column.values()[columnIndex].label;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Column.values()[columnIndex].type;
    }
}
