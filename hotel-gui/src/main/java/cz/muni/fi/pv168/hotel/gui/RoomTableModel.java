package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Room;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lýdie Hemalová 433757
 */
public class RoomTableModel extends AbstractTableModel{
    
    private enum Column {

        NUMBER("Number", Integer.class) {
            @Override
            Object getValue(Room room) {
                return room.getNumber();
            }  
        },
        CAPACITY("Capacity", Integer.class){
            @Override
            Object getValue(Room room) {
                return room.getCapacity();
            }  
        },
        PRICE("Price", Integer.class){
            @Override
            Object getValue(Room room) {
                return room.getPrice();
            }
        };
        
        private final String label;
        private final Class<?> type;

        private Column(String label, Class<?> type) {
            this.label = label;
            this.type = type;
        }

        abstract Object getValue(Room room);
    } 
    
    private List<Room> roomList;

    public RoomTableModel(List<Room> roomList) {
        this.roomList = roomList;
    }
    
    @Override
    public int getRowCount() {
        return roomList.size();
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Room currentRoom = roomList.get(rowIndex);
        return Column.values()[columnIndex].getValue(currentRoom);
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
