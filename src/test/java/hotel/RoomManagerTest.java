package hotel;

import exception.DBUtils;
import exception.IllegalEntityException;
import exception.ServiceFailureException;
import exception.ValidationException;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link RoomManagerImpl}
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class RoomManagerTest {

    private RoomManagerImpl manager;
    private DataSource ds;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static DataSource prepareDataSource() throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:gravemgr-test");
        ds.setCreateDatabase("create");
        return ds;
    }

    @Before
    public void setUp() throws Exception {
        ds = prepareDataSource();
        DBUtils.executeSqlScript(ds, RoomManager.class.getResourceAsStream("dropTables.sql"));
        manager = new RoomManagerImpl(ds);
    }

    @After
    public void tearDown() throws Exception {
        DBUtils.executeSqlScript(ds, RoomManager.class.getResourceAsStream("dropTables.sql"));
    }

    private RoomBuilder firstRoom() {
        return new RoomBuilder()
                .id(null)
                .capacity(5)
                .price(500)
                .numberOfRoom(1);

    }

    private RoomBuilder secondRoom() {
        return new RoomBuilder()
                .id(null)
                .capacity(5)
                .price(500)
                .numberOfRoom(2);
    }


    //--------------------------------------------------------------------------
    // Test for RoomManager.createRoom(Room)
    //--------------------------------------------------------------------------

    @Test
    public void createRoom() {
        Room room = firstRoom().build();
        manager.createRoom(room);

        Long roomId = room.getId();
        assertThat(roomId).isNotNull();

        assertThat(manager.getRoom(roomId))
                .isNotSameAs(room)
                .isEqualToComparingFieldByField(room);
    }

    @Test
    public void createGuestWithExistingId() {
        Room room = firstRoom()
                .id(1L)
                .build();

        expectedException.expect(IllegalEntityException.class);
        manager.createRoom(room);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullRoom() {
        manager.createRoom(null);
    }

    @Test
    public void createGraveWithNegativePrice() {
        Room room = firstRoom().price(-100).build();
        assertThatThrownBy(() -> manager.createRoom(room))
                .isInstanceOf(ValidationException.class);
    }


    @Test
    public void createRoomWithNegativeNumberOfRoom() {
        Room room = firstRoom().numberOfRoom(-1).build();
        assertThatThrownBy(() -> manager.createRoom(room))
                .isInstanceOf(ValidationException.class);

    }

    @Test
    public void createRoomWithSameNumberOfRoom() {
        Room r1 = firstRoom().build();
        Room r2 = secondRoom().numberOfRoom(1).build();

        manager.createRoom(r1);

        assertThatThrownBy(() -> manager.createRoom(r2))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void createRoomWithNegativeCapacity() {
        Room room = firstRoom().capacity(-1).build();
        assertThatThrownBy(() -> manager.createRoom(room))
                .isInstanceOf(ValidationException.class);

    }
    //--------------------------------------------------------------------------
    // Test for RoomManager.deleteRoom(Room)
    //--------------------------------------------------------------------------


    @Test
    public void deleteRoom() {
        Room r1 = firstRoom().build();
        Room r2 = secondRoom().build();

        manager.createRoom(r1);
        manager.createRoom(r2);

        manager.deleteRoom(r1);

        assertThat(manager.findAllRooms()).usingFieldByFieldElementComparator().doesNotContain(r1);
        assertThat(manager.findAllRooms()).usingFieldByFieldElementComparator().containsOnly(r2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNullRoom() {
        manager.deleteRoom(null);
    }

    @Test
    public void deleteRoomWithNullId() {
        Room room = firstRoom().id(null).build();
        expectedException.expect(IllegalEntityException.class);
        manager.deleteRoom(room);
    }

    @Test
    public void deleteNonExistRoom() {
        Room room = firstRoom().id(2L).build();
        expectedException.expect(IllegalEntityException.class);
        manager.deleteRoom(room);
    }


    //--------------------------------------------------------------------------
    // Test for RoomManager.findAllRoom()
    //--------------------------------------------------------------------------

    @Test
    public void findAllRooms() {
        assertThat(manager.findAllRooms()).isEmpty();

        Room r1 = firstRoom().build();
        Room r2 = secondRoom().build();

        manager.createRoom(r1);
        manager.createRoom(r2);

        assertThat(manager.findAllRooms()).usingFieldByFieldElementComparator().containsOnly(r1, r2);
    }

    //--------------------------------------------------------------------------
    // Tests if RoomManager methods throws ServiceFailureException in case of
    // DB operation failure
    //--------------------------------------------------------------------------

    @FunctionalInterface
    private static interface Operation<T> {
        void callOn(T subjectOfOperation);
    }


    @Test
    public void createRoomWithSqlExceptionThrown() throws SQLException {
        SQLException sqlException = new SQLException();
        DataSource failingDataSource = mock(DataSource.class);
        when(failingDataSource.getConnection()).thenThrow(sqlException);
        manager = new RoomManagerImpl(failingDataSource);

        Room room = firstRoom().build();

        assertThatThrownBy(() -> manager.createRoom(room))
                .isInstanceOf(ServiceFailureException.class)
                .hasCause(sqlException);
    }


    private void testExpectedServiceFailureException(Operation<RoomManager> operation) throws SQLException {
        SQLException sqlException = new SQLException();
        DataSource failingDataSource = mock(DataSource.class);
        when(failingDataSource.getConnection()).thenThrow(sqlException);
        manager = new RoomManagerImpl(failingDataSource);
        assertThatThrownBy(() -> operation.callOn(manager))
                .isInstanceOf(ServiceFailureException.class)
                .hasCause(sqlException);
    }


    @Test
    public void getRoomWithSqlExceptionThrown() throws SQLException {
        Room room = firstRoom().build();
        manager.createRoom(room);
        testExpectedServiceFailureException((RoomManager) -> RoomManager.getRoom(room.getId()));
    }

    @Test
    public void deleteRoomWithSqlExceptionThrown() throws SQLException {
        Room room = firstRoom().build();
        manager.createRoom(room);
        testExpectedServiceFailureException((RoomManager) -> RoomManager.deleteRoom(room));
    }

    @Test
    public void findAllRoomsWithSqlExceptionThrown() throws SQLException {
        testExpectedServiceFailureException(RoomManager::findAllRooms);
    }
}