package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private ExceptionRepository exceptionRepository;

    private static final String INSERT_USERS_SQL = "INSERT INTO users"
            + " (id, email, first_name, last_name, password, created_at, updated_at) VALUES"
            + " (?, ?, ?, ?, ?, ?, ?);";

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertQuery(Users users) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, users.getId());
            preparedStatement.setString(2, users.getEmail());
            preparedStatement.setString(3, users.getFirstName());
            preparedStatement.setString(4, users.getLastName());
            preparedStatement.setString(5, users.getPassword());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(users.getCreatedAt()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(users.getUpdatedAt()));

            System.out.println(preparedStatement);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email sudah terdaftar");
            } else {
                ExceptionRepository.printSQLException(e);

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Silahkan menghubungi penyedia layanan");
            }
        }
    }
}
