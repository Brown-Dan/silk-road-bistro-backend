package uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;

public record Offer(String title, String content, boolean enabled, Double minimumSpend, Integer discountPercentage,
                    String offerCode) {

    public static final class Builder {
        private String title;
        private String content;
        private boolean enabled;
        private Double minimumSpend;
        private Integer discountPercentage;
        private String offerCode;

        private Builder() {
        }

        public static Builder anOffer() {
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

        public Builder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder withMinimumSpend(Double minimumSpend) {
            this.minimumSpend = minimumSpend;
            return this;
        }

        public Builder withDiscountPercentage(Integer discountPercentage) {
            this.discountPercentage = discountPercentage;
            return this;
        }

        public Builder withOfferCode(String offerCode) {
            this.offerCode = offerCode;
            return this;
        }

        public Offer build() {
            return new Offer(title, content, enabled, minimumSpend, discountPercentage, offerCode);
        }
    }
}
