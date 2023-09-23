package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Transactions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

    private ExceptionRepository exceptionRepository;

    private static final String INSERT_TRANSACTIONS_SQL = "INSERT INTO transactions"
            + " (id, invoice_number, transaction_type, balance_id, service_id, user_id, created_at, updated_at) VALUES"
            + " (?, ?, ?, ?, ?, ?, ?, ?);";

    @Override
    public void save(Transactions transaction) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTIONS_SQL)) {
            preparedStatement.setString(1, transaction.getId());
            preparedStatement.setString(2, transaction.getInvoiceNumber());
            preparedStatement.setString(3, transaction.getTransactionType());
            preparedStatement.setString(4, transaction.getBalance().getId());
            preparedStatement.setString(5, transaction.getServices().getId());
            preparedStatement.setString(6, transaction.getUser().getId());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(transaction.getCreatedAt()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(transaction.getUpdatedAt()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
