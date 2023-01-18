package roomescape.console;

public enum Command {
    ADD_RESERVATION,
    FIND_RESERVATION,
    DELETE_RESERVATION,
    ADD_THEME,
    SHOW_THEME,
    DELETE_THEME,
    QUIT,
    INVALID_INPUT;

    private String[] params = {};

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
