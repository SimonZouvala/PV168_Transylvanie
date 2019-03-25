package hotel;

import exception.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import static java.time.Month.*;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 *
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class AccommodationTest {
    
    private AccommodationImpl manager;
    private GuestManagerImpl guestManager;
    private RoomManagerImpl roomManager;

    private final static ZonedDateTime NOW
            = LocalDateTime.of(2019, Month.MARCH, 24, 14, 00).atZone(ZoneId.of("UTC"));
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static DataSource prepareDataSource() throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:gravemgr-test");
        ds.setCreateDatabase("create");
        return ds;
    }

    @Before
    // databaze
            
    @After
    public void tearDown() throws SQLException, IOException {
       // databaze
    }
    
    private Room r1, r2, r3, roomWithNullId, roomNotInDB ;
    private Guest g1, g2, g3,g4, guestWithNullId, guestNotInDB;
    
    private void prepareTestData() {

        r1 = new RoomBuilder().price(500).capacity(5).numberOfRoom(1).build();
        r2 = new RoomBuilder().price(500).capacity(5).numberOfRoom(2).build();
        r3 = new RoomBuilder().price(500).capacity(5).numberOfRoom(3).build();

        g1 = new GuestBuilder().name("Willy").phone("205050150").dateOfCheckIn(2019,MARCH,21).build();
        g2 = new GuestBuilder().name("Berta").phone("454875487").dateOfCheckIn(2019,MARCH,19).build();
        g3 = new GuestBuilder().name("Vlad").phone("956421478").dateOfCheckIn(2019,MARCH,23).build();
        g4 = new GuestBuilder().name("Nessi").phone("956425578").dateOfCheckIn(2019,MARCH,22).build();

        guestManager.createGuest(g1);
        guestManager.createGuest(g2);
        guestManager.createGuest(g3);

        roomManager.createRoom(r1);
        roomManager.createRoom(r2);
        roomManager.createRoom(r3);

        roomWithNullId = new RoomBuilder().id(null).build();
        roomNotInDB = new RoomBuilder().id(r3.getId() + 100).build();
        assertThat(roomManager.getRoom(roomNotInDB.getId())).isNull();

        guestWithNullId = new GuestBuilder().name("Null id").phone("455278").id(null).build();
        guestNotInDB = new GuestBuilder().name("Not in DB").phone("55454").id(g3.getId() + 100).build();
        assertThat(guestManager.getGuest(guestNotInDB.getId())).isNull();
    }
    @Test
    public void findGuestByRoom(){
        assertThat(manager.findGuestByRoom(r1)).isNull();
        assertThat(manager.findGuestByRoom(r2)).isNull();
        assertThat(manager.findGuestByRoom(r3)).isNull(); 

        manager.addGuest(g1, r3);

        assertThat(manager.findGuestByRoom(r3))
                .isEqualToComparingFieldByField(g1);
        assertThat(manager.findGuestByRoom(r2)).isNull();
        assertThat(manager.findGuestByRoom(r1)).isNull();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void findGuestWithNullRoom() {
        manager.findGuestByRoom(null);
    }
    
    @Test(expected = IllegalEntityException.class)
    public void findGuestbyRoomWithNullId() {
        manager.findGuestByRoom(roomWithNullId);
    }
    
    @Test
    public void addGuest(){
        assertThat(manager.findGuestByRoom(r1)).isNull();
        assertThat(manager.findGuestByRoom(r2)).isNull();
        assertThat(manager.findGuestByRoom(r3)).isNull();

        //muzeme pridat do pokoje vice hostu? ze ne, tak proc findGuestByRoom vraci list
        manager.addGuest(g1, r3);
        manager.addGuest(g3, r1);
       // manager.addGuest(g2, r3);

        assertThat(manager.findGuestByRoom(r1))
                .isEqualToComparingFieldByField(g3);
        assertThat(manager.findGuestByRoom(r2))
                .isNull();
        assertThat(manager.findGuestByRoom(r3))
                .isEqualToComparingFieldByField(g1);
        
    }
    @Test
    public void addGuestToFullRoom(){
        manager.addGuest(g1, r3);
        manager.addGuest(g3, r1);
        
        assertThatThrownBy(() -> manager.addGuest(g4, r3))
                .isInstanceOf(IllegalEntityException.class);
        expectedException.expect(ValidationException.class);
        
        
         assertThat(manager.findGuestByRoom(r1))
                .isEqualToComparingFieldByField(g3);
        assertThat(manager.findGuestByRoom(r2))
                .isNull();
        assertThat(manager.findGuestByRoom(r3))
                .isEqualToComparingFieldByField(g1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void putNullGuestIntoRoom() {
        manager.addGuest(null, r2);
    }
      @Test(expected = IllegalEntityException.class)
    public void putGuestWithNullIdIntoRoom() {
        manager.addGuest(guestWithNullId, r2);
    }

    @Test(expected = IllegalEntityException.class)
    public void putGuestNotInDBIntoRoom() {
        manager.addGuest(guestNotInDB, r2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putGuestIntoNullRoom() {
        manager.addGuest(g2, null);
    }

    @Test(expected = IllegalEntityException.class)
    public void putGuestIntoRoomWithNullId() {
        manager.addGuest(g2, roomWithNullId);
    }

    @Test(expected = IllegalEntityException.class)
    public void putGuestIntoRoomNotInDB() {
        manager.addGuest(g2, roomNotInDB);
    }
    
    @Test
    public void checkOutGuest(){
        manager.addGuest(g1, r3);
        assertThat(manager.findGuestByRoom(r3))
                .isEqualToComparingFieldByField(g1);
        int day = NOW.getDayOfMonth()-g1.getDateOfCheckIn().getDayOfMonth();
        int price = day*r3.getPrice();
        
        assertThat(manager.checkOutGuest(r3))
                .isEqualToComparingFieldByField(price);
        assertThat(manager.findGuestByRoom(r3))
                .isNull();
    }
    

    @Test
    public void freeRooms(){
        assertThat(manager.freeRooms())
                .usingFieldByFieldElementComparator()
                .containsOnly(r1,r2,r3);

        manager.addGuest(g1, r3);
        manager.addGuest(g2, r3);
        manager.addGuest(g3, r1);

        assertThat(manager.freeRooms())
                .usingFieldByFieldElementComparator()
                .containsOnly(r2);
    }    
    
    
}
