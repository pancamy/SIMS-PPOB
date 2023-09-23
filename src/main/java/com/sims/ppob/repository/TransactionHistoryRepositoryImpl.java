package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Banners;
import com.sims.ppob.entity.TransactionHistories;
import com.sims.ppob.model.PagingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class TransactionHistoryRepositoryImpl implements TransactionHistoryRepository {

    private ExceptionRepository exceptionRepository;

    private static final String INSERT_TRANSACTION_HISTORIES_SQL = "INSERT INTO transaction_histories"
            + " (id, invoice_number, transaction_type, description, total_amount, created_at, updated_at) VALUES"
            + " (?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ALL_TRANSACTION_HISTORIES_SQL = "SELECT id, invoice_number, transaction_type, description, total_amount, created_at, updated_at"
            + " FROM transaction_histories"
            + " ORDER BY created_at DESC"
            + " OFFSET ?"
            + " %s";

    @Override
    public void save(TransactionHistories transactionHistory) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_HISTORIES_SQL)) {
            preparedStatement.setString(1, transactionHistory.getId());
            preparedStatement.setString(2, transactionHistory.getInvoiceNumber());
            preparedStatement.setString(3, transactionHistory.getTransactionType());
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

    @Override
    public List<TransactionHistories> getAll(PagingRequest pagingRequest) {
        List<TransactionHistories> transactionHistories = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     String.format(SELECT_ALL_TRANSACTION_HISTORIES_SQL,
                             (pagingRequest.getLimit() > 0) ? "LIMIT ?" : "")
             )) {
            preparedStatement.setInt(1, pagingRequest.getOffset());

            if (pagingRequest.getLimit() > 0) {
                preparedStatement.setInt(2, pagingRequest.getLimit());
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TransactionHistories transactionHistory = new TransactionHistories();
                transactionHistory.setId(resultSet.getString("id"));
                transactionHistory.setInvoiceNumber(resultSet.getString("invoice_number"));
                transactionHistory.setTransactionType(resultSet.getString("transaction_type"));
                transactionHistory.setDescription(resultSet.getString("description"));
                transactionHistory.setTotalAmount(resultSet.getLong("total_amount"));
                transactionHistory.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                transactionHistory.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                transactionHistories.add(transactionHistory);
            }
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return transactionHistories;
    }
}
