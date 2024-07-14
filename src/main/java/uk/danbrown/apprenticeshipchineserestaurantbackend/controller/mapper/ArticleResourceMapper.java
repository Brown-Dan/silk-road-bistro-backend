package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.mapper;

import org.springframework.stereotype.Component;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ArticleResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article;

import java.time.Clock;
import java.time.LocalDate;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Article.Builder.anArticle;

@Component
public class ArticleResourceMapper {

    private final Clock clock;

    public ArticleResourceMapper(Clock clock) {
        this.clock = clock;
    }

    public Article toDomain(ArticleResource articleResource) {
        return anArticle()
                .withTitle(articleResource.title())
                .withContent(articleResource.content())
                .withDate(LocalDate.now(clock))
                .build();
    }
}
