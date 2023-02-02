package nextstep.dao;

import nextstep.domain.Theme;
import nextstep.dto.ThemeDTO;

import java.util.List;

public interface ThemeDAO {
    String INSERT_SQL = "INSERT INTO theme(name, desc, price) VALUES(?,?,?)";

    String SELECT_SQL = "SELECT * FROM theme";

    String SELECT_BY_ID_SQL = "SELECT * FROM theme WHERE id = ?";

    String DELETE_BY_ID_SQL = "DELETE FROM theme WHERE id = ?";

    Long insert(Theme theme);

    List<Theme> getList();

    Theme getById(Long id);

    Boolean deleteById(Long id);
}
