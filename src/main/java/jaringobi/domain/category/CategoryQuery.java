package jaringobi.domain.category;

import static jaringobi.domain.category.QCategory.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Category> findAll() {
        return jpaQueryFactory.selectFrom(category)
                .fetch();
    }
}
