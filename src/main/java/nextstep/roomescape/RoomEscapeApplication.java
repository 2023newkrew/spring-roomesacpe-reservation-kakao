package nextstep.roomescape;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class RoomEscapeApplication {


    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(RoomEscapeApplication.class);
        ThemeReservationConsoleController themeReservationConsoleController = context.getBean("themeReservationConsoleController", ThemeReservationConsoleController.class);
        themeReservationConsoleController.play();

    }


}
