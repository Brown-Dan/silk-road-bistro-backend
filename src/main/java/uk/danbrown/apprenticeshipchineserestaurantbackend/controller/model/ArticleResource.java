package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model;

import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;

import java.time.LocalDate;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article.Builder.anArticle;

public record ArticleResource(String title, String content) {

    public static ArticleResource fromDomain(Article article) {
        return new ArticleResource(article.title(), article.content());
    }

    public Article toDomain() {
        return anArticle()
                .withTitle(title)
                .withContent(content)
                .withDate(LocalDate.now())
                .build();
    }
}
