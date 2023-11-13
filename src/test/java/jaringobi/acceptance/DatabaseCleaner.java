package jaringobi.acceptance;

import groovy.util.logging.Slf4j;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class DatabaseCleaner implements InitializingBean{

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities()
                .stream()
                .filter(it -> !it.getName().equalsIgnoreCase("category")) // category 엔터티 제외
                .map(it -> {
                    final String entityName = it.getName().toLowerCase(Locale.ROOT);
                    if (entityName.equals("user")) {
                        return entityName.concat("s");
                    }
                    if (entityName.equals("categorybudget")) {
                        return "budget_by_category";
                    }
                    return entityName;
                })
                .toList();
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET foreign_key_checks = 0;").executeUpdate();
        tableNames.forEach(tableName -> executeQueryWithTable(tableName));
        entityManager.createNativeQuery("SET foreign_key_checks = 1;").executeUpdate();
    }

    private void executeQueryWithTable(String tableName) {
        entityManager.createNativeQuery("TRUNCATE TABLE " + tableName + ";").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1;").executeUpdate();
    }
}
