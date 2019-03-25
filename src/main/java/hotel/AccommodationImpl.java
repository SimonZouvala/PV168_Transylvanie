/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel;

import javax.sql.DataSource;
import java.util.List;

/**
 *
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class AccommodationImpl implements Accommodation{

    private DataSource dataSource;

    @SuppressWarnings("WeakerAccess")
    public AccommodationImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Guest findGuestByRoom(Room room){
        return null;
    }

    public void addGuest(Guest guest, Room room){
    }

    public int checkOutGuest(Room room){
        return 1;
    }

    public List<Room> freeRooms(){
        return null;
    }
}
