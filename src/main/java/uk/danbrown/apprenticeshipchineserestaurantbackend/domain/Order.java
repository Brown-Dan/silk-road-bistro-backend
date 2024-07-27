package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Location;

import java.util.List;

public record Order(List<MenuItem> items, Location address, String username, boolean delivered) {

    public static final class Builder {
        private List<MenuItem> items;
        private Location address;
        private String username;
        private boolean delivered;

        private Builder() {
        }

        public static Builder anOrder() {
            return new Builder();
        }

        public Builder withItems(List<MenuItem> items) {
            this.items = items;
            return this;
        }

        public Builder withAddress(Location address) {
            this.address = address;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withDelivered(boolean delivered) {
            this.delivered = delivered;
            return this;
        }

        public Order build() {
            return new Order(items, address, username, delivered);
        }
    }
}
