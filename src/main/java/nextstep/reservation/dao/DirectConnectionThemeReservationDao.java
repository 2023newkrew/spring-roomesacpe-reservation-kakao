package nextstep.reservation.dao;

import lombok.RequiredArgsConstructor;
import nextstep.reservation.entity.Reservation;
import nextstep.util.DatabaseUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DirectConnectionThemeReservationDao implements ThemeReservationDao {

    private static final String INSERT_SQL = "INSERT INTO `RESERVATION`(`DATE`, `TIME`, `NAME`, `THEME_ID`) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_RESERVATION_ID_SQL = "DELETE FROM `RESERVATION` WHERE `ID` = ?";
    private static final String SELECT_BY_RESERVATION_ID_SQL = "SELECT * FROM `RESERVATION` WHERE `ID` = ?";
    private static final String SELECT_BY_DATE_TIME_SQL = "SELECT * FROM `RESERVATION` WHERE `date` = ? AND `time` = ?";

    private static final String SELECT_BY_THEME_ID = "SELECT * FROM `RESERVATION` WHERE THEME_ID = ?";

    private final DataSource dataSource;
    @Override
    public int insert(Reservation reservation){
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        Long reservationId = 0L;
        int insertCount = 0;

        try{
            con = dataSource.getConnection();
            int parameterIndex = 1;
            psmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            psmt.setDate(parameterIndex++, Date.valueOf(reservation.getDate()));
            psmt.setTime(parameterIndex++, Time.valueOf(reservation.getTime()));
            psmt.setString(parameterIndex++, reservation.getName());
            psmt.setLong(parameterIndex++, reservation.getThemeId());
            insertCount = psmt.executeUpdate();
            resultSet = psmt.getGeneratedKeys();
            while (resultSet.next()) {
                reservationId = resultSet.getLong(1);
            }
            reservation.setId(reservationId);
        }catch (SQLException sqlException){

        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
        return insertCount;
    }

    @Override
    public int delete(Long id){
        Connection con = null;
        PreparedStatement psmt = null;
        int deleteCount = 0;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(DELETE_BY_RESERVATION_ID_SQL);
            psmt.setLong(1, id);
            deleteCount = psmt.executeUpdate();
        }catch (SQLException sqlException){

        }finally {
            DatabaseUtil.close(con, psmt);
        }
        return deleteCount;
    }

    @Override
    public Optional<Reservation> findById(Long id){
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(SELECT_BY_RESERVATION_ID_SQL);
            psmt.setLong(1, id);
            resultSet = psmt.executeQuery();
            List<Reservation> reservations = getReservation(resultSet);
            return reservations.stream()
                    .findFirst();
        }catch (SQLException sqlException){
            return Optional.empty();
        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    @Override
    public Optional<Reservation> findByDatetime(LocalDate date, LocalTime time) {
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(SELECT_BY_DATE_TIME_SQL);
            int parameterIndex = 1;
            psmt.setDate(parameterIndex++, Date.valueOf(date));
            psmt.setTime(parameterIndex++, Time.valueOf(time));

            resultSet = psmt.executeQuery();
            List<Reservation> reservations = getReservation(resultSet);
            return reservations.stream()
                    .findFirst();
        }catch (SQLException sqlException){
            return Optional.empty();
        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    private static List<Reservation> getReservation(ResultSet resultSet) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        while(resultSet.next()){
            Long id = resultSet.getLong("ID");
            LocalDate date = resultSet.getDate("DATE").toLocalDate();
            LocalTime time = resultSet.getTime("TIME").toLocalTime();
            String name = resultSet.getString("NAME");
            Long themeId = resultSet.getLong("THEME_ID");
            reservations.add((new Reservation(id, date, time, name, themeId)));
        }
        return reservations;
    }

    @Override
    public List<Reservation> findByThemeId(Long themeId) {
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet resultSet = null;

        try{
            con = dataSource.getConnection();
            psmt = con.prepareStatement(SELECT_BY_THEME_ID);
            psmt.setLong(1, themeId);

            resultSet = psmt.executeQuery();
            return getReservation(resultSet);
        }catch (SQLException sqlException){
            return List.of();
        }finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }
}
