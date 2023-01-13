package nextstep.common;

import nextstep.domain.JdbcEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseExecutor {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<String> tableNames;

    @PostConstruct
    public void findJdbcEntity() throws ClassNotFoundException {
        tableNames = new ArrayList<>();

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AnnotationTypeFilter(JdbcEntity.class));
        provider.addExcludeFilter(new AnnotationTypeFilter(Repository.class));

        for (BeanDefinition candidate : provider.findCandidateComponents("nextstep.domain")) {
            tableNames.add(Class.forName(candidate.getBeanClassName()).getSimpleName());
        }
    }

    public void createTables() {
        for (String tableName : tableNames) {
            Resource resource = new ClassPathResource(tableName + "-schema.sql");
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
            databasePopulator.execute(dataSource);
        }
    }

    @Transactional
    public void clearAll() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

        for (String tableName : tableNames) {
            jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
            jdbcTemplate.execute("ALTER TABLE " + tableName + " ALTER COLUMN id RESTART WITH 1");
        }

        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

}
