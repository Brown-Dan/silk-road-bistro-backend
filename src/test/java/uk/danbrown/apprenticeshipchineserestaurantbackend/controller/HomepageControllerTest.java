package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.mapper.ArticleResourceMapper;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.HomepageService;

import java.time.LocalDate;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article.Builder.anArticle;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.utils.MvcResultAssert.assertThat;

@WebMvcTest(HomepageController.class)
public class HomepageControllerTest extends ControllerTestBase {

    @MockBean
    HomepageService homepageService;

    @MockBean
    ArticleResourceMapper articleResourceMapper;

    @Test
    void getArticles_givenLimit_shouldReturnArticleResources() {
        Article expectedArticle = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();
        String expectedResponseBody = "{\"articles\":[{\"title\":\"Title\",\"content\":\"Content\"}]}";
        Integer limit = 3;

        when(homepageService.getArticles(any())).thenReturn(singletonList(expectedArticle));

        MvcResult mvcResult = get("/articles?limit=%s".formatted(limit));

        verify(homepageService).getArticles(limit);
        assertThat(mvcResult).hasStatus(HttpStatus.OK).hasBody(expectedResponseBody);
    }

    @Test
    void getArticles_givenNoLimit_shouldDefaultToThreeAndReturnArticleResources() {
        Article expectedArticle = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();
        String expectedResponseBody = "{\"articles\":[{\"title\":\"Title\",\"content\":\"Content\"}]}";

        when(homepageService.getArticles(any())).thenReturn(singletonList(expectedArticle));

        MvcResult mvcResult = get("/articles");

        verify(homepageService).getArticles(3);
        assertThat(mvcResult).hasStatus(HttpStatus.OK).hasBody(expectedResponseBody);
    }

    @Test
    void createArticle_givenValidArticle_shouldReturnCreatedArticle() throws EntityAlreadyExistsWithIdException, FailureInsertingEntityException {
        Article expectedArticle = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();
        String expectedResponseBody = "{\"title\":\"Title\",\"content\":\"Content\"}";

        when(homepageService.createArticle(any())).thenReturn(expectedArticle);

        MvcResult mvcResult = post("/articles", expectedResponseBody);

        assertThat(mvcResult).hasStatus(HttpStatus.CREATED).hasBody(expectedResponseBody);
    }
}
