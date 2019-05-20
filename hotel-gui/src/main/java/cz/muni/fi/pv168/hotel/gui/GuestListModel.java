package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.Guest;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class GuestListModel extends AbstractListModel {

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

    public void addGuest(Guest guest) {
        guestList.add(guest);
        int lastRow = guestList.size() - 1;
        fireIntervalAdded(guest, lastRow, lastRow);
    }

    public void refreshGuests() {
        int lastRow = guestList.size() - 1;
        fireContentsChanged(guestList, lastRow, lastRow);
    }
}
