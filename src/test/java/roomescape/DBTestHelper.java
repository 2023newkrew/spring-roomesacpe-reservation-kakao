package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBTestHelper {
    private final JdbcTemplate jdbcTemplate;

    public DBTestHelper(
            JdbcTemplate jdbcTemplate
    ){
        this.jdbcTemplate = jdbcTemplate;
    }
    public void dbCleanUp(String tableName){
        jdbcTemplate.update("TRUNCATE TABLE " + tableName);
        jdbcTemplate.update("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1");
    }
}
