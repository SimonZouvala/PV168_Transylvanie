package hotel;

import exception.*;

import java.sql.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */

// Uz jen chybi par testu (5). Bacha je mozne, ze mame spatne testy. Pokud ti bude padat test checkOutGuest
// tak nastav do ZonedDateTime NOW dnesni datum. Potom to funguje :)

public class GuestManagerImpl implements GuestManager {

    private final DataSource dataSource;
    private final Clock clock;

    @SuppressWarnings("WeakerAccess")
    public GuestManagerImpl(DataSource dataSource, Clock clock) {
        this.dataSource = dataSource;
        this.clock = clock;
    }


    @Override
    public void deleteGuest(Guest guest) throws ServiceFailureException {
        if (guest == null) throw new IllegalArgumentException("guest is null");
        if (guest.getId() == null) throw new IllegalEntityException("guest id is null");

        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement("DELETE FROM Guest WHERE id = ?")) {
            st.setLong(1, guest.getId());
            int rows = st.executeUpdate();
            if (rows != 1) throw new IllegalEntityException("deleted " + rows + " instead of 1 guest");
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when deleting guest from the db", ex);
        }
    }


    @Override
    public void createGuest(Guest guest) {
        validate(guest);
        if (guest.getId() != null) throw new IllegalEntityException("Guest id is already set");

        List<Room> freeRooms = freeRooms();
        if (freeRooms.size() == 0)
            throw new IllegalEntityException("All rooms are full");
        Room firstFreeRoom = freeRooms.get(0);
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "INSERT INTO Guest (name,phone,dateOfCheckIn,dateOfCheckOut, roomId) VALUES (?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, guest.getName());
            st.setString(2, guest.getPhone());
            guest.setDateOfCheckIn(LocalDate.now(clock));
            guest.setDateOfCheckOut(null);
            st.setDate(3, toSqlDate(guest.getDateOfCheckIn()));
            st.setDate(4, toSqlDate(guest.getDateOfCheckOut()));
            st.setLong(5, firstFreeRoom.getId());

            st.executeUpdate();
            guest.setId(DBUtils.getId(st.getGeneratedKeys()));

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting guest into db", ex);
        }
        connectionGuestWithRoom(guest, firstFreeRoom);


    }

    @Override
    public List<Guest> findAllGuest() {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT id, name, phone, dateOfCheckIn,dateOfCheckOut, roomId FROM Guest")) {
            return executeQueryForMultipleGuests(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when getting all guests from DB", ex);
        }
    }

    static List<Guest> executeQueryForMultipleGuests(PreparedStatement st) throws SQLException {
        try (ResultSet rs = st.executeQuery()) {
            List<Guest> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowToGuest(rs));
            }
            return result;
        }
    }

    static private Guest rowToGuest(ResultSet rs) throws SQLException {
        Guest guest = new Guest();
        Room room = new Room();
        guest.setId(rs.getLong("id"));
        guest.setName(rs.getString("name"));
        guest.setPhone(rs.getString("phone"));
        guest.setDateOfCheckIn(toLocalDate(rs.getDate("dateOfCheckIn")));
        guest.setDateOfCheckOut(toLocalDate(rs.getDate("dateOfCheckOut")));
        room.setId(rs.getLong("RoomId"));

        return guest;
    }

    @Override
    public Guest findGuestByName(String name) {
        if (name == null) throw new IllegalArgumentException("name is null");

        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT id, name, phone,dateOfCheckIn, dateOfCheckOut,  roomId  FROM Guest WHERE name = ?")) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rowToGuest(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when getting guest with name = " + name + " from DB", ex);
        }
    }

    @Override
    public Guest findGuestByRoom(Room room) {
        if (room == null) throw new IllegalArgumentException("room is null");
        if (room.getId() == null) throw new IllegalEntityException("room id is null");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(
                     "SELECT Guest.id, name, phone, dateOfCheckIn, dateOfCheckOut, roomId " +
                             "FROM Guest LEFT JOIN Room ON Room.id = Guest.roomId " +
                             "WHERE Room.id = ?")) {
            st.setLong(1, room.getId());
            return GuestManagerImpl.executeQueryForSingleGuests(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when trying to find guest by room " + room, ex);
        }
    }

    @Override
    public int checkOutGuest(Guest guest) {
        if (guest == null) throw new IllegalArgumentException("guest is null");
        if (guest.getId() == null) throw new IllegalEntityException("guest id is null");

        Room room = getRoomByGuest(guest);
        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(
                     "UPDATE Guest SET roomId = NULL, dateOfCheckOut = ? WHERE id =? AND roomId IS NOT NULL")) {
            guest.setDateOfCheckOut(LocalDate.now(clock));
            st.setDate(1, toSqlDate(guest.getDateOfCheckOut()));
            st.setLong(2, guest.getId());
            int count = st.executeUpdate();
            if (count != 1) throw new IllegalEntityException("updated " + count + " instead of 1 body");
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when check out guest from room", ex);
        }

        Period period = Period.between(LocalDate.now(), guest.getDateOfCheckIn());
        return room.getPrice() * period.getDays();
    }


    @Override
    public List<Room> freeRooms() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(
                     "SELECT Room.id, price, capacity, number " +
                             "FROM Room LEFT JOIN Guest ON Room.id = Guest.roomId " +
                             "GROUP BY Room.id, price, capacity, number " +
                             "HAVING COUNT(Guest.id) = 0")) {
            return RoomManagerImpl.executeQueryForMultipleRooms(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when trying to find empty rooms", ex);
        }
    }

    @Override
    public Guest getGuest(Long id) throws ServiceFailureException {
        if (id == null) throw new IllegalArgumentException("id is null");

        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT id, name, phone, dateOfCheckIn, dateOfCheckOut, roomId  FROM Guest  WHERE id = ?")) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rowToGuest(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when getting guest with id = " + id + " from DB", ex);
        }
    }

    private void validate(Guest guest) {
        if (guest == null) {
            throw new IllegalArgumentException("guest is null");
        }
        if (guest.getName() == null) {
            throw new ValidationException("name is null");
        }
        if (guest.getPhone() == null) {
            throw new ValidationException("phone is null");
        }
        if (guest.getDateOfCheckIn() != null && guest.getDateOfCheckOut() != null && guest.getDateOfCheckOut().isBefore(guest.getDateOfCheckOut())) {
            throw new ValidationException("dateOfCheckOut is before dateOfCheckIn");
        }
        LocalDate today = LocalDate.now(clock);
        if (guest.getDateOfCheckOut() != null && guest.getDateOfCheckIn().isAfter(today)) {
            throw new ValidationException("dateOfCheckIn is in future");
        }
    }

    private static Date toSqlDate(LocalDate localDate) {
        return localDate == null ? null : Date.valueOf(localDate);
    }

    private static LocalDate toLocalDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }


    public static Guest executeQueryForSingleGuests(PreparedStatement st) throws SQLException {
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rowToGuest(rs);
            } else {
                return null;
            }
        }
    }

    private void connectionGuestWithRoom(Guest guest, Room firstFreeRoom) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(
                    "UPDATE Guest SET roomId = ? WHERE id = ? AND roomId IS NULL")) {
                conn.setAutoCommit(false);

                st.setLong(1, firstFreeRoom.getId());
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

    private Room getRoomByGuest(Guest guest) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(
                     "SELECT Room.id, price, capacity, number " +
                             "FROM Room LEFT JOIN Guest ON  Guest.roomId = Room.id WHERE Guest.roomId = ?")) {
            st.setLong(1, guest.getId());
            return RoomManagerImpl.executeQueryForSingleRoom(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when trying to find empty rooms", ex);
        }

    }
}
