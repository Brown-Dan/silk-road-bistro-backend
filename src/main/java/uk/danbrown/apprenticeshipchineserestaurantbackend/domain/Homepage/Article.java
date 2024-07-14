package uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;

import java.time.LocalDate;

public record Article(String title, String content, LocalDate date) {

    public static final class Builder {
        private String title;
        private String content;
        private LocalDate date;

        private Builder() {
        }

        public static Builder anArticle() {
            return new Builder();
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Article build() {
            return new Article(title, content, date);
        }
    }
}
