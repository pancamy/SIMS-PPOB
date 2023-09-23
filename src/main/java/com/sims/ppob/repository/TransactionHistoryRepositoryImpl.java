package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.TransactionHistories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class TransactionHistoryRepositoryImpl implements TransactionHistoryRepository {

    private ExceptionRepository exceptionRepository;

    private static final String INSERT_TRANCATION_HISROIES_SQL = "INSERT INTO transaction_histories"
            + " (id, invoice_number, transaction_type, description, total_amount, created_at, updated_at) VALUES"
            + " (?, ?, ?, ?, ?, ?, ?);";

    @Override
    public void save(TransactionHistories transactionHistory) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANCATION_HISROIES_SQL)) {
            preparedStatement.setString(1, transactionHistory.getId());
            preparedStatement.setString(2, transactionHistory.getInvoiceNumber());
            preparedStatement.setString(3, transactionHistory.getTransactionType().toString());
            preparedStatement.setString(4, transactionHistory.getDescription());
            preparedStatement.setLong(5, transactionHistory.getTotalAmount());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(transactionHistory.getCreatedAt()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(transactionHistory.getUpdatedAt()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
