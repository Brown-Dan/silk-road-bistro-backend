package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.autotrader.generated.tables.pojos.ArticleEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article.Builder.anArticle;

public class ArticleEntityMapperTest {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final LocalDate DATE = LocalDate.of(2024, 6, 27);

    private ArticleEntityMapper articleEntityMapper;

    @BeforeEach
    void setUp() {
        articleEntityMapper = new ArticleEntityMapper();
    }

    @Test
    void toDomain_givenValidArticleEntity_shouldReturnArticle() {
        Article result = articleEntityMapper.toDomain(getArticleEntity());

        assertThat(result).isEqualTo(getArticle());
    }

    private ArticleEntity getArticleEntity() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(TITLE);
        articleEntity.setContent(CONTENT);
        articleEntity.setDate(DATE);
        return articleEntity;
    }

    private Article getArticle() {
        return anArticle()
                .withTitle(TITLE)
                .withContent(CONTENT)
                .withDate(DATE)
                .build();
    }
}
