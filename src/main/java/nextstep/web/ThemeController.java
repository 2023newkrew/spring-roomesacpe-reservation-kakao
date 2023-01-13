package nextstep.web;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import nextstep.model.Theme;
import nextstep.repository.ReservationConverter;
import nextstep.web.dto.ThemeRequest;
import nextstep.web.dto.ThemeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThemeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody ThemeRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO theme (name, desc, price) VALUES (?, ?, ?)";

        PreparedStatementCreator creator = con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDesc());
            ps.setInt(3, request.getPrice());
            return ps;
        };

        jdbcTemplate.update(creator, keyHolder);
        Long id = keyHolder.getKey().longValue();

        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponse>> getThemes() {
        List<Theme> themes = jdbcTemplate.query("SELECT id, name, desc, price FROM theme", (rs, rn) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String desc = rs.getString("desc");
            int price = rs.getInt("price");
            return new Theme(id, name, desc, price);
        });

        List<ThemeResponse> responses = themes.stream()
                .map(t -> new ThemeResponse(t.getId(), t.getName(), t.getDesc(), t.getPrice()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}
