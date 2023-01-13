package nextstep.console;

import nextstep.console.controller.ConsoleReservationController;
import nextstep.console.controller.ConsoleThemeController;
import java.util.Scanner;

import static nextstep.console.ConsoleCommands.*;

public class ConsoleRoomEscapeApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleReservationController reservationController = new ConsoleReservationController();
        ConsoleThemeController themeController = new ConsoleThemeController();

        while (true) {
            System.out.println();
            System.out.println("### 명령어를 입력하세요. ###");
            System.out.println("- 예약기능 사용하기: reservation");
            System.out.println("- 테마기능 사용하기: theme");
            System.out.println("- 종료: quit");

            String input = scanner.nextLine();
            if(input.startsWith(RESERVATION))
                reservationController.run();

            if(input.startsWith(THEME))
                themeController.run();

            if (input.equals(QUIT))
                break;
        }
    }
}
