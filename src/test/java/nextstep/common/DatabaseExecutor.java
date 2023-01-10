package nextstep.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseExecutor {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable(String tableName) {
        jdbcTemplate.execute("CREATE TABLE RESERVATION(" +
                "id bigint not null auto_increment," +
                "date date," +
                "time time," +
                "name varchar(20)," +
                "theme_name  varchar(20)," +
                "theme_desc  varchar(255)," +
                "theme_price int," +
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
