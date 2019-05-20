package cz.muni.fi.pv168.hotel.gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;

public class PropertiesOnClasspathExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Properties properties = loadPropertiesFromClassPath("configuration.properties");
        System.err.println(properties);

    }

    private static DataSource configureDataSource(Properties properties) {
        String path = properties.getProperty("configuration.db.path")
                .replace("${user.home}", System.getProperty("user.home"));
        
        EmbeddedDataSource ds = new EmbeddedDataSource();
        //we will use in memory database
        ds.setDatabaseName(path);
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
            throw new RuntimeException("Error when loading properties file from classpath: " + url , ex);
        }
    } 
    
}
