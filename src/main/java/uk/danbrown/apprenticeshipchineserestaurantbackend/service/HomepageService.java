package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.HomepageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HomepageService {

    private final HomepageRepository homepageRepository;

    public HomepageService(HomepageRepository homepageRepository) {
        this.homepageRepository = homepageRepository;
    }

    public Article createArticle(Article article) throws FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
        Optional<Article> existingArticle = homepageRepository.getArticleByTitle(article.title());

        if (existingArticle.isPresent()) {
            throw new EntityAlreadyExistsWithIdException(article.title());
        }
        return homepageRepository.createArticle(article);
    }

    public List<Article> getArticles(Integer limit) {
        return homepageRepository.getArticles(limit);
    }
}
