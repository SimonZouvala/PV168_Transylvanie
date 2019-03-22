package hotel;

import java.util.Objects;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class Room {
    private Long id;
    private int price;
    private int capacity;
    private int numberOfRoom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", price=" + price +
                ", capacity=" + capacity +
                ", numberOfRoom=" + numberOfRoom +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return numberOfRoom == room.numberOfRoom &&
                Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfRoom);
    }
}
