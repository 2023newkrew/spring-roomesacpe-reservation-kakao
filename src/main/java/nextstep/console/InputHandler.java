package nextstep.console;

public interface InputHandler {

    boolean supports(String input);
    void handle(String input);
}
