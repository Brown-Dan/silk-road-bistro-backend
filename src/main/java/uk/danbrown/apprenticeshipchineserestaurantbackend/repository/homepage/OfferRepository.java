package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.OfferEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Offer;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.Article.ARTICLE;
import static uk.co.autotrader.generated.tables.Offer.OFFER;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Offer.Builder.anOffer;

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
                .set(OFFER.HOMEPAGE_ID, requestContextManager.getRequestContext().currentId())
                .set(OFFER.TITLE, offer.title())
                .set(OFFER.CONTENT, offer.content())
                .set(OFFER.ENABLED, offer.enabled())
                .set(OFFER.MINIMUM_SPEND, BigDecimal.valueOf(offer.minimumSpend()))
                .set(OFFER.DISCOUNT_PERCENTAGE, offer.discountPercentage())
                .set(OFFER.OFFER_CODE, offer.offerCode())
                .returningResult().fetchOneInto(OfferEntity.class);

        return Optional.ofNullable(insertedOffer).map(this::toDomain)
                .orElseThrow(() -> new FailureInsertingEntityException(offer));
    }

    private Offer toDomain(OfferEntity offerEntity) {
        return anOffer()
                .withTitle(offerEntity.getTitle())
                .withContent(offerEntity.getContent())
                .withEnabled(Optional.ofNullable(offerEntity.getEnabled()).orElse(false))
                .withMinimumSpend(Optional.ofNullable(offerEntity.getMinimumSpend()).map(BigDecimal::doubleValue).orElse(0.0))
                .withDiscountPercentage(offerEntity.getDiscountPercentage())
                .withOfferCode(offerEntity.getOfferCode()).build();
    }
}
