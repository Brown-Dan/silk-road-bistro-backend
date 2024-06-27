package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.ArticleEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper.ArticleEntityMapper;

import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.Article.ARTICLE;

@Repository
public class ArticleRepository {

    private final DSLContext db;
    private final ArticleEntityMapper articleEntityMapper;

    public ArticleRepository(DSLContext db, ArticleEntityMapper articleEntityMapper) {
        this.db = db;
        this.articleEntityMapper = articleEntityMapper;
    }

    public Optional<Article> getArticleByTitle(String title) {
        ArticleEntity article = db.selectFrom(ARTICLE).where(ARTICLE.TITLE.eq(title)).fetchOneInto(ArticleEntity.class);
        return Optional.ofNullable(article).map(articleEntityMapper::toDomain);
    }

    public List<Article> getArticles(Integer limit) {
        return db.selectFrom(ARTICLE).orderBy(ARTICLE.DATE.desc()).limit(limit).fetchInto(ArticleEntity.class).stream()
                .map(articleEntityMapper::toDomain)
                .toList();
    }

    public Article createArticle(Article article) throws FailureInsertingEntityException {
        ArticleEntity insertedArticle = db.insertInto(ARTICLE)
                .set(ARTICLE.TITLE, article.title())
                .set(ARTICLE.CONTENT, article.content())
                .set(ARTICLE.DATE, article.date())
                .returningResult().fetchOneInto(ArticleEntity.class);

        return Optional.ofNullable(insertedArticle).map(articleEntityMapper::toDomain)
                .orElseThrow(() -> new FailureInsertingEntityException(article));
    }
}
