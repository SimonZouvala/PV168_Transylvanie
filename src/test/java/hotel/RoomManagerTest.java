package hotel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Test class for {@link RoomManagerImpl}
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class RoomManagerTest {

    private RoomManagerImpl manager;

    @Before
    public void setUp() throws Exception {
        manager = new RoomManagerImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    private RoomBuilder firstRoom() {
        return new RoomBuilder()
                .id(null)
                .capacity(0)
                .price(0)
                .numberOfRoom(0);

    }

    private RoomBuilder secondRoom() {
        return new RoomBuilder()
                .id(null)
                .capacity(0)
                .price(0)
                .numberOfRoom(0);
    }

    @Test
    public void createRoom() {
        Room room = firstRoom().build();

        manager.createRoom(room);

        idIsNotNull(room);
        capacityIsGreaterThanZero(room);
        priceIsGreaterThenZero(room);

    }

    @Test
    public void deleteRoom() {
        Room r1 = firstRoom().build();
        Room r2 = secondRoom().build();

        manager.createRoom(r1);
        manager.createRoom(r2);

        manager.deleteRoom(r1);

        assertThat(manager.findAllRoom()).usingFieldByFieldElementComparator().doesNotContain(r1);
        assertThat(manager.findAllRoom()).usingFieldByFieldElementComparator().containsOnly(r2);

    }

    @Test
    public void findAllRoom() {
        assertThat(manager.findAllRoom()).isEmpty();

        Room r1 = firstRoom().build();
        Room r2 = secondRoom().build();

        manager.createRoom(r1);
        manager.createRoom(r2);

        assertThat(manager.findAllRoom()).usingFieldByFieldElementComparator().containsOnly(r1, r2);
        numberOfRoomIsDeferent(r1,r2);
    }

    private void capacityIsGreaterThanZero(Room room) {
        assertThat(room.getCapacity()).isGreaterThan(0);
    }

    private void idIsNotNull(Room room) {
        assertThat(room.getId()).isNotNull();
    }

    private void priceIsGreaterThenZero(Room room) {
        assertThat(room.getPrice()).isGreaterThan(0);
    }

    private void numberOfRoomIsDeferent(Room r1, Room r2) {
        assertThat(r1.getNumberOfRoom()).isNotEqualTo(r2.getNumberOfRoom());
    }

}