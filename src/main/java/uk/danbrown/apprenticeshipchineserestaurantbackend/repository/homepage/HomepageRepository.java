package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.HomepageEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Location;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.Homepage.HOMEPAGE;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage.Builder.aHomepage;

@Repository
public class HomepageRepository {

    private final DSLContext db;
    private final RequestContextManager requestContextManager;

    public HomepageRepository(DSLContext db, RequestContextManager requestContextManager) {
        this.db = db;
        this.requestContextManager = requestContextManager;
    }

    public Homepage getHomepage() throws EntityNotFoundException {
        Optional<HomepageEntity> homepageEntity = db.selectFrom(HOMEPAGE)
                .where(HOMEPAGE.ID.eq(requestContextManager.getRequestContext().currentId()))
                .fetchOptionalInto(HomepageEntity.class);

        return homepageEntity.map(this::mapHomepageEntityToHomepage)
                .orElseThrow(() -> new EntityNotFoundException(requestContextManager.getRequestContext().currentId()));
    }

    private Homepage mapHomepageEntityToHomepage(HomepageEntity homepageEntity) {
        return aHomepage()
                .withBiography(homepageEntity.getBiography())
                .withLocation(new Location(homepageEntity.getAddress()))
                .withImages(Optional.ofNullable(homepageEntity.getImages()).map(images -> Arrays.stream(images).toList())
                        .orElse(List.of()))
                .build();
    }
}
