package nextstep.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseExecutor {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createReservationTable() {
        jdbcTemplate.execute("CREATE TABLE RESERVATION(" +
                "id bigint not null auto_increment," +
                "date date," +
                "time time," +
                "name varchar(20)," +
                "theme_id bigint not null," +
                "primary key (id)" +
                ");");
    }

    public void createThemeTable() {
        jdbcTemplate.execute("CREATE TABLE THEME(" +
                "id bigint not null auto_increment," +
                "name varchar(20)," +
                "desc varchar(255)," +
                "price int," +
                "primary key (id)" +
                ");");
    }

    @Transactional
    public void clearTable(String tableName) {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
        jdbcTemplate.execute("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

}
