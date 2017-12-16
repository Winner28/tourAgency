package com.epam.lab.ultimatewebservice.db.connpool;

import java.util.Locale;
import java.util.ResourceBundle;


public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();
    private final static ResourceBundle bundle =
            ResourceBundle.getBundle("DBResources", new Locale("en"));

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
