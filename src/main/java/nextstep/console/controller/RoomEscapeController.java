package nextstep.console.controller;

import nextstep.console.dto.Command;
import nextstep.console.dto.CommandType;
import nextstep.console.dto.Params;
import nextstep.console.view.RoomEscapeInput;
import nextstep.console.view.RoomEscapeOutput;
import nextstep.reservation.dto.ReservationDTO;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.service.ReservationService;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;


public class RoomEscapeController {

    private final RoomEscapeInput input;

    private final RoomEscapeOutput output;

    private final ReservationService service;

    private final Map<CommandType, Consumer<Params>> commandActions;

    public RoomEscapeController(RoomEscapeInput input, RoomEscapeOutput output, ReservationService service) {
        this.input = input;
        this.service = service;
        this.output = output;
        this.commandActions = createCommandActions();
    }

    private Map<CommandType, Consumer<Params>> createCommandActions() {
        return Map.of(
                CommandType.ADD, this::add,
                CommandType.FIND, this::find,
                CommandType.DELETE, this::delete,
                CommandType.QUIT, p -> {}
        );
    }

    private void add(Params params) {
        ReservationRequest request = params.getReservationRequest();
        Long id = service.create(request);
        ReservationDTO reservation = service.getById(id);
        output.printAddReservation(reservation);
    }

    private void find(Params params) {
        Long id = params.getLong();
        ReservationDTO reservation = service.getById(id);
        output.printFindReservation(reservation);
    }

    private void delete(Params params) {
        Long id = params.getLong();
        if (service.deleteById(id)) {
            output.printDeleteReservation();
        }
    }

    public void run() {
        boolean isQuit = false;
        while (!isQuit) {
            Command command = inputCommand();
            isQuit = executeAction(command);
        }
    }

    private Command inputCommand() {
        Command command = null;
        while (Objects.isNull(command)) {
            output.printMenu();
            command = tryInputCommand();
        }

        return command;
    }

    private Command tryInputCommand() {
        Command command = input.inputCommand();
        try {
            validateCommand(command);
        }
        catch (RuntimeException ex) {
            output.printError(ex.getMessage());
            command = null;
        }

        return command;
    }

    private void validateCommand(Command command) {
        CommandType menuType = command.getType();
        Params params = command.getParams();
        if (menuType.equals(CommandType.NONE)) {
            throw new RuntimeException("존재하지 않는 메뉴입니다.");
        }

        int paramCount = menuType.getParamCount();
        if (paramCount != params.size()) {
            throw new RuntimeException("해당 메뉴는 " + paramCount + "개의 인자가 필요합니다.");
        }
    }

    private boolean executeAction(Command command) {
        Consumer<Params> commandAction = commandActions.get(command.getType());
        try {
            commandAction.accept(command.getParams());
        }
        catch (RuntimeException ex) {
            output.printError(ex.getMessage());
        }

        return command.isQuit();
    }
}
