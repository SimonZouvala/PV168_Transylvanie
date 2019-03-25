package hotel;

//name of package
import exception.*;


import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import javax.sql.DataSource;
import javax.xml.bind.ValidationException;
import org.apache.derby.jdbc.EmbeddedDataSource;

import org.junit.rules.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;

import static java.time.Month.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * @author ?imon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */

public class GuestManagerTest {
    
    private GuestManagerImpl manager;
    private DataSource ds;
    
    private final static ZonedDateTime NOW
            = LocalDateTime.of(2019, MARCH, 24, 16, 00).atZone(ZoneId.of("UTC"));
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static DataSource prepareDataSource() throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("guestmgr-test");
        ds.setCreateDatabase("create");
        return ds;
    }

    private static Clock prepareClockMock(ZonedDateTime now) {
        return Clock.fixed(now.toInstant(), now.getZone());
    }

    Room room = mock(Room.class);

    @Before
    public void setUp() throws SQLException, IOException {
        ds = prepareDataSource();
        DBUtils.executeSqlScript(ds,GuestManager.class.getResourceAsStream("createTables.sql"));
        manager = new GuestManagerImpl(ds, prepareClockMock(NOW));
    }

    @After
    public void tearDown() throws Exception {
        DBUtils.executeSqlScript(ds,GuestManager.class.getResourceAsStream("dropTables.sql"));
    }
    
    private GuestBuilder willyGuestBuilder() {
        return new GuestBuilder()
                .name("Willy Werewolf")
                .phone("705052648")
                .dateOfCheckIn(2019,JANUARY,21)
                .dateOfCheckOut(2019,FEBRUARY,8)
                .room(room);
    }

    private GuestBuilder aliceGuestBuilder() {
        return new GuestBuilder()
            .name("Alice Zombie")
            .phone("605050689")
            .dateOfCheckIn(2019,FEBRUARY,21)
            .dateOfCheckOut(2019,MARCH,8)
            .room(room);
    }

    //
    // Test for GuestManager.createGuest(Guest)
    //
    
    @Test
    public void createGuest() {
        Guest guest = willyGuestBuilder().build();
        manager.createGuest(guest);

        Long guestId = guest.getId();
        assertThat(guestId).isNotNull();

        assertThat(manager.getGuest(guestId))
                .isNotSameAs(guest)
                .isEqualToComparingFieldByField(guest);
    }
    
    @Test
    public void createGuestWithExistingId() {
        Guest guest = willyGuestBuilder()
                .id(1L)
                .build();
        expectedException.expect(IllegalEntityException.class);
        manager.createGuest(guest);
    }
    
    @Test
    public void createGuestWithNullName() {
        Guest guest = willyGuestBuilder()
                .name(null)
                .build();
        assertThatThrownBy(() -> manager.createGuest(guest))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void createGuestWithNullPhone() {
        Guest guest = willyGuestBuilder()
                .phone(null)
                .build();
        assertThatThrownBy(() -> manager.createGuest(guest))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void createGuestCheckOutBeforeCheckIn() {
        Guest guest = willyGuestBuilder()
                .dateOfCheckIn(2019,JANUARY,21)
                .dateOfCheckOut(2019,JANUARY,20)
                .build();
        expectedException.expect(ValidationException.class);
        manager.createGuest(guest);
    }

    @Test
    public void createGuestCheckInAndCheckOutInSameDay() {
        Guest guest = willyGuestBuilder()
                .dateOfCheckIn(2019,JANUARY,21)
                .dateOfCheckOut(2019,JANUARY,21)
                .build();
        manager.createGuest(guest);

        assertThat(manager.getGuest(guest.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(guest);
    }

    @Test
    public void createGuestWithCheckInTomorrow() {
        LocalDate tomorrow = NOW.toLocalDate().plusDays(1);
        Guest guest = willyGuestBuilder()
                .dateOfCheckIn(tomorrow)
                .dateOfCheckOut(null)
                .build();
        assertThatThrownBy(() -> manager.createGuest(guest))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void createGuestWithCheckInToday() {
        LocalDate today = NOW.toLocalDate();
        Guest guest = willyGuestBuilder()
            .dateOfCheckIn(2019,JANUARY,21)
            .dateOfCheckOut(2019,JANUARY,20)
            .build();
        manager.createGuest(guest);

        assertThat(manager.getGuest(guest.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(guest);
    }

    @Test
    public void createGuestWithCheckOutTomorrow() {
        LocalDate tomorrow = NOW.toLocalDate().plusDays(1);
        Guest guest = willyGuestBuilder()
                .dateOfCheckOut(tomorrow)
                .build();
        assertThatThrownBy(() -> manager.createGuest(guest))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void createGuestNullCheckIn() {
        Guest guest = willyGuestBuilder()
                .dateOfCheckIn(null)
                .build();
        manager.createGuest(guest);
        assertThat(manager.getGuest(guest.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(guest);
    }

    @Test
    public void createGuestNullCheckOut() {
        Guest guest = willyGuestBuilder()
            .dateOfCheckOut(null)
            .build();
        manager.createGuest(guest);
        assertThat(manager.getGuest(guest.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(guest);
    }
    //
    // Test for GuestManager find methods
    //
    @Test
    public void findAllGuest() {
        assertThat(manager.findAllGuest()).isEmpty();

        Guest willy = willyGuestBuilder().build();
        Guest alice = aliceGuestBuilder().build();

        manager.createGuest(willy);
        manager.createGuest(alice);

        assertThat(manager.findAllGuest())
                .usingFieldByFieldElementComparator()
                .containsOnly(willy,alice);
    }

    @Test
    public void findGuestByName() {
        Guest willy = willyGuestBuilder().build();

        manager.createGuest(willy);

        assertThat(manager.findGuestByName(willy.getName()))
                .isNotNull()
                .isEqualToComparingFieldByField(willy);
    }
    
     //
    // Test for GuestManager.deleteGuest(Guest)
    //
    
     @Test
    public void deleteGuest() {

        Guest willy = willyGuestBuilder().build();
        Guest alice = aliceGuestBuilder().build();
        manager.createGuest(willy);
        manager.createGuest(alice);

        assertThat(manager.getGuest(willy.getId())).isNotNull();
        assertThat(manager.getGuest(alice.getId())).isNotNull();

        manager.deleteGuest(willy);

        assertThat(manager.getGuest(willy.getId())).isNull();
        assertThat(manager.getGuest(alice.getId())).isNotNull();

    }


    @Test(expected = IllegalArgumentException.class)
    public void deleteNullGuest() {
        manager.deleteGuest(null);
    }

    @Test
    public void deleteGuestWithNullId() {
        Guest guest = willyGuestBuilder().id(null).build();
        expectedException.expect(IllegalEntityException.class);
        manager.deleteGuest(guest);
    }

    @Test
    public void deleteNonExistingGuest() {
        Guest guest = willyGuestBuilder().id(1L).build();
        expectedException.expect(IllegalEntityException.class);
        manager.deleteGuest(guest);
    }
}