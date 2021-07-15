package io.muzoo.ssc.webapp.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationLoader {

    public static ConfigProperties load() {
        String configFilename = "config.properties";
        try (FileInputStream file = new FileInputStream(configFilename)){
            Properties prop = new Properties();
            prop.load(file);

            // get the property value and print it out
            String driverClassName = prop.getProperty("database.driverClassName");
            String connectionUrl = prop.getProperty("database.connectionUrl");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");


            ConfigProperties cp = new ConfigProperties();
            cp.setDatabaseDriverClassName(driverClassName);
            cp.setDatabaseConnectionUrl(connectionUrl);
            cp.setDatabaseUsername(username);
            cp.setDatabasePassword(password);
            return cp;


        } catch (Exception e) {return null;}
    }
}
