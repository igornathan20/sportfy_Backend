package com.sportfy.sportfy.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.web.client.RestTemplate;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Configurations {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner migrateDatabase(Flyway flyway, PasswordEncoder passwordEncoder, DataSource dataSource) {
        return args -> {
            flyway.migrate();

            String selectQuery = "SELECT username, nome FROM usuario WHERE password = 'pass'";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = selectStatement.executeQuery()) {

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = "pass";
                    String encodedPassword = passwordEncoder.encode(password);

                    String updateQuery = "UPDATE usuario SET password = ? WHERE username = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, encodedPassword);
                        updateStatement.setString(2, username);
                        updateStatement.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }

}
