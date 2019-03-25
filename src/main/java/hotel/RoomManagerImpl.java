package hotel;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class RoomManagerImpl implements RoomManager {

    private DataSource dataSource;

    @SuppressWarnings("WeakerAccess")
    public RoomManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createRoom(Room room) {

    }

    @Override
    public void deleteRoom(Room room) {

    }

    @Override
    public List<Room> findAllRooms() {
        return null;
    }

    @Override
    public Room getRoom(Long id) {
        return null;
    }


}
