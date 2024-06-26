package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper;

import org.springframework.stereotype.Component;
import uk.co.autotrader.generated.tables.pojos.ArticleEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article.Builder.anArticle;

@Component
public class ArticleMapper {

    public Article toDomain(ArticleEntity article) {
        return anArticle()
                .withTitle(article.getTitle())
                .withContent(article.getContent())
                .withDate(article.getDate())
                .build();
    }

    public ArticleEntity toEntity(Article article) {
        return new ArticleEntity(
                article.title(),
                article.content(),
                article.date()
        );
    }
}
