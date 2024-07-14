package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ArticleResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article.Builder.anArticle;

public class ArticleResourceMapperTest {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final LocalDate DATE = LocalDate.of(2024, 6, 27);

    private ArticleResourceMapper articleResourceMapper;

    @BeforeEach
    void setUp() {
        articleResourceMapper = new ArticleResourceMapper(Clock.fixed(DATE.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC));
    }

    @Test
    void toDomain_givenArticleResource_shouldMapToArticle() {
        Article result = articleResourceMapper.toDomain(getArticleResource());

        assertThat(result).isEqualTo(getArticle());
    }

    private ArticleResource getArticleResource() {
        return new ArticleResource(TITLE, CONTENT);
    }

    private Article getArticle() {
        return anArticle()
                .withTitle(TITLE)
                .withContent(CONTENT)
                .withDate(DATE)
                .build();
    }

}
