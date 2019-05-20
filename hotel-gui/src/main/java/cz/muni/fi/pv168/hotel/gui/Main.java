/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.hotel.gui;

import cz.muni.fi.pv168.hotel.GuestManager;
import cz.muni.fi.pv168.hotel.GuestManagerImpl;
import cz.muni.fi.pv168.hotel.RoomManager;
import cz.muni.fi.pv168.hotel.RoomManagerImpl;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.Clock;
import java.util.Properties;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class Main {

    private final static Clock clock = Clock.systemDefaultZone();

    private final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        DataSource dataSource;
        dataSource = configureDataSource(loadPropertiesFromClassPath("jdbc.properties"));
        //        Properties properties = loadPropertiesFromClassPath("jdbc.properties");
//        System.err.println(properties);
//        DataSource dataSource = configureDataSource(properties);
        RoomManager rmanager = new RoomManagerImpl(dataSource);
        GuestManager gmanager = new GuestManagerImpl(dataSource, clock);
        log.info("vytvořeny manažery");
        EventQueue.invokeLater(() -> {
            new MainWindow(rmanager, gmanager).setVisible(true);
        });

    }

    private static DataSource configureDataSource(Properties properties) {
        String path = properties.getProperty("jdbc.path")
                .replace("${user.home}", System.getProperty("user.home"));

        EmbeddedDataSource ds = new EmbeddedDataSource();
        //we will use in memory database
        ds.setDatabaseName("hotelDB");
        // database is created automatically if it does not exist yet
        ds.setCreateDatabase("create");
        return ds;
        //return null;
    }

    private static Properties loadPropertiesFromClassPath(String propertiesFileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        ClassLoader classLoader = PropertiesOnClasspathExample.class.getClassLoader();
        URL url = classLoader.getResource(propertiesFileName);
        if (url == null) {
            throw new IllegalArgumentException("properties file not found on classpath: " + propertiesFileName);
        }
        try (InputStream is = url.openStream()) {
            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (IOException ex) {
            throw new RuntimeException("Error when loading properties file from classpath: " + url, ex);
        }
    }
}
