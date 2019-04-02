/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import exception.IllegalEntityException;
import exception.ServiceFailureException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class AccommodationImpl implements Accommodation {

    private DataSource dataSource;

    @SuppressWarnings("WeakerAccess")
    public AccommodationImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Guest findGuestByRoom(Room room) {
        if (room == null) throw new IllegalArgumentException("room is null");
        if (room.getId() == null) throw new IllegalEntityException("room id is null");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(
                     "SELECT Guest.id, name, phone, dateOfCheckIn, dateOfCheckOut, room.id price, capacity, number " +
                             "FROM Guest JOIN Room ON Room.id = Guest.roomId " +
                             "WHERE Room.id = ?")) {
            st.setLong(1, room.getId());
            return GuestManagerImpl.executeQueryForSingleGuests(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when trying to find guest by room " + room, ex);
        }
    }

    public void addGuest(Guest guest, Room room) {
        if (room == null) {
            throw new IllegalArgumentException("room is null");
        }
        if (room.getId() == null) {
            throw new IllegalEntityException("room id is null");
        }
        if (guest == null) throw new IllegalArgumentException("guest is null");
        if (guest.getId() == null) {
            throw new IllegalEntityException("guest id is null");
        }

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("UPDATE Guest SET roomId = ? WHERE id = ? AND roomId IS NULL")) {
                conn.setAutoCommit(false);

                //check if any room is free
                List<Room> countRooms = freeRooms();
                if (countRooms.size() == 0)
                    throw new IllegalEntityException("All rooms are full");

                st.setLong(1, room.getId());
                st.setLong(2, guest.getId());

                conn.commit();
            } catch (Exception ex) {

                conn.rollback();
                throw ex;
            } finally {

                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when adding guest into room", ex);
        }
    }

    public int checkOutGuest(Room room) {
        return 1;
    }

    public List<Room> freeRooms() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(
                     "SELECT Room.id, price, capacity, numberOfRoom " +
                             "FROM Room LEFT JOIN Guest ON Room.id = Guest.roomId " +
                             "GROUP BY Room.id, price, capacity, numberOfRoom " +
                             "HAVING COUNT(Guest.id) = 0")) {
            return RoomManagerImpl.executeQueryForMultipleRooms(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when trying to find empty rooms", ex);
        }
    }
}
