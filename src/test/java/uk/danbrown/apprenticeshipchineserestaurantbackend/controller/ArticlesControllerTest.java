package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.ArticlesService;

import java.time.LocalDate;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article.Builder.anArticle;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.utils.MvcResultAssert.assertThat;

@WebMvcTest(ArticlesController.class)
public class ArticlesControllerTest extends ControllerTestBase {

    @MockBean
    ArticlesService articlesService;

    @Test
    void getArticles_givenLimit_shouldReturnArticleResources() {
        Article expectedArticle = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();
        String expectedResponseBody = "[{\"title\":\"Title\",\"content\":\"Content\"}]";
        Integer limit = 3;

        when(articlesService.getArticles(any())).thenReturn(singletonList(expectedArticle));

        MvcResult mvcResult = get("/articles?limit=%s".formatted(limit));

        verify(articlesService).getArticles(limit);
        assertThat(mvcResult).hasStatus(HttpStatus.OK).hasBody(expectedResponseBody);
    }

    @Test
    void getArticles_givenNoLimit_shouldDefaultToThreeAndReturnArticleResources() {
        Article expectedArticle = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();
        String expectedResponseBody = "[{\"title\":\"Title\",\"content\":\"Content\"}]";

        when(articlesService.getArticles(any())).thenReturn(singletonList(expectedArticle));

        MvcResult mvcResult = get("/articles");

        verify(articlesService).getArticles(3);
        assertThat(mvcResult).hasStatus(HttpStatus.OK).hasBody(expectedResponseBody);
    }

    @Test
    void createArticle_givenValidArticle_shouldReturnCreatedArticle() throws EntityAlreadyExistsWithIdException, FailureInsertingEntityException {
        Article expectedArticle = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();
        String expectedResponseBody = "{\"title\":\"Title\",\"content\":\"Content\"}";

        when(articlesService.createArticle(any())).thenReturn(expectedArticle);

        MvcResult mvcResult = post("/articles", expectedResponseBody);

        assertThat(mvcResult).hasStatus(HttpStatus.CREATED).hasBody(expectedResponseBody);
    }
}
