package nextstep.console.controller;


import nextstep.console.view.ConsoleThemeView;
import nextstep.domain.repository.ReservationRepository;
import nextstep.domain.repository.ThemeRepository;
import nextstep.dto.CreateThemeRequest;
import nextstep.repository.JdbcReservationRepository;
import nextstep.repository.JdbcThemeRepository;
import nextstep.service.ThemeService;
import nextstep.utils.DataSourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;



import static nextstep.console.ConsoleCommands.*;

public class ConsoleThemeController {

    private final ConsoleThemeView themeView;
    private final ThemeService themeService;

    public ConsoleThemeController() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceUtil.getDataSource());
        ReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);
        ThemeRepository themeRepository = new JdbcThemeRepository(jdbcTemplate);

        this.themeView = new ConsoleThemeView();
        this.themeService = new ThemeService(themeRepository, reservationRepository);
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

        Long savedId = themeService.createTheme(themeRequest);

        themeView.printAddMessage();
        themeView.printThemeResponse(themeService.findThemeById(savedId));
    }

    private void findProcess(String input) {
        Long id = Long.parseLong(input.split(" ")[1]);

        themeView.printThemeResponse(themeService.findThemeById(id));
    }

    private void findAllProcess() {
        themeView.printThemes(themeService.getAllThemes());
    }

    private void deleteProcess(String input) {
        Long id = Long.parseLong(input.split(" ")[1]);

        if(themeService.deleteThemeById(id))
            themeView.printDeleteMessage();
    }


}
