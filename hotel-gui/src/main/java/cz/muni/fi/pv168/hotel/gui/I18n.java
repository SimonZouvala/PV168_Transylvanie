package cz.muni.fi.pv168.hotel.gui;

import java.util.ResourceBundle;

public class I18n {

    private final ResourceBundle bundle;
    private final Class<?> clazz;
    private final boolean debug;
    
    public I18n(Class<?> clazz) {
        String bundleName = clazz.getPackage().getName() + ".HotelBundle"; 
        this.bundle = ResourceBundle.getBundle(bundleName);
        this.clazz = clazz;
        this.debug = false;
    }
    
    public String getString(String key) {
        String fullKey = clazz.getSimpleName() + "." + key;
        String result = bundle.getString(fullKey);
        if (debug) {
            result = fullKey + result;
        }
        return result;
    }
    
    public String getString(Enum<?> key) {
        return getString(key.getDeclaringClass().getSimpleName() 
                + "." + key.name());
    }

}
