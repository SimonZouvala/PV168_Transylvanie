package cz.muni.fi.pv168.hotel;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.Clock;
import java.util.List;
import java.util.Properties;

public class Main {
    private static Clock clock = Clock.systemDefaultZone();

    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static DataSource getDataSource() throws IOException {
        HikariDataSource ds = new HikariDataSource();

        //load connection properties from a file
        Properties p = new Properties();
        p.load(Main.class.getResourceAsStream("/jdbc.properties"));

        //set connection
        ds.setDriverClassName(p.getProperty("jdbc.driver"));
        ds.setJdbcUrl(p.getProperty("jdbc.url"));
        ds.setUsername(p.getProperty("jdbc.user"));
        ds.setPassword(p.getProperty("jdbc.password"));

        //populate db with tables and data
        new ResourceDatabasePopulator(
                new ClassPathResource("createTables.sql"),
                new ClassPathResource("test-data.sql"))
                .execute(ds);
        return ds;
    }

    public static void main(String[] args) throws Exception, IOException {


        log.info("start");

        DataSource dataSource = getDataSource();
        RoomManager roomManager = new RoomManagerImpl(dataSource);
        GuestManager guestManager = new GuestManagerImpl(dataSource, clock);

        List<Room> allRooms = roomManager.findAllRooms();
        System.out.println("allRooms = " + allRooms);

        List<Guest> allGuests = guestManager.findAllGuest();
        System.out.println("All guests = " + allGuests);

    }

}
