package hotel;

import java.time.Clock;
import java.util.List;
import javax.sql.DataSource;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class GuestManagerImpl implements GuestManager {
    
    private final DataSource dataSource;
    private final Clock clock;

    @SuppressWarnings("WeakerAccess")
    public GuestManagerImpl(DataSource dataSource, Clock clock) {
        this.dataSource = dataSource;
        this.clock = clock;
    }
    
    @Override
    public void deleteGuest(Guest guest) {

    }

    @Override
    public void createGuest(Guest guest) {

    }

    @Override
    public List<Guest> findAllGuest() {
        return null;
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
