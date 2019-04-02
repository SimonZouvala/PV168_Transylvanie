package hotel;

import exception.*;

import java.sql.*;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */

// jak doplnit room u create, rowToGuest
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

        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "INSERT INTO Guest (name,phone,dateOfCheckIn,dateOfCheckOut, Room.id, price, capacity, number) VALUES (?,?,?,?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, guest.getName());
            st.setString(2, guest.getPhone());
            st.setDate(3, toSqlDate(guest.getDateOfCheckIn()));
            st.setDate(4, toSqlDate(guest.getDateOfCheckOut()));
            st.setLong(5, guest.getRoom().getId());
            st.setInt(6,guest.getRoom().getPrice());
            st.setInt(7, guest.getRoom().getCapacity());
            st.setInt(8,guest.getRoom().getNumber());

            st.executeUpdate();
            guest.setId(DBUtils.getId(st.getGeneratedKeys()));

        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when inserting room into db", ex);
        }
    }

    @Override
    public List<Guest> findAllGuest() {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT id, name, phone, dateOfCheckIn,dateOfCheckOut, Room.id, price, capacity, number  FROM Guest JOIN Room ON Room.id = Guest.roomId")) {
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
        room.setPrice(rs.getInt("price"));
        room.setCapacity(rs.getInt("capacity"));
        room.setNumber(rs.getInt("number"));
        guest.setRoom(room);

        return guest;
    }

    @Override
    public Guest findGuestByName(String name) {
        if (name == null) throw new IllegalArgumentException("name is null");

        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT id, name, phone,dateOfCheckIn, dateOfCheckOut,  Room.id, price, capacity,  number  FROM Guest JOIN Room ON Room.id = Guest.roomId WHERE name = ?")) {
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
    public Guest getGuest(Long id) throws ServiceFailureException {
        if (id == null) throw new IllegalArgumentException("id is null");

        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT id, name, phone, dateOfCheckIn, dateOfCheckOut,  Room.id, price, capacity, number  FROM Guest JOIN Room ON Room.id = Guest.roomId WHERE id = ?")) {
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
            throw new ValidationException("dateOfCheckOut is before dateOfheckIn");
        }
        LocalDate today = LocalDate.now(clock);
        if (guest.getDateOfCheckOut() != null && guest.getDateOfCheckIn().isAfter(today)) {
            throw new ValidationException("dateOfCheckIn is in future");
        }
//        if (guest.getDateOfCheckIn() != null && guest.getDateOfCheckOut().isAfter(today)) {
//            throw new ValidationException("dateOfCheckOut is in future");
//        }
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
}
