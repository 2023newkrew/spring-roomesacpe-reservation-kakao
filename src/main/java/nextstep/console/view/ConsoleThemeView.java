package nextstep.console.view;

import nextstep.dto.ThemeResponse;
import nextstep.dto.ThemesResponse;

import java.util.Scanner;

public class ConsoleThemeView {
    private final Scanner sc;

    public ConsoleThemeView() {
        this.sc = new Scanner(System.in);
    }

    public void printCommand() {
        System.out.println("### 명령어를 입력하세요. ###");
        System.out.println("- 테마 추가: add");
        System.out.println("- 테마 조회: find {id} ex) find 1");
        System.out.println("- 모든테마 조회: all");
        System.out.println("- 테마 삭제: delete {id} ex) delete 1");
        System.out.println("- 뒤로가기: back");
    }

    public String inputCommand() {
        return sc.nextLine();
    }

    public String inputThemeName() {
        System.out.print("- 테마이름: ");
        return sc.nextLine();
    }

    public String inputThemeDesc() {
        System.out.print("- 테마설명: ");
        return sc.nextLine();
    }

    public Integer inputThemePrice() {
        System.out.print("- 테마가격: ");
        return Integer.parseInt(sc.nextLine());
    }

    public void printAddMessage() {
        System.out.println("테마가 추가되었습니다.");
    }

    public void printDeleteMessage() {
        System.out.println("테마가 삭제되었습니다.");
    }

    public void printThemeResponse(ThemeResponse theme) {
        System.out.println("테마 번호: " + theme.getId());
        System.out.println("테마 이름: " + theme.getName());
        System.out.println("테마 설명: " + theme.getDesc());
        System.out.println("테마 가격: " + theme.getPrice());
        System.out.println();
    }

    public void printThemes(ThemesResponse themes) {
        themes.getThemes().forEach(themeResponse -> printThemeResponse(themeResponse));
    }
}
