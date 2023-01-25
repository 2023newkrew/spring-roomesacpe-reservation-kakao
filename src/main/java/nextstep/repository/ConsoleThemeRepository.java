package nextstep.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import nextstep.model.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class ConsoleThemeRepository implements ThemeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleThemeRepository.class);
    private static final RowMapper<Theme> ROW_MAPPER = new ThemeRowMapper();

    private final DataSource dataSource;

    public ConsoleThemeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Theme save(Theme theme) {
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?);";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"})) {

            ps.setString(1, theme.getName());
            ps.setString(2, theme.getDesc());
            ps.setInt(3, theme.getPrice());
            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();
            Long id = resultSet.getLong("id");
            return new Theme(id, theme.getName(), theme.getDesc(), theme.getPrice());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Theme> findAll() {
        List<Theme> themes = new ArrayList<>();
        String sql = "SELECT id, name, desc, price FROM reservation";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Theme theme = ROW_MAPPER.mapRow(rs, rs.getRow());
                themes.add(theme);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return themes;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM theme WHERE id = ?";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM theme";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Theme> findById(Long id) {
        String sql = "SELECT id, name, desc, price FROM theme WHERE id = ?";

        try (Connection con = createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Theme theme = ROW_MAPPER.mapRow(rs, rs.getRow());
                return Optional.of(theme);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    private Connection createConnection() {
        try {
            Connection con = dataSource.getConnection();
            LOGGER.debug("정상적으로 연결되었습니다.");
            return con;
        } catch (SQLException e) {
            LOGGER.error("연결 오류: " + e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
