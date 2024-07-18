package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

public record MenuItem(String name, String category, double price, boolean isVegan) {

    public static final class Builder {
        private String name;
        private String category;
        private double price;
        private boolean isVegan;

        private Builder() {
        }

        public static Builder aMenuItem() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder withIsVegan(boolean isVegan) {
            this.isVegan = isVegan;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(name, category, price, isVegan);
        }
    }
}
