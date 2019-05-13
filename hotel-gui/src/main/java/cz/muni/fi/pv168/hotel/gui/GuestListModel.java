package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Guest;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Lýdie Hemalová 433757
 */
public class GuestListModel extends AbstractListModel{
     
   private final List<Guest> guestList;

    public GuestListModel(List<Guest> guestList) {
        this.guestList = guestList;  
    }

    @Override
  public int getSize() {
    return guestList.size();
  }

    @Override
  public Object getElementAt(int index) {
    return guestList.get(index).getName();
  }
}
