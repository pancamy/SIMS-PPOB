package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Services;
import com.sims.ppob.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository{

    private ExceptionRepository exceptionRepository;

    private static final String SELECT_ALL_SERVICES_SQL = "SELECT id, service_code, service_name, service_tariff, service_icon, created_at, updated_at"
            + " FROM services";

    private static final String GET_BY_STATUS_CODE_SERVICES_SQL = "SELECT id, service_code, service_name, service_tariff, service_icon, created_at, updated_at"
            + " FROM services"
            + " WHERE service_code = ?";

    private static final String INSERT_SERVICES_SQL = "INSERT INTO services"
            + " (id, service_code, service_name, service_tariff, service_icon, created_at, updated_at) VALUES"
            + " (?, ?, ?, ?, ?, ?, ?);";

    @Override
    public List<Services> getAll() {
        List<Services> services = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SERVICES_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Services service = new Services();
                service.setId(resultSet.getString("id"));
                service.setServiceCode(resultSet.getString("service_code"));
                service.setServiceName(resultSet.getString("service_name"));
                service.setServiceTariff(resultSet.getLong("service_tariff"));
                service.setServiceIcon(resultSet.getString("service_icon"));
                service.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                service.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                services.add(service);
            }
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return services;
    }

    @Override
    public Services getByStatusCode(String serviceCode) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_STATUS_CODE_SERVICES_SQL)) {
            preparedStatement.setString(1, serviceCode);

            ResultSet resultSet = preparedStatement.executeQuery();

            Services service = new Services();

            while (resultSet.next()) {
                service.setId(resultSet.getString("id"));
                service.setServiceCode(resultSet.getString("service_code"));
                service.setServiceName(resultSet.getString("service_name"));
                service.setServiceTariff(resultSet.getLong("service_tariff"));
                service.setServiceIcon(resultSet.getString("service_icon"));
                service.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                service.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
            }

            return service;
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void save(Services service) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SERVICES_SQL)) {
            preparedStatement.setString(1, service.getId());
            preparedStatement.setString(2, service.getServiceCode());
            preparedStatement.setString(3, service.getServiceName());
            preparedStatement.setLong(4, service.getServiceTariff());
            preparedStatement.setString(5, service.getServiceIcon());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(service.getCreatedAt()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(service.getUpdatedAt()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
