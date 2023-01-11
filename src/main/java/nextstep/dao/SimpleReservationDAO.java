package nextstep.dao;

import nextstep.dto.ReservationDTO;
import nextstep.dto.ThemeDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Time;

public class SimpleReservationDAO implements ReservationDAO{

    private static final RowMapper<ReservationDTO> RESERVATION_DTO_ROW_MAPPER =
            (resultSet, rowNum) -> {
                if (rowNum == 0) {
                    return null;
                }
                ThemeDTO theme = new ThemeDTO(
                        resultSet.getString("theme_name"),
                        resultSet.getString("theme_desc"),
                        resultSet.getInt("theme_price")
                );
                return new ReservationDTO(
                        resultSet.getLong("id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime(),
                        resultSet.getString("name"),
                        theme
                );
            };

    @Override
    public Boolean existsByDateAndTime(Date date, Time time) throws RuntimeException {
        try(Connection connection = getConnection()) {
            var ps = connection.prepareStatement(SELECT_BY_DATE_AND_TIME_SQL);
            ps.setDate(1,date);
            ps.setTime(2,time);
            var rs = ps.executeQuery();
            return rs.getInt(1)>0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long insert(ReservationDTO dto) {
        try(Connection connection = getConnection()) {
            var ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            Date date = Date.valueOf(dto.getDate());
            Time time = Time.valueOf(dto.getTime());
            ThemeDTO theme = dto.getTheme();
            ps.setDate(1, date);
            ps.setTime(2, time);
            ps.setString(3, dto.getName());
            ps.setString(4, theme.getName());
            ps.setString(5, theme.getDesc());
            ps.setInt(6, theme.getPrice());
            ps.executeUpdate();
            var rs = ps.getGeneratedKeys();
            return rs.getLong(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReservationDTO getById(Long id) {
        try(Connection connection = getConnection()) {
            var ps = connection.prepareStatement(SELECT_BY_ID_SQL);
            ps.setLong(1,id);
            var rs = ps.executeQuery();
            return RESERVATION_DTO_ROW_MAPPER.mapRow(rs,0);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try(Connection connection = getConnection()) {
            var ps = connection.prepareStatement(DELETE_BY_ID_SQL);
            ps.setLong(1,id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=true", "sa", "");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
