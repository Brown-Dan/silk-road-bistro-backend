package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import org.springframework.stereotype.Repository;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;

import java.util.List;

@Repository
public class InformationRepositoryOrchestrator {

    private final ArticlesRepository articlesRepository;

    public InformationRepositoryOrchestrator(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public List<Article> getArticles(Integer limit) {
        return articlesRepository.getArticles(limit);
    }
}
