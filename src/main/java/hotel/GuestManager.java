package hotel;

import java.util.List;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public interface GuestManager {
    void deleteGuest(Guest guest);

    void createGuest(Guest guest);

    List<Guest> findAllGuest();

    Guest findGuestByName(String name);
}
