package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

import java.util.List;

public record Homepage(List<Article> articles,
                       OpeningHours openingHours,
                       String biography,
                       Location location,
                       List<String> images,
                       List<Offer> offers) {

    public Homepage.Builder cloneBuilder() {
        return Builder.aHomepage()
                .withArticles(articles)
                .withOpeningHours(openingHours)
                .withBiography(biography)
                .withLocation(location)
                .withImages(images)
                .withOffers(offers);
    }

    public static final class Builder {
        private List<Article> articles;
        private OpeningHours openingHours;
        private String biography;
        private Location location;
        private List<String> images;
        private List<Offer> offers;

        private Builder() {
        }

        public static Builder aHomepage() {
            return new Builder();
        }

        public Builder withArticles(List<Article> articles) {
            this.articles = articles;
            return this;
        }

        public Builder withOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
            return this;
        }

        public Builder withBiography(String biography) {
            this.biography = biography;
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder withImages(List<String> images) {
            this.images = images;
            return this;
        }

        public Builder withOffers(List<Offer> offers) {
            this.offers = offers;
            return this;
        }

        public Homepage build() {
            return new Homepage(articles, openingHours, biography, location, images, offers);
        }
    }
}
