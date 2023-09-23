package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Balances;
import com.sims.ppob.entity.Users;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;

@Repository
public class BalanceRepositoryImpl implements BalanceRepository{

    private ExceptionRepository exceptionRepository;

    private static final String SELECT_BY_ID_USERS_SQL = "SELECT id, email, first_name, last_name, profile, created_at, updated_at"
            + " FROM users"
            + " WHERE id = ?";

    private static final String SELECT_ALL_BALANCE_SQL = "SELECT id, balance, user_id, created_at, updated_at"
            + " FROM balances"
            + " WHERE user_id = ?";

    private static final String INSERT_BALANCES_SQL = "INSERT INTO balances"
            + " (id, balance, user_id, created_at, updated_at) VALUES"
            + " (?, ?, ?, ?, ?);";

    @Override
    public Balances getByUserId(String userId) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BALANCE_SQL)) {
            preparedStatement.setString(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            Balances balance = new Balances();

            while (resultSet.next()) {
                balance.setId(resultSet.getString("id"));
                balance.setBalance(resultSet.getLong("balance"));
                balance.setUsers(getUser(resultSet.getString("user_id")));
                balance.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                balance.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
            }

            return balance;
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void save(Balances balance) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BALANCES_SQL)) {
            preparedStatement.setString(1, balance.getId());
            preparedStatement.setLong(2, balance.getBalance());
            preparedStatement.setString(3, balance.getUsers().getId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(balance.getCreatedAt()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(balance.getUpdatedAt()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public Users getUser(String id) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_USERS_SQL)) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            Users user = new Users();

            while (resultSet.next()) {
                user.setId(resultSet.getString("id"));
                user.setEmail(resultSet.getString("email"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setProfile(resultSet.getString("profile"));
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
