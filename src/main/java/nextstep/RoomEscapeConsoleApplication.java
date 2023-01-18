package nextstep;

import nextstep.console.view.ReservationView;
import nextstep.console.view.ThemeView;

import java.sql.SQLException;
import java.util.Scanner;

public class RoomEscapeConsoleApplication {


    public static void main(String[] args) throws SQLException {

        ReservationView reservationView = new ReservationView();
        ThemeView themeView = new ThemeView();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("### 접근할 서비스를 선택하세요. ###");
            System.out.println("- 예약 관련 서비스: 1");
            System.out.println("- 테마 관련 서비스: 2");
            System.out.println("- 종료: 3");

            String input = scanner.nextLine();
            if (input.startsWith("1")){
                System.out.println();
                System.out.println("### 명령어를 입력하세요. ###");
                System.out.println("- 예약하기: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
                System.out.println("- 예약조회: find {id} ex) find 1");
                System.out.println("- 예약취소: delete {id} ex) delete 1");

                reservationView.parseInput(scanner.nextLine());
            }

            if (input.startsWith("2")){
                System.out.println();
                System.out.println("### 명령어를 입력하세요. ###");
                System.out.println("- 테마 추가: add {date},{time},{name},{theme_id} ex) add 2022-08-11,13:00,류성현,1");
                System.out.println("- 테마 목록: list");
                System.out.println("- 테마 삭제: delete {id} ex) delete 1");

                themeView.parseInput(scanner.nextLine());
            }

            if (input.startsWith("3")){
                break;
            }

        }
    }
}
