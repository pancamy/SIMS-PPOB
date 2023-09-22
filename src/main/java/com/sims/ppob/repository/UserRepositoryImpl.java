package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Users;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import io.jsonwebtoken.Jwts;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private ExceptionRepository exceptionRepository;

    private static final String INSERT_USERS_SQL = "INSERT INTO users"
            + " (id, email, first_name, last_name, password, created_at, updated_at) VALUES"
            + " (?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_BY_EMAIL_USERS_SQL = "SELECT id, email, first_name, last_name, password, created_at, updated_at"
            + " FROM users"
            + " WHERE email = ?";

    public void save(Users users) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, users.getId());
            preparedStatement.setString(2, users.getEmail());
            preparedStatement.setString(3, users.getFirstName());
            preparedStatement.setString(4, users.getLastName());
            preparedStatement.setString(5, users.getPassword());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(users.getCreatedAt()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(users.getUpdatedAt()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email sudah terdaftar");
            } else {
                ExceptionRepository.printSQLException(e);

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @Override
    public Users login(String email) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL_USERS_SQL)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            Users user = new Users();

            while (resultSet.next()) {
                user.setId(resultSet.getString("id"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setPassword(resultSet.getString("password"));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
            }

            return user;
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
