package hotel;

import java.util.List;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public interface Accommodation {

    Guest findGuestByRoom(Room room);

    void addGuest(Guest guest, Room room);

    int checkOutGuest(Room room);

    List<Room> freeRooms();
}
