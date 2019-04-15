package cz.muni.fi.pv168.hotel;

import java.util.List;

/**
 * This service allows to manipulate with rooms.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public interface RoomManager {
    void createRoom(Room room);

    void deleteRoom(Room room);

    List<Room> findAllRooms();

    Room getRoom(Long id);
}
