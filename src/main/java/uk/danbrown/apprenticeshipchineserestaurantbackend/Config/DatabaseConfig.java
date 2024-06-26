package uk.danbrown.apprenticeshipchineserestaurantbackend.Config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.time.Duration;

public class DatabaseConfig {

    private final String url;
    private final String username;

    public DatabaseConfig(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username
    ) {
        this.url = url;
        this.username = username;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setSchema("cds");
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername(username);
        dataSource.setJdbcUrl(url);
        dataSource.setMaximumPoolSize(5);
        dataSource.setConnectionTimeout(Duration.ofMinutes(5).toMillis());
        dataSource.setIdleTimeout(Duration.ofMinutes(10).toMillis());
        dataSource.setMaxLifetime(Duration.ofMinutes(30).toMillis());
        return dataSource;
    }
}
