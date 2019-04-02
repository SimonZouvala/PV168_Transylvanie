package hotel;

/**
 * This is builder for the {@link Room} class to make tests better readable.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class RoomBuilder {

    private Long id;
    private int price;
    private int capacity;
    private int number;

    public RoomBuilder id(Long id){
        this.id = id;
        return this;
    }
    public RoomBuilder price(int price){
        this.price = price;
        return this;
    }
    public RoomBuilder capacity(int capacity){
        this.capacity = capacity;
        return this;
    }

    public RoomBuilder number(int number){
        this.number = number;
        return this;
    }

    public Room build() {
        Room room = new Room();
        room.setId(id);
        room.setPrice(price);
        room.setCapacity(capacity);
        room.setNumber(number);
        return room;
    }
}
