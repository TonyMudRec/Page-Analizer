package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseRepository {

    @Setter
    private static HikariDataSource dataSource;

    public static PreparedStatement getStatement(String sql) throws SQLException {
        return dataSource
                .getConnection()
                .prepareStatement(sql);
    }

    public static PreparedStatement getStatement(String sql, int constant) throws SQLException {
        return dataSource
                .getConnection()
                .prepareStatement(sql, constant);
    }
}
