package nextstep.console.controller;

import static nextstep.console.ConsoleCommand.ADD;
import static nextstep.console.ConsoleCommand.DELETE;
import static nextstep.console.ConsoleCommand.EDIT;
import static nextstep.console.ConsoleCommand.FIND;

import nextstep.console.view.View;
import nextstep.console.utils.ConnectionHandler;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.entity.Theme;
import nextstep.repository.ThemeJdbcRepositoryImpl;
import nextstep.service.ThemeService;
import nextstep.service.ThemeServiceImpl;

public class ThemeConsoleController {

    private final ThemeService themeService;

    private final View view;

    public ThemeConsoleController(ConnectionHandler connectionHandler, View view) {
        this.themeService = new ThemeServiceImpl(new ThemeJdbcRepositoryImpl(connectionHandler));
        this.view = view;
    }
    public void executeThemeCommand(String input) {
        String command = input.split(" ")[1];
        String params = input.split(" ")[2];
        if (command.startsWith(ADD)) {
            ThemeCreateDto themeCreateDto = makeThemeCreateDto(params);
            view.printTheme(makeTheme(themeCreateDto, themeService.createTheme(themeCreateDto)));
        }
        if (command.startsWith(FIND)) {
            Long id = Long.parseLong(params);
            view.printThemeResponseDto(themeService.findTheme(id));
        }
        if (command.startsWith(EDIT)) {
            themeService.editTheme(makeThemeEditDto(params));
        }
        if (command.startsWith(DELETE)) {
            themeService.deleteById(Long.parseLong(params));
        }
    }

    private Theme makeTheme(ThemeCreateDto themeCreateDto, Long id) {
        return new Theme(id, themeCreateDto.getName(), themeCreateDto.getDescription(),
                themeCreateDto.getPrice());
    }

    private ThemeEditDto makeThemeEditDto(String params) {
        String[] tokens = params.split(",");
        Long id = Long.parseLong(tokens[0]);
        String name = tokens[1];
        String desc = tokens[2];
        Integer price = Integer.parseInt(tokens[3]);
        return new ThemeEditDto(id, name, desc, price);
    }

    private ThemeCreateDto makeThemeCreateDto(String params) {
        String[] tokens = params.split(",");
        String name = tokens[0];
        String desc = tokens[1];
        int price = Integer.parseInt(tokens[2]);
        return new ThemeCreateDto(name, desc, price);
    }


}
