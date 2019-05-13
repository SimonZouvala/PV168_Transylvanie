package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Guest;
import cz.muni.fi.pv168.hotel.Room;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Lýdie Hemalová 433757
 */
public class RoomListModel extends AbstractListModel{

  
     
    private List<Room> roomList;

    public RoomListModel(List<Room> roomList) {
        this.roomList = roomList;
    }

  public int getSize() {
    return roomList.size();
  }

  public Object getElementAt(int index) {
    return "Pokoj č. " + roomList.get(index).getNumber();
  }
   
}
