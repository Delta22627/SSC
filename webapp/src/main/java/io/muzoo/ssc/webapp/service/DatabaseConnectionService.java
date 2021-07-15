package io.muzoo.ssc.webapp.service;

import com.zaxxer.hikari.HikariDataSource;
import io.muzoo.ssc.webapp.config.ConfigProperties;
import io.muzoo.ssc.webapp.config.ConfigurationLoader;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionService {
    private static DatabaseConnectionService service;

    private final HikariDataSource ds;

    /**
     * Database connection pool using hikari library
     * The secret and variables are loaded from disk
     * file.config
     */
    private DatabaseConnectionService() {
        ds = new HikariDataSource();
        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);

        ConfigProperties configProperties = ConfigurationLoader.load();

        if (configProperties == null){
            throw new RuntimeException("Unable to read config.properties");
        }
        ds.setDriverClassName(configProperties.getDatabaseDriverClassName());
        ds.setJdbcUrl(configProperties.getDatabaseConnectionUrl());
        ds.addDataSourceProperty("user", configProperties.getDatabaseUsername());
        ds.addDataSourceProperty("password", configProperties.getDatabasePassword());
        ds.setAutoCommit(false);

    }

    public Connection getConnection() throws SQLException{
        return ds.getConnection();
    }

    public static DatabaseConnectionService getInstance(){
        if (service == null){
            service = new DatabaseConnectionService();
        }
        return service;
    }
}
