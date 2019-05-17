package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Guest;
import cz.muni.fi.pv168.hotel.Room;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class RoomListModel extends AbstractListModel {

    private static final I18n I18N = new I18n(RoomListModel.class);
    private List<Room> roomList;

    public RoomListModel(List<Room> roomList) {
        this.roomList = roomList;
    }

    public int getSize() {
        return roomList.size();
    }

    public Object getElementAt(int index) {
        return I18N.getString("pokoj") + roomList.get(index).getNumber();
    }

    public void addRoom(Room room) {
        roomList.add(room);
        int lastRow = roomList.size() - 1;
        fireIntervalAdded(room, lastRow, lastRow);
    }

    public void deleteRoom(Room room) {
        roomList.remove(room);
        int lastRow = roomList.size() - 1;
        fireIntervalRemoved(room, lastRow, lastRow);
    }
}
