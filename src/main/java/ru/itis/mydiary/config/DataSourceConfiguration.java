package ru.itis.mydiary.config;

import com.mongodb.MongoClient;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.util.Properties;


@AllArgsConstructor
public class DataSourceConfiguration {

    private Properties properties;

    public DataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(properties.getProperty("postgres.url"));
        hikariConfig.setUsername(properties.getProperty("postgres.username"));
        hikariConfig.setPassword(properties.getProperty("postgres.password"));
        hikariConfig.setDriverClassName(properties.getProperty("postgres.driver-name"));
        hikariConfig.setMaximumPoolSize(
                Integer.parseInt(properties.getProperty("postgres.hikari.max-pool-size")));

        return new HikariDataSource(hikariConfig);
    }

    public MongoClient mongoClient() {
        String host = properties.getProperty("mongo.host");
        int port = Integer.parseInt(properties.getProperty("mongo.port"));
        return new MongoClient(host, port);
    }
}
