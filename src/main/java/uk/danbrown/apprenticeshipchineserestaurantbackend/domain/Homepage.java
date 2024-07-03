package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

import java.util.List;

public record Homepage(List<Article> articles, OpeningHours openingHours) {

    public static final class Builder {
        private List<Article> articles;
        private OpeningHours openingHours;

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

        public Homepage build() {
            return new Homepage(articles, openingHours);
        }
    }
}
