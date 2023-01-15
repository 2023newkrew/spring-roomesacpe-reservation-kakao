package nextstep.step3;

import nextstep.step3.controller.ThemeReservationConsoleController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;

@ComponentScan(basePackages = {"nextstep"})
public class RoomEscapeConsoleApplication {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(RoomEscapeConsoleApplication.class);
        ThemeReservationConsoleController themeReservationConsoleController = context.getBean("themeReservationConsoleController", ThemeReservationConsoleController.class);
        themeReservationConsoleController.play();
    }
}
