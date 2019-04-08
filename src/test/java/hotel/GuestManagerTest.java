package hotel;

import exception.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;

import org.junit.rules.ExpectedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;

import static java.lang.StrictMath.abs;
import static java.time.Month.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */

public class GuestManagerTest {

    private RoomManagerImpl roomManager;
    private GuestManagerImpl manager;
    private DataSource ds;

    private final static ZonedDateTime NOW
            = LocalDateTime.of(2019, APRIL, 8, 16, 00).atZone(ZoneId.of("UTC"));

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static DataSource prepareDataSource() throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:guestmgr-test");
        ds.setCreateDatabase("create");
        return ds;
    }

    private static Clock prepareClockMock(ZonedDateTime now) {
        return Clock.fixed(now.toInstant(), now.getZone());
    }


    @Before
    public void setUp() throws SQLException, IOException {
        ds = prepareDataSource();
        DBUtils.executeSqlScript(ds, GuestManager.class.getResourceAsStream("createTables.sql"));

        manager = new GuestManagerImpl(ds, prepareClockMock(NOW));
        roomManager = new RoomManagerImpl(ds);
        prepareTestData();
    }

    @After
    public void tearDown() throws Exception {
        DBUtils.executeSqlScript(ds, GuestManager.class.getResourceAsStream("dropTables.sql"));
    }


    private Room room1, room2, room3, roomWithNullId, roomNotInDB;


    private void prepareTestData() {

        room1 = new RoomBuilder().price(500).capacity(5).number(1).build();
        room2 = new RoomBuilder().price(500).capacity(5).number(2).build();
        room3 = new RoomBuilder().price(500).capacity(5).number(3).build();

        roomManager.createRoom(room1);
        roomManager.createRoom(room2);
        roomManager.createRoom(room3);

        roomWithNullId = new RoomBuilder().id(null).build();
        roomNotInDB = new RoomBuilder().id(room3.getId() + 100).build();
        assertThat(roomManager.getRoom(roomNotInDB.getId())).isNull();
    }

    private GuestBuilder willyGuestBuilder() {
        return new GuestBuilder()
                .name("Willy Werewolf")
                .phone("705052648")
                .dateOfCheckIn(2019, JANUARY, 21)
                .dateOfCheckOut(null);
    }

    private GuestBuilder aliceGuestBuilder() {
        return new GuestBuilder()
                .name("Alice Zombie")
                .phone("605050689")
                .dateOfCheckIn(2019, FEBRUARY, 21)
                .dateOfCheckOut(null);
    }

    //--------------------------------------------------------------------------
    // Test for GuestManager.createGuest(Guest)
    //--------------------------------------------------------------------------

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

    @Test(expected = IllegalArgumentException.class)
    public void createNullGuest() {
        manager.createGuest(null);
    }
    @Test
    public void createBodyWithNullName() {
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
    /*
    @Test
    public void createGuestCheckOutBeforeCheckIn() {
        Guest guest = willyGuestBuilder()
                .dateOfCheckIn(2019, JANUARY, 21)
                .dateOfCheckOut(2019, JANUARY, 20)
                .build();
        expectedException.expect(ValidationException.class);
        manager.createGuest(guest);
    }
    */

    @Test
    public void createGuestCheckInAndCheckOutInSameDay() {
        LocalDate today = NOW.toLocalDate();
        Guest guest = willyGuestBuilder()
                .dateOfCheckIn(today)
                .dateOfCheckOut(today)
                .build();
        manager.createGuest(guest);

        assertThat(manager.getGuest(guest.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(guest);
    }
/*
    @Test
    public void createGuestWithCheckInTomorrow() {
        LocalDate tomorrow = NOW.toLocalDate().plusDays(1);
        Guest guest = willyGuestBuilder()
                .dateOfCheckIn(tomorrow)
                .dateOfCheckOut(null)
                .build();
        assertThatThrownBy(() -> manager.createGuest(guest))
                .isInstanceOf(exception.ValidationException.class);
    }
*/
    @Test
    public void createGuestWithCheckInToday() {
        LocalDate today = NOW.toLocalDate();
        Guest guest = willyGuestBuilder()
                .dateOfCheckIn(today)
                .dateOfCheckOut(null)
                .build();
        manager.createGuest(guest);

        assertThat(manager.getGuest(guest.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(guest);
    }
    /*
    @Test
    public void createGuestWithCheckOutTomorrow() {
        LocalDate tomorrow = NOW.toLocalDate().plusDays(1);
        Guest guest = willyGuestBuilder()
                .dateOfCheckOut(tomorrow)
                .build();
        assertThatThrownBy(() -> manager.createGuest(guest))
                .isInstanceOf(ValidationException.class);
    }
*/
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
                .build();
        manager.createGuest(guest);
        assertThat(manager.getGuest(guest.getId()))
                .isNotNull()
                .isEqualToComparingFieldByField(guest);
    }

    //--------------------------------------------------------------------------
    // Test for GuestManager find methods
    //--------------------------------------------------------------------------

    @Test
    public void findAllGuest() {
        assertThat(manager.findAllGuest()).isEmpty();

        Guest willy = willyGuestBuilder().build();
        Guest alice = aliceGuestBuilder().build();

        manager.createGuest(willy);
        manager.createGuest(alice);

        assertThat(manager.findAllGuest())
                .usingFieldByFieldElementComparator()
                .containsOnly(willy, alice);
    }

    @Test
    public void findGuestByName() {
        Guest willy = willyGuestBuilder().build();

        manager.createGuest(willy);

        assertThat(manager.findGuestByName(willy.getName()))
                .isNotNull()
                .isEqualToComparingFieldByField(willy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findGuestWithNullRoom() {
        manager.findGuestByRoom(null);
    }

    @Test(expected = IllegalEntityException.class)
    public void findGuestbyRoomWithNullId() {
        manager.findGuestByRoom(roomWithNullId);
    }


    //--------------------------------------------------------------------------
    // Test for GuestManager.deleteGuest(Guest)
    //--------------------------------------------------------------------------

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

    @Test
    public void findGuestByRoom() {
        assertThat(manager.findGuestByRoom(room1)).isNull();
        assertThat(manager.findGuestByRoom(room2)).isNull();
        assertThat(manager.findGuestByRoom(room3)).isNull();
        Guest guest = willyGuestBuilder().build();
        manager.createGuest(guest);

        assertThat(manager.findGuestByRoom(room1))
                .isEqualToComparingFieldByField(guest);
        assertThat(manager.findGuestByRoom(room2)).isNull();
        assertThat(manager.findGuestByRoom(room3)).isNull();
    }

    @Test
    public void freeRooms() {
        assertThat(manager.freeRooms())
                .usingFieldByFieldElementComparator()
                .containsOnly(room1, room2, room3);

        manager.createGuest(aliceGuestBuilder().build());
        manager.createGuest(willyGuestBuilder().build());

        assertThat(manager.freeRooms())
                .usingFieldByFieldElementComparator()
                .containsOnly(room3);
    }


    @Test
    public void checkOutGuest() {

        Guest guest = willyGuestBuilder().build();
        manager.createGuest(guest);
        assertThat(manager.findGuestByRoom(room1))
                .isEqualToComparingFieldByField(guest);
        Period period = Period.between(NOW.toLocalDate(), guest.getDateOfCheckIn());
        int price = period.getDays() * room1.getPrice();

        assertThat(manager.checkOutGuest(guest))
                .isEqualTo(price);
        assertThat(manager.findGuestByRoom(room1))
                .isNull();
    }

    //--------------------------------------------------------------------------
    // Tests if GuestManager methods throws ServiceFailureException in case of
    // DB operation failure
    //--------------------------------------------------------------------------

    @FunctionalInterface
    private static interface Operation<T> {
        void callOn(T subjectOfOperation);
    }


    @Test
    public void createGuestWithSqlExceptionThrown() throws SQLException {
        SQLException sqlException = new SQLException();
        DataSource failingDataSource = mock(DataSource.class);
        when(failingDataSource.getConnection()).thenThrow(sqlException);
        manager = new GuestManagerImpl(failingDataSource, prepareClockMock(NOW));

        Guest guest = willyGuestBuilder().build();

        assertThatThrownBy(() -> manager.createGuest(guest))
                .isInstanceOf(ServiceFailureException.class)
                .hasCause(sqlException);
    }


    private void testExpectedServiceFailureException(Operation<GuestManager> operation) throws SQLException {
        SQLException sqlException = new SQLException();
        DataSource failingDataSource = mock(DataSource.class);
        when(failingDataSource.getConnection()).thenThrow(sqlException);
        manager = new GuestManagerImpl(failingDataSource, prepareClockMock(NOW));
        assertThatThrownBy(() -> operation.callOn(manager))
                .isInstanceOf(ServiceFailureException.class)
                .hasCause(sqlException);
    }


    @Test
    public void getGuestWithSqlExceptionThrown() throws SQLException {
        Guest guest = willyGuestBuilder().build();
        manager.createGuest(guest);
        testExpectedServiceFailureException((guestManager) -> guestManager.getGuest(guest.getId()));
    }

    @Test
    public void deleteGuestWithSqlExceptionThrown() throws SQLException {
        Guest guest = willyGuestBuilder().build();
        manager.createGuest(guest);
        testExpectedServiceFailureException((guestManager) -> guestManager.deleteGuest(guest));
    }

    @Test
    public void findAllGuestsWithSqlExceptionThrown() throws SQLException {
        testExpectedServiceFailureException(GuestManager::findAllGuest);
    }

    @Test
    public void findGuestByRoomWithSqlExceptionThrown() throws SQLException {
        testExpectedServiceFailureException((guestManager) -> guestManager.findGuestByRoom(room1));
    }


    @Test
    public void checkOutGuestWithSqlExceptionThrown() throws SQLException {
        Guest guest = willyGuestBuilder().build();
        manager.createGuest(guest);
        testExpectedServiceFailureException((guestManager) -> guestManager.checkOutGuest(guest));
    }

    @Test
    public void freeRoomsWithSqlExceptionThrown() throws SQLException {
        testExpectedServiceFailureException(GuestManager::freeRooms);
    }


}