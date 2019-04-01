package hotel;

import exception.DBUtils;
import exception.IllegalEntityException;
import exception.ServiceFailureException;
import exception.ValidationException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        validate(room);
        if (room.getId() != null) {
            throw new IllegalEntityException("room id is already set");
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement("INSERT INTO Room (price,capacity,numberOfRoom) VALUES (?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, room.getPrice());
            st.setInt(2, room.getCapacity());
            st.setInt(3, room.getNumberOfRoom());
            st.executeUpdate();
            room.setId(DBUtils.getId(st.getGeneratedKeys()));

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting room into db", ex);
        }
    }



    @Override
    public void deleteRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (room.getId() == null) {
            throw new IllegalEntityException("room id is null");
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement("DELETE FROM Room WHERE id = ?")) {
            st.setLong(1, room.getId());
            int count = st.executeUpdate();
            if (count != 1) {
                throw new IllegalEntityException("deleted " + count + " instead of 1 room");
            }

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when deleting room from the db", ex);
        }
    }

    @Override
    public List<Room> findAllRooms() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT id, price, capacity, numberOfRoom FROM Room")) {
            return executeQueryForMultipleRooms(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when getting all rooms from DB", ex);
        }

    }

    static List<Room> executeQueryForMultipleRooms(PreparedStatement st) throws SQLException {
        try (ResultSet rs = st.executeQuery()) {
            List<Room> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowToRoom(rs));
            }
            return result;
        }
    }

    private static Room rowToRoom(ResultSet rs) throws SQLException {
        Room result = new Room();
        result.setId(rs.getLong("id"));
        result.setPrice(rs.getInt("price"));
        result.setCapacity(rs.getInt("capacity"));
        result.setNumberOfRoom(rs.getInt("number of room"));
        return result;
    }

    @Override
    public Room getRoom(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT id, price, capacity, numberOfRoom FROM Room WHERE id = ?")) {
            st.setLong(1, id);
            return executeQueryForSingleRoom(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when getting room with id = " + id + " from DB", ex);
        }
    }

    private Room executeQueryForSingleRoom(PreparedStatement st) throws SQLException {
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rowToRoom(rs);
            } else {
                return null;
            }
        }
    }
    private void validate(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (room.getPrice() < 0) {
            throw new ValidationException("price is negative number");
        }
        if (room.getCapacity() < 0) {
            throw new ValidationException("capacity is negative number");
        }
        if (room.getNumberOfRoom() < 0) {
            throw new ValidationException("number of room is negative number");
        }
    }

}
