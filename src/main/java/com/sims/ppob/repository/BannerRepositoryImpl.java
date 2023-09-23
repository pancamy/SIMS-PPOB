package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Banners;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BannerRepositoryImpl implements BannerRepository {

    private ExceptionRepository exceptionRepository;

    private static final String SELECT_ALL_BANNERS_SQL = "SELECT id, banner_image, banner_name, description, created_at, updated_at"
            + " FROM banners";

    private static final String INSERT_BANNERS_SQL = "INSERT INTO banners"
            + " (id, banner_name, description, banner_image, created_at, updated_at) VALUES"
            + " (?, ?, ?, ?, ?, ?);";

    public List<Banners> getAll() {
        List<Banners> banners = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BANNERS_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Banners banner = new Banners();
                banner.setId(resultSet.getString("id"));
                banner.setBannerImage(resultSet.getString("banner_image"));
                banner.setBannerName(resultSet.getString("banner_name"));
                banner.setDescription(resultSet.getString("description"));
                banner.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                banner.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                banners.add(banner);
            }
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return banners;
    }

    @Override
    public void save(Banners banner) {
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BANNERS_SQL)) {
            preparedStatement.setString(1, banner.getId());
            preparedStatement.setString(2, banner.getBannerName());
            preparedStatement.setString(3, banner.getDescription());
            preparedStatement.setString(4, banner.getBannerImage());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(banner.getCreatedAt()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(banner.getUpdatedAt()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            ExceptionRepository.printSQLException(e);

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
