package nextstep.dao;

import nextstep.dto.ThemeDTO;

import java.util.List;

public interface ThemeDAO {
    String INSERT_SQL = "INSERT INTO theme(name, desc, price) VALUES(?,?,?)";

    String SELECT_SQL = "SELECT * FROM theme";

    String DELETE_BY_ID_SQL = "DELETE FROM theme WHERE id = ?";

    Long insert(ThemeDTO dto);

    List<ThemeDTO> getList();

    Boolean deleteById(Long id);
}
