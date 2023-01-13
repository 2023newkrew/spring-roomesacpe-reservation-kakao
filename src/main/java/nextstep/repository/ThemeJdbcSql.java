package nextstep.repository;

public final class ThemeJdbcSql {

    public static final String FIND_BY_ID = "SELECT * FROM THEME WHERE id = ?";
    public static final String UPDATE = "UPDATE THEME SET name = ?, desc = ?, price = ? WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM THEME WHERE ID = ?";
    public static final String INSERT_INTO = "INSERT INTO THEME(NAME, DESC, PRICE) VALUES(?, ?, ?);";

    private ThemeJdbcSql(){}







}
