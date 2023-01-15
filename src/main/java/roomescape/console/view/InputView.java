package roomescape.console.view;

import java.util.Scanner;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getCommand() {
        printCommandHelp();
        return scanner.nextLine();
    }

    private static void printCommandHelp() {
        System.out.println();
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 예약하기: res_add {date},{time},{name},{theme_id} ex) res_add 2022-08-11,13:00,류성현,1");
        System.out.println("- 예약조회: res_find {id} ex) res_find 1");
        System.out.println("- 예약취소: res_delete {id} ex) res_delete 1");
        System.out.println("- 테마추가: the_add {name},{desc},{price} ex) theme add 워너고홈,병맛 어드벤처 회사 코믹물,29000");
        System.out.println("- 테마조회: the_find {id} ex) the_find 1");
        System.out.println("- 테마목록조회: the_list");
        System.out.println("- 테마삭제: the_delete {id} ex) theme delete 1");
        System.out.println("- 종료: quit");
    }

}
