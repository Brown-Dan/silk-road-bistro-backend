package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.HomepageEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Location;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.Homepage.HOMEPAGE;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Homepage.Builder.aHomepage;

@Repository
public class HomepageRepository {

    private final DSLContext db;
    private final RequestContextManager requestContextManager;
    private final ObjectMapper objectMapper;

    public HomepageRepository(DSLContext db, RequestContextManager requestContextManager, ObjectMapper objectMapper) {
        this.db = db;
        this.requestContextManager = requestContextManager;
        this.objectMapper = objectMapper;
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
                .withLocation(mapStringToLocation(homepageEntity.getAddress()))
                .withImages(Optional.ofNullable(homepageEntity.getImages()).map(images -> Arrays.stream(images).toList())
                        .orElse(List.of()))
                .build();
    }

    public String updateBiography(String biography) throws FailureInsertingEntityException {
        Optional<HomepageEntity> updatedBiography = db.update(HOMEPAGE)
                .set(HOMEPAGE.BIOGRAPHY, biography)
                .where(HOMEPAGE.ID.eq(requestContextManager.getRequestContext().currentId()))
                .returningResult().fetchOptionalInto(HomepageEntity.class);

        return updatedBiography.map(HomepageEntity::getBiography).orElseThrow(() -> new FailureInsertingEntityException(biography));
    }

    public List<String> updateImages(List<String> images) throws FailureInsertingEntityException {
        Optional<HomepageEntity> updatedImages = db.update(HOMEPAGE)
                .set(HOMEPAGE.IMAGES, images.toArray(new String[]{}))
                .where(HOMEPAGE.ID.eq(requestContextManager.getRequestContext().currentId()))
                .returningResult().fetchOptionalInto(HomepageEntity.class);

        return updatedImages.map(homepage -> Arrays.stream(homepage.getImages()).toList())
                .orElseThrow(() -> new FailureInsertingEntityException(images));
    }

    public Location updateAddress(Location address) throws FailureInsertingEntityException {
        Optional<HomepageEntity> updatedImages = db.update(HOMEPAGE)
                .set(HOMEPAGE.ADDRESS, mapLocationToString(address))
                .where(HOMEPAGE.ID.eq(requestContextManager.getRequestContext().currentId()))
                .returningResult().fetchOptionalInto(HomepageEntity.class);

        return updatedImages.map(homepage -> mapStringToLocation(homepage.getAddress()))
                .orElseThrow(() -> new FailureInsertingEntityException(address));
    }

    public void deleteImage(String imageUrl) throws EntityNotFoundException, FailureInsertingEntityException {
        Optional<HomepageEntity> currentImages = db.selectFrom(HOMEPAGE)
                .where(HOMEPAGE.ID.eq(requestContextManager.getRequestContext().currentId()))
                .fetchOptionalInto(HomepageEntity.class);

        List<String> images = currentImages.map(homepage -> Arrays.stream(homepage.getImages()).toList())
                .orElseThrow(() -> new EntityNotFoundException(imageUrl));

        updateImages(images.stream().filter(img -> !img.equals(imageUrl)).toList());
    }

    private String mapLocationToString(Location location) {
        try {
            return objectMapper.writeValueAsString(location);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    private Location mapStringToLocation(String location) {
        try {
            return objectMapper.readValue(location, Location.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
