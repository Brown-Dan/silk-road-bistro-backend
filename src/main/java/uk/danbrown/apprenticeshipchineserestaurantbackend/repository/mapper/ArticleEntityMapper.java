package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper;

import org.springframework.stereotype.Component;
import uk.co.autotrader.generated.tables.pojos.ArticleEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article.Builder.anArticle;

@Component
public class ArticleEntityMapper {

    public Article toDomain(ArticleEntity article) {
        return anArticle()
                .withTitle(article.getTitle())
                .withContent(article.getContent())
                .withDate(article.getDate())
                .build();
    }
}
