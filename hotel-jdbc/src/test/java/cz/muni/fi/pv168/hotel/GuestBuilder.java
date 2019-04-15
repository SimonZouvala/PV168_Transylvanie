package cz.muni.fi.pv168.hotel;

import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author Lýdie Hemalová (433757@mail.muni.cz)
 */
public class GuestBuilder {
        
    private Long id;
    private String name;
    private String phone;
    private LocalDate dateOfCheckIn;
    private LocalDate dateOfCheckOut;
    private Long room;

public GuestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public GuestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public GuestBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public GuestBuilder dateOfCheckIn(LocalDate dateOfCheckIn) {
        this.dateOfCheckIn = dateOfCheckIn;
        return this;
    }

    public GuestBuilder dateOfCheckIn(int year, Month month, int day) {
        this.dateOfCheckIn = LocalDate.of(year, month, day);
        return this;
    }

    public GuestBuilder dateOfCheckOut(int year, Month month, int day) {
        this.dateOfCheckOut = LocalDate.of(year, month, day);
        return this;
    }

    public GuestBuilder dateOfCheckOut(LocalDate dateOfCheckOut) {
        this.dateOfCheckOut = dateOfCheckOut;
        return this;
    }

    public GuestBuilder room(Long room) {
        this.room = room;
        return this;
    }

    public Guest build() {
        Guest guest = new Guest();
        guest.setId(id);
        guest.setName(name);
        guest.setPhone(phone);
        guest.setDateOfCheckIn(dateOfCheckIn);
        guest.setDateOfCheckOut(dateOfCheckOut);
        guest.setRoomId(room);
        return guest;
    }

}
