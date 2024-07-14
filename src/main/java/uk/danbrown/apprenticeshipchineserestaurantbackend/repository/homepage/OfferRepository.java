package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.OfferEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Offer;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.Article.ARTICLE;
import static uk.co.autotrader.generated.tables.Offer.OFFER;

@Repository
public class OfferRepository {

    private final DSLContext db;
    private final RequestContextManager requestContextManager;

    public OfferRepository(DSLContext db, RequestContextManager requestContextManager) {
        this.db = db;
        this.requestContextManager = requestContextManager;
    }

    public List<Offer> getOffers(Integer limit) {
        return db.selectFrom(OFFER)
                .where(OFFER.HOMEPAGE_ID.eq(requestContextManager.getRequestContext().currentId()))
                .limit(limit).fetchInto(OfferEntity.class).stream()
                .map(this::toDomain)
                .toList();
    }

    public Offer insertOffer(Offer offer) throws FailureInsertingEntityException {
        OfferEntity insertedOffer = db.insertInto(OFFER)
                .set(ARTICLE.HOMEPAGE_ID, requestContextManager.getRequestContext().currentId())
                .set(ARTICLE.TITLE, offer.title())
                .set(ARTICLE.CONTENT, offer.content())
                .returningResult().fetchOneInto(OfferEntity.class);

        return Optional.ofNullable(insertedOffer).map(this::toDomain)
                .orElseThrow(() -> new FailureInsertingEntityException(offer));
    }

    private Offer toDomain(OfferEntity offerEntity) {
        return new Offer(offerEntity.getTitle(), offerEntity.getContent(), true);
    }
}
