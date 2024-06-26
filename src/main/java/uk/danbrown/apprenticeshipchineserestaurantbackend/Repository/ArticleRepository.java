package uk.danbrown.apprenticeshipchineserestaurantbackend.Repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.ArticleEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Repository.Mapper.ArticleMapper;

import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.Article.ARTICLE;

@Repository
public class ArticleRepository {

    private final DSLContext db;
    private final ArticleMapper articleMapper;

    public ArticleRepository(DSLContext db, ArticleMapper articleMapper) {
        this.db = db;
        this.articleMapper = articleMapper;
    }

    public Optional<Article> getArticleByTitle(String title) {
        ArticleEntity article = db.selectFrom(ARTICLE).where(ARTICLE.TITLE.eq(title)).fetchOneInto(ArticleEntity.class);
        return Optional.ofNullable(article).map(articleMapper::toDomain);
    }

    public List<ArticleEntity> getArticles(Integer limit) {
        return db.selectFrom(ARTICLE).orderBy(ARTICLE.DATE).limit(limit).fetchInto(ArticleEntity.class);
    }

    public Article addArticle(Article article) throws FailureInsertingEntityException {
        ArticleEntity insertedArticle = db.insertInto(ARTICLE)
                .set(ARTICLE.TITLE, article.title())
                .set(ARTICLE.CONTENT, article.content())
                .set(ARTICLE.DATE, article.date())
                .returningResult().fetchOneInto(ArticleEntity.class);

        return Optional.ofNullable(insertedArticle).map(articleMapper::toDomain)
                .orElseThrow(() -> new FailureInsertingEntityException(article));
    }
}
