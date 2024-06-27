package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArticlesService {

    private final ArticleRepository articleRepository;

    public ArticlesService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(Article article) throws FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
        Optional<Article> existingArticle = articleRepository.getArticleByTitle(article.title());

        if (existingArticle.isPresent()) {
            throw new EntityAlreadyExistsWithIdException(article.title());
        }
        return articleRepository.insertArticle(article);
    }

    public List<Article> getArticles(Integer limit) {
        return articleRepository.getArticles(limit);
    }
}
