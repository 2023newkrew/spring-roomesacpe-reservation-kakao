package nextstep.console.controller;

import static nextstep.console.ConsoleCommand.ADD;
import static nextstep.console.ConsoleCommand.DELETE;
import static nextstep.console.ConsoleCommand.EDIT;
import static nextstep.console.ConsoleCommand.FIND;

import nextstep.console.view.View;
import nextstep.dto.ThemeCreateDto;
import nextstep.dto.ThemeEditDto;
import nextstep.dto.ThemeResponseDto;
import nextstep.entity.Theme;
import nextstep.exception.ConsoleConflictException;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;

public class ThemeConsoleController {

    private final ThemeService themeService;
    private final ReservationService reservationService;

    private final View view;

    public ThemeConsoleController(ThemeService themeService, ReservationService reservationService, View view) {
        this.themeService = themeService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void executeThemeCommand(String input) {
        String command = input.split(" ")[1];
        String params = input.split(" ")[2];
        if (command.startsWith(ADD)) {
            ThemeCreateDto themeCreateDto = makeThemeCreateDto(params);
            Theme theme = themeService.createTheme(themeCreateDto);
            view.printThemeResponseDto(ThemeResponseDto.of(theme));
        }
        if (command.startsWith(FIND)) {
            Long id = Long.parseLong(params);
            view.printThemeResponseDto(ThemeResponseDto.of(themeService.findById(id)));
        }
        if (command.startsWith(EDIT)) {
            Long themeId = Long.parseLong(params.split(",")[0]);
            boolean exist = reservationService.existByThemeId(themeId);
            if(exist){
                throw new ConsoleConflictException("해당하는 테마를 예약한 사람이 존재합니다.");
            }
            themeService.editTheme(makeThemeEditDto(params));
        }
        if (command.startsWith(DELETE)) {
            long themeId = Long.parseLong(params);
            boolean exist = reservationService.existByThemeId(themeId);
            if(exist){
                throw new ConsoleConflictException("해당하는 테마를 예약한 사람이 존재합니다.");
            }
            themeService.deleteById(themeId);
        }
    }



    private ThemeEditDto makeThemeEditDto(String params) {
        String[] tokens = params.split(",");
        Long id = Long.parseLong(tokens[0]);
        String name = tokens[1];
        String desc = tokens[2];
        Integer price = Integer.parseInt(tokens[3]);
        return ThemeEditDto.builder()
                .name(name)
                .description(desc)
                .price(price)
                .id(id)
                .build();
    }

    private ThemeCreateDto makeThemeCreateDto(String params) {
        String[] tokens = params.split(",");
        String name = tokens[0];
        String desc = tokens[1];
        int price = Integer.parseInt(tokens[2]);
        return ThemeCreateDto.builder()
                .price(price)
                .name(name)
                .description(desc)
                .build();
    }


}
