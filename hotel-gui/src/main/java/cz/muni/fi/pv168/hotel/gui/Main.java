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
import java.time.Clock;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lýdie Hemalová {433757@mail.muni.cz}
 */
public class Main {

    private final static Clock clock = Clock.systemDefaultZone();

    private final static Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        
    
        

        try {
            DataSource dataSource = cz.muni.fi.pv168.hotel.Main.getDataSource();
            RoomManager rmanager = new RoomManagerImpl(dataSource);
            GuestManager gmanager = new GuestManagerImpl(dataSource, clock);
            
            log.info("vytvořeny manažery");
            EventQueue.invokeLater(() -> {
            new MainWindow(rmanager, gmanager).setVisible(true);
            });        
        } catch (IOException e) {
            log.error("Nepovedlo se vytvořit databázi", e);
        }
        
        
        
        
    }
    
}
    

