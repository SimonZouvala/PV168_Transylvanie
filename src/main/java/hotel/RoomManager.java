package hotel;

import java.util.List;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public interface RoomManager {
    void createRoom(Room room);

    void deleteRoom(Room room);

    List<Room> findAllRoom();
}
