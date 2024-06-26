package uk.danbrown.apprenticeshipchineserestaurantbackend.Service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Repository.ArticleRepository;

import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article addArticle(Article article) throws FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
        Optional<Article> existingArticle = articleRepository.getArticleByTitle(article.title());

        if (existingArticle.isPresent()) {
            throw new EntityAlreadyExistsWithIdException(article.title());
        }
        return articleRepository.addArticle(article);
    }
}
