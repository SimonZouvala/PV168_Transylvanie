package hotel;

import exception.ServiceFailureException;
import exception.*;
import java.sql.*;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */
//ODPOLEDNE jeste neco dodelam
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
        
        try(Connection con = dataSource.getConnection();
                PreparedStatement st = con.prepareStatement("DELETE FROM Guest WHERE id = ?")){
            st.setLong(1,guest.getId());
            int rows = st.executeUpdate();
            if (rows != 1) throw new IllegalEntityException("deleted " + rows + " instead of 1 guest");
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when deleting guest from the db", ex);
        }
    }
    

    @Override
    public void createGuest(Guest guest) {

    }

    @Override
    public List<Guest> findAllGuest() {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT id, name, phone, dateOfCheckIn,dateOfCheckOut,room FROM Guest")) {
            return executeQueryForMultipleGuests(st);
        } catch (SQLException ex) {
            throw new ServiceFailureException("Error when getting all guests from DB", ex);
        }
    }
    static List<Guest> executeQueryForMultipleGuest(PreparedStatement st) throws SQLException {
        try (ResultSet rs = st.executeQuery()) {
            List<Guest> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowToBody(rs));
            }
            return result;
        }
    }
    
        static private Guest rowToGuest(ResultSet rs) throws SQLException {
        Guest guest = new Guest();
        guest.setId(rs.getLong("id"));
        guest.setName(rs.getString("name"));
        guest.getPhone(rs.getString("phone"));
        guest.getDateOfCheckIn(toLocalDate(rs.getDate("dateOfCheckIn")));
        Guest setDateOfCheckOut = guest.setDateOfCheckOut(toLocalDate(rs.getDate("died")));
        guest.getRoom(toRoom(rs.getString("room"));
        return guest;
    }
    @Override
    public Guest findGuestByName(String name) {
        return null;
    }
    @Override
    public Guest getGuest(Long id) {
        return null;
    }
    
}
