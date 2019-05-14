package cz.muni.fi.pv168.hotel;

import java.util.Objects;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class Room {
    private Long id;
    private int price;
    private int capacity;
    private int number;

    public Room() {
         }

    public Room(int price, int capacity, int number) {
       this.price = price;
       this.capacity = capacity;
       this.number = number;
    }

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int numberOfRoom) {
        this.number = numberOfRoom;
    }

    @Override
    public String toString() {
        return " ID: " + id +
                "\n Price: " + price +
                "\n Capacity: " + capacity +
                "\n Number: " + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return number == room.number &&
                Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}
