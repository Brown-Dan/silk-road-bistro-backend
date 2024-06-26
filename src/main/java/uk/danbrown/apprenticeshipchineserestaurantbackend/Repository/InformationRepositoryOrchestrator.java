package uk.danbrown.apprenticeshipchineserestaurantbackend.Repository;

import org.springframework.stereotype.Repository;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Repository.Mapper.ArticleMapper;

import java.util.List;

@Repository
public class InformationRepositoryOrchestrator {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public InformationRepositoryOrchestrator(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public List<Article> getArticles(Integer limit) {
        return articleRepository.getArticles(limit).stream().map(articleMapper::toDomain).toList();
    }
}
