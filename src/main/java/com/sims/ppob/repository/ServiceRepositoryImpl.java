package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Services;
import com.sims.ppob.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository{

    private ExceptionRepository exceptionRepository;

    private static final String SELECT_ALL_SERVICES_SQL = "SELECT id, service_code, service_name, service_tariff, service_icon, created_at, updated_at"
            + " FROM services";

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
}
