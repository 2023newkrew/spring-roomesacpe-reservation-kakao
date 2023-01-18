package nextstep.repository;

public final class ThemeJdbcSql {

    public static final String FIND_BY_ID_STATEMENT = "SELECT * FROM THEME WHERE id = ?";
    public static final String UPDATE_STATEMENT = "UPDATE THEME SET name = ?, desc = ?, price = ? WHERE id = ?";
    public static final String DELETE_BY_ID_STATEMENT = "DELETE FROM THEME WHERE ID = ?";
    public static final String INSERT_INTO_STATEMENT = "INSERT INTO THEME(NAME, DESC, PRICE) VALUES(?, ?, ?);";

    private ThemeJdbcSql(){}







}
