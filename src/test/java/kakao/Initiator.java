package kakao;

import org.springframework.jdbc.core.JdbcTemplate;

public class Initiator {
    private final JdbcTemplate jdbcTemplate;

    public Initiator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createThemeForTest() {
        jdbcTemplate.update("insert into theme(name, desc, price)\nvalues ('워너고홈', '병맛 어드벤처 회사 코믹물', 29000)");
    }

    public void clear() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE theme");
        jdbcTemplate.execute("ALTER TABLE theme ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");

        jdbcTemplate.execute("TRUNCATE TABLE reservation");
        jdbcTemplate.execute("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
    }
}
