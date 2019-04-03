package hotel;


import java.util.List;

/**
 * This service allows to manipulate with guests.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public interface GuestManager {
    void deleteGuest(Guest guest);

    void createGuest(Guest guest);

    List<Guest> findAllGuest();
    
    Guest getGuest(Long id);

    Guest findGuestByName(String name);

    Guest findGuestByRoom(Room room);

    int checkOutGuest(Guest guest);

    List<Room> freeRooms();
}
