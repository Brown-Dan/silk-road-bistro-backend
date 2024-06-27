package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper;

import io.cucumber.java.bs.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.autotrader.generated.tables.pojos.ArticleEntity;

public class ArticleMapperTest {


    private ArticleMapper articleMapper;

    @BeforeEach
    void setUp() {
        articleMapper = new ArticleMapper();
    }


    @Test
    void toDomain_givenValidArticleEntity_shouldReturnArticle() {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setTitle("title");
        articleEntity.setContent("content");


    }
}
