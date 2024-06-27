package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model;

import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;

public record ArticleResource(String title, String content) {

    public static ArticleResource fromDomain(Article article) {
        return new ArticleResource(article.title(), article.content());
    }
}
