package com.sims.ppob.repository;

import com.sims.ppob.application.database.DbConnection;
import com.sims.ppob.entity.Banners;
import com.sims.ppob.entity.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BannerRepositoryImpl implements BannerRepository {

    private ExceptionRepository exceptionRepository;

    private static final String SELECT_ALL_USERS_SQL = "SELECT id, banner_image, banner_name, description, created_at, updated_at"
            + " FROM banners";

    public List<Banners> getAll() {
        List<Banners> banners = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_SQL)) {
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
}
