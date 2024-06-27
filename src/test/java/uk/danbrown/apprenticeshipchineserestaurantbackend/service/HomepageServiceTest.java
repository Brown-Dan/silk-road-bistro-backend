package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.HomepageRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article.Builder.anArticle;

@ExtendWith(MockitoExtension.class)
public class HomepageServiceTest {

    @Mock
    private HomepageRepository homepageRepository;

    private HomepageService homepageService;

    @BeforeEach
    void setUp() {
        homepageService = new HomepageService(homepageRepository);
    }

    @Test
    void getArticles_shouldCallArticleRepository() {
        Article expectedArticle = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();
        Integer limit = 3;

        when(homepageRepository.getArticles(any())).thenReturn(singletonList(expectedArticle));

        List<Article> result = homepageService.getArticles(limit);

        verify(homepageRepository).getArticles(limit);
        assertThat(result).containsExactlyInAnyOrder(expectedArticle);
    }

    @Test
    void createArticle_givenArticleWithExistingTitle_shouldThrowEntityAlreadyExistsWithIdException() {
        Article article = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();

        when(homepageRepository.getArticleByTitle(any())).thenReturn(Optional.of(article));


        assertThatThrownBy(() -> homepageService.createArticle(article)).isInstanceOf(EntityAlreadyExistsWithIdException.class)
                .hasFieldOrPropertyWithValue("id", article.title());
        verify(homepageRepository).getArticleByTitle(article.title());
    }

    @Test
    void createArticle_givenValidArticle_shouldReturnCreatedArticle() throws EntityAlreadyExistsWithIdException, FailureInsertingEntityException {
        Article article = anArticle()
                .withTitle("Title")
                .withContent("Content")
                .withDate(LocalDate.now()).build();

        when(homepageRepository.getArticleByTitle(any())).thenReturn(Optional.empty());
        when(homepageRepository.createArticle(any())).thenReturn(article);

        Article result = homepageService.createArticle(article);

        verify(homepageRepository).getArticleByTitle(article.title());
        verify(homepageRepository).createArticle(article);
        assertThat(result).isEqualTo(article);
    }
}
