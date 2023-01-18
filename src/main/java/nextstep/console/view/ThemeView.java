package nextstep.console.view;

import nextstep.console.service.ThemeConsoleService;
import nextstep.domain.Theme;
import nextstep.domain.dto.ThemeRequest;
import nextstep.domain.dto.ThemeResponse;
import nextstep.domain.service.ThemeService;

import java.util.List;

public class ThemeView {
    private static final String ADD = "add";
    private static final String LIST = "list";
    private static final String DELETE = "delete";

    private final ThemeService service;

    public ThemeView() {
        service = new ThemeConsoleService();
    }

    public void parseInput(String input){
        if (input.startsWith(ADD)) {
            String params = input.split(" ")[1];

            String name = params.split(",")[0];
            String desc = params.split(",")[1];
            String price = params.split(",")[2];

            ThemeRequest themeRequest = new ThemeRequest(name, desc, Integer.parseInt(price));

            Theme theme = service.newTheme(themeRequest);

            System.out.println("테마가 추가되었습니다.");
            System.out.println("번호: " + theme.getId());
            System.out.println("이름: " + theme.getName());
            System.out.println("설명: " + theme.getDesc());
            System.out.println("가격: " + theme.getPrice());
        }

        if (input.startsWith(LIST)) {

            List<ThemeResponse> themes = service.findAllTheme();

            for (ThemeResponse theme : themes) {
                System.out.println("번호: " + theme.getId());
                System.out.println("이름: " + theme.getName());
                System.out.println("설명: " + theme.getDesc());
                System.out.println("가격: " + theme.getPrice());
                System.out.println("===");
            }
        }

        if (input.startsWith(DELETE)) {
            String params = input.split(" ")[1];

            Long id = Long.parseLong(params.split(",")[0]);

            if (service.deleteTheme(id)) {
                System.out.println("테마가 삭제되었습니다.");
            }
        }

    }
}
