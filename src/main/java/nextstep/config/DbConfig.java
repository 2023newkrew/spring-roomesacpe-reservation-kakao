package nextstep.config;

import org.json.simple.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class DbConfig {

    private String url;
    private String username;
    private String password;

    public DbConfig() {
        init();
    }

    public void init() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("application.yml");

        JSONObject jsonObject = new JSONObject(yaml.load(inputStream));
        JSONObject springJson = new JSONObject((Map) jsonObject.get("spring"));
        JSONObject datasourceJson = new JSONObject((Map) springJson.get("datasource"));

        url = (String) datasourceJson.get("url");
        username = (String) datasourceJson.get("username");
        password = datasourceJson.get("password") == null ? "" : (String) datasourceJson.get("password");
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
