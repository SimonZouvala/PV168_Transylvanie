package cz.muni.fi.pv168.hotel;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class Guest {
    private Long id;
    private String name;
    private String phone;
    private LocalDate dateOfCheckIn;
    private LocalDate dateOfCheckOut;
    private Long room;

    public Guest() {
    }

    public Guest(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfCheckIn() {
        return dateOfCheckIn;
    }

    public void setDateOfCheckIn(LocalDate dateOfCheckIn) {
        this.dateOfCheckIn = dateOfCheckIn;
    }

    public LocalDate getDateOfCheckOut() {
        return dateOfCheckOut;
    }

    public void setDateOfCheckOut(LocalDate dateOfCheckOut) {
        this.dateOfCheckOut = dateOfCheckOut;
    }

    public Long getRoomId() {
        return room;
    }

    public void setRoomId(Long room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return " ID: " + id + 
                "\n Name: " + name +
                "\n Phone: " + phone +
                "\n Date of check in: " + dateOfCheckIn +
                "\n Date of check out: " + dateOfCheckOut +
                "\n Room: " + room;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(id, guest.id) &&
                Objects.equals(name, guest.name) &&
                Objects.equals(phone, guest.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone);
    }

    void getPhone(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

