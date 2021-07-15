package io.muzoo.ssc.webapp.service;

import com.zaxxer.hikari.HikariDataSource;
import io.muzoo.ssc.webapp.config.ConfigProperties;
import io.muzoo.ssc.webapp.config.ConfigurationLoader;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionService {

    private final HikariDataSource ds;

    public DatabaseConnectionService() {
        ds = new HikariDataSource();
        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);

        ConfigProperties configProperties = ConfigurationLoader.load();

        if (configProperties == null){
            throw new RuntimeException("Unable to read config.properties")
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
}
