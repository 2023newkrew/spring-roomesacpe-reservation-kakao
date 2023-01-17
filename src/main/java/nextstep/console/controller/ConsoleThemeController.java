package nextstep.console.controller;


import nextstep.console.repository.ConsoleReservationRepository;
import nextstep.console.repository.ConsoleThemeRepository;
import nextstep.console.view.ConsoleThemeView;
import nextstep.domain.ThemeManager;
import nextstep.dto.CreateThemeRequest;


import static nextstep.console.ConsoleCommands.*;

public class ConsoleThemeController {

    private final ConsoleThemeView themeView;
    private final ThemeManager themeManager;

    public ConsoleThemeController() {
        this.themeManager = new ThemeManager(
                new ConsoleReservationRepository(),
                new ConsoleThemeRepository()
        );
        this.themeView = new ConsoleThemeView();
    }

    public void run() {
        while(true) {
            themeView.printCommand();
            String input = themeView.inputCommand();

            if(input.startsWith(ADD))
                addProcess();

            if(input.startsWith(FIND))
                findProcess(input);

            if(input.startsWith(ALL))
                findAllProcess();

            if(input.startsWith(DELETE))
                deleteProcess(input);

            if(input.startsWith(BACK))
                return;
        }
    }

    private void addProcess() {
        String name = themeView.inputThemeName();
        String desc = themeView.inputThemeDesc();
        Integer price = themeView.inputThemePrice();

        CreateThemeRequest themeRequest = new CreateThemeRequest(name, desc, price);

        Long savedId = themeManager.createTheme(themeRequest);

        themeView.printAddMessage();
        themeView.printThemeResponse(themeManager.findThemeById(savedId));
    }

    private void findProcess(String input) {
        Long id = Long.parseLong(input.split(" ")[1]);

        themeView.printThemeResponse(themeManager.findThemeById(id));
    }

    private void findAllProcess() {
        themeView.printThemes(themeManager.getAllThemes());
    }

    private void deleteProcess(String input) {
        Long id = Long.parseLong(input.split(" ")[1]);

        if(themeManager.deleteThemeById(id))
            themeView.printDeleteMessage();
    }


}
