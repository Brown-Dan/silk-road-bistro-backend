package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.co.autotrader.generated.tables.pojos.ArticleEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContext;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage.ArticleRepository;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper.ArticleEntityMapper;
import uk.danbrown.apprenticeshipchineserestaurantbackend.utils.DatabaseHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article.Builder.anArticle;

@JsonTest
@ExtendWith(MockitoExtension.class)
public class ArticleRepositoryTest {

    private static final DatabaseHelper dbHelper = new DatabaseHelper();

    private ArticleRepository articleRepository;

    @MockBean
    private RequestContextManager requestContextManager;

    @BeforeEach
    void setUp() {
        when(requestContextManager.getRequestContext()).thenReturn(new RequestContext("test"));
        articleRepository = new ArticleRepository(dbHelper.getDslContext(), new ArticleEntityMapper(), requestContextManager);
    }

    @AfterEach
    void tearDown() {
        dbHelper.clearTables();
    }

    @Test
    void getArticleByTitle_givenNonExistentTitle_shouldReturnEmptyOptional() {
        String title = "NonExisting";

        Optional<Article> result = articleRepository.getArticleByTitle(title);

        assertThat(result).isEmpty();
    }

    @Test
    void getArticleByTitle_givenExistingTitle_shouldReturnArticle() {
        ArticleEntity existingArticle = getArticle1();
        dbHelper.insertArticle(existingArticle);

        Article expectedArticle = anArticle()
                .withTitle(existingArticle.getTitle())
                .withContent(existingArticle.getContent())
                .withDate(existingArticle.getDate())
                .build();

        Optional<Article> result = articleRepository.getArticleByTitle(existingArticle.getTitle());

        assertThat(result).hasValue(expectedArticle);
    }

    @Test
    void getArticles_givenLimit_shouldReturnArticles() {
        ArticleEntity expectedArticleEntity = getArticle1();
        dbHelper.insertArticle(expectedArticleEntity, getArticle2());

        Article expectedArticle = anArticle()
                .withTitle(expectedArticleEntity.getTitle())
                .withContent(expectedArticleEntity.getContent())
                .withDate(expectedArticleEntity.getDate())
                .build();

        List<Article> result = articleRepository.getArticles(1);

        assertThat(result).asList().containsExactly(expectedArticle);
    }

    @Test
    void insertArticle_givenArticle_shouldInsertIntoDatabase() throws FailureInsertingEntityException {
        Article expectedArticle = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.of(2024, 6, 6))
                .build();

        Article result = articleRepository.insertArticle(expectedArticle);

        assertThat(result).isEqualTo(expectedArticle);
    }

    private ArticleEntity getArticle1() {
        ArticleEntity article = new ArticleEntity();
        article.setHomepageId("test");
        article.setTitle("Title1");
        article.setContent("Title1");
        article.setDate(LocalDate.of(2024, 6, 6));
        return article;
    }

    private ArticleEntity getArticle2() {
        ArticleEntity article = new ArticleEntity();
        article.setHomepageId("test");
        article.setTitle("Title2");
        article.setContent("Title2");
        article.setDate(LocalDate.of(2023, 7, 7));
        return article;
    }
}
