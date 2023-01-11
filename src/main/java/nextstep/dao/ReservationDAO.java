package nextstep.dao;

import nextstep.dto.ReservationDTO;

import java.sql.Time;
import java.sql.Date;

public interface ReservationDAO {

     String SELECT_BY_DATE_AND_TIME_SQL = "SELECT count(*) FROM reservation WHERE date = ? AND time = ? LIMIT 1";

     String INSERT_SQL = "INSERT INTO reservation(date,time,name,theme_name,theme_desc,theme_price) VALUES(?,?,?,?,?,?)";

     String SELECT_BY_ID_SQL = "SELECT * FROM reservation WHERE id = ?";

     String DELETE_BY_ID_SQL = "DELETE FROM reservation WHERE id = ?";

    Boolean existsByDateAndTime(Date date, Time time) throws RuntimeException;

    Long insert(ReservationDTO dto);

    ReservationDTO getById(Long id);

    void deleteById(Long id);
}
