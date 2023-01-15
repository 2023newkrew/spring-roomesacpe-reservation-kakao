package nextstep.repository.reservation;

import nextstep.ConsoleConnectDB;
import nextstep.domain.Reservation;
import nextstep.domain.Theme;
import nextstep.dto.FindReservation;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsoleReservationRepository implements ReservationRepository {

    private Connection con = null;

    public ConsoleReservationRepository() {
        con = ConsoleConnectDB.getConnect();
    }

    @Override
    public Reservation findById(Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(findByIdSql, new String[]{"id"});
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return from(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("예약 내역을 찾을 수 없습니다.");
        }
    }

    @Override
    public List<FindReservation> findAll() {
        try {
            PreparedStatement ps = con.prepareStatement(findAllSql, new String[]{"id"});
            ResultSet resultSet = ps.executeQuery();
            List<FindReservation> reservationList = new ArrayList<>();
            while (resultSet.next()) {
                reservationList.add(allFrom(resultSet));
            }
            return reservationList;
        } catch (Exception e) {
            throw new RuntimeException("예약을 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteById(Long id) {
        delete(deleteByIdSql, id);

    }

    @Override
    public void deleteByThemeId(Long themeId) {
        delete(deleteByThemeIdSql, themeId);
    }

    @Override
    public Long save(LocalDate date, LocalTime time, String name, Theme theme) {
        try {
            validateReservation(date, time);

            PreparedStatement ps = getReservationPreparedStatement(con, date, time, name, theme);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getLong("id");
        } catch (SQLException | IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createTable() throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(createTableSql);
    }

    @Override
    public void dropTable() throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(dropTableSql);
    }

    private void validateReservation(LocalDate date, LocalTime time) throws SQLException {
        try {
            PreparedStatement ps = con.prepareStatement(checkDuplicationSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(time));
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int row = resultSet.getInt("total_rows");
            if (row > 0) {
                throw new IllegalArgumentException("이미 예약이 존재합니다.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("이미 예약이 존재합니다.");
        } catch (SQLException e) {
            throw new SQLException("SQL 오류");
        }
    }

    private void delete(String sql, Long id) {
        try {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, id);
            int cnt = ps.executeUpdate();

            if (cnt == 0) throw new RuntimeException("해당 id의 예약은 존재하지 않습니다.");
        } catch (Exception e) {
            throw new RuntimeException("해당 id의 예약은 존재하지 않습니다.");
        }
    }
}
