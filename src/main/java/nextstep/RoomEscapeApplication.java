package nextstep;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import nextstep.console.ConsoleApp;
import nextstep.entity.ThemeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@SpringBootApplication
public class RoomEscapeApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(RoomEscapeApplication.class, args);
        ConsoleApp.run();
    }



}
