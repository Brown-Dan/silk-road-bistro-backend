package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.ArticlesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArticlesService {

    private final ArticlesRepository articlesRepository;

    public ArticlesService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public Article createArticle(Article article) throws FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
        Optional<Article> existingArticle = articlesRepository.getArticleByTitle(article.title());

        if (existingArticle.isPresent()) {
            throw new EntityAlreadyExistsWithIdException(article.title());
        }
        return articlesRepository.createArticle(article);
    }

    public List<Article> getArticles(Integer limit) {
        return articlesRepository.getArticles(limit);
    }
}
