package hexlet.code.repository;

import hexlet.code.model.Url;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * repository for urls.
 */
public class UrlRepository extends BaseRepository {

    public static void save(@NotNull Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.setTimestamp(2, url.getCreatedAt());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * @param name
     * @return url
     * @throws SQLException
     */
    public static @Nullable Url find(String name) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Url url = new Url(name, createdAt);
                url.setId(id);
                return url;
            }
            return null;
        }
    }

    /**
     * @param id
     * @return url
     * @throws SQLException
     */
    public static @Nullable Url find(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Url url = new Url(name, createdAt);
                url.setId(id);
                return url;
            }
            return null;
        }
    }

    /**
     * @return list of urls with checks.
     * @throws SQLException
     */
    public static @NotNull List<Url> getEntities() throws SQLException {
        String sql = "SELECT u.id, u.name, u.created_at, max(uc.created_at) as last_check "
                + "FROM urls u LEFT JOIN url_checks uc on uc.url_id = u.id "
                + "GROUP BY u.id, u.name, u.created_at";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Url> urls = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Timestamp lastCheck = resultSet.getTimestamp("last_check");
                Url url = new Url(name, createdAt, lastCheck);
                url.setId(id);
                urls.add(url);
            }
            return urls;
        }
    }
}
