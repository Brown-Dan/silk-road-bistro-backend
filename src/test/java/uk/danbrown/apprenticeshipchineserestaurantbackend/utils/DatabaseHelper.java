package uk.danbrown.apprenticeshipchineserestaurantbackend.utils;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import uk.co.autotrader.generated.tables.OpeningHours;
import uk.co.autotrader.generated.tables.pojos.ArticleEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static uk.co.autotrader.generated.tables.Article.ARTICLE;
import static uk.co.autotrader.generated.tables.OpeningHours.OPENING_HOURS;

public class DatabaseHelper {

    private final DSLContext db;

    public DatabaseHelper() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChineseRestaurant", "local", "local");
            db = DSL.using(connection, SQLDialect.POSTGRES);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database");
        }
    }

    public DSLContext getDslContext() {
        return db;
    }

    public void clearTables() {
        db.deleteFrom(ARTICLE).execute();
        db.deleteFrom(OPENING_HOURS).execute();
    }

    public void insertOpeningHoursJson(String openingHours) {
        db.insertInto(OPENING_HOURS)
                .set(OPENING_HOURS.OPENING_HOURS_, openingHours)
                .execute();
    }

    public void insertArticle(ArticleEntity... articles) {
        for (ArticleEntity article : articles) {
            db.insertInto(ARTICLE)
                    .set(ARTICLE.TITLE, article.getTitle())
                    .set(ARTICLE.CONTENT, article.getContent())
                    .set(ARTICLE.DATE, article.getDate())
                    .execute();
        }
    }
}
