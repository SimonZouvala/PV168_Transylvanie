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
 *
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class AccommodationImpl implements Accommodation{

    private DataSource dataSource;

    @SuppressWarnings("WeakerAccess")
    public AccommodationImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Guest findGuestByRoom(Room room){
        if (room == null) throw new IllegalArgumentException("room is null");
        if (room.getId() == null) throw new IllegalEntityException("room id is null");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(
                     "SELECT Guest.id, name, phone, dateOfCheckIn, dateOfCheckOut, room " +
                             "FROM Guest JOIN Room ON Room.id = Guest.roomId " +
                             "WHERE Room.id = ?")) {
            st.setLong(1, room.getId());
            return GuestManagerImpl.executeQueryForSingleGuests(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when trying to find guest by room " + room, ex);
        }
    }

    public void addGuest(Guest guest, Room room){

    }

    public int checkOutGuest(Room room){
        return 1;
    }

    public List<Room> freeRooms(){
        return null;
    }
}
