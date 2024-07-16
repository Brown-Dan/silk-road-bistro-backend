package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

import java.time.LocalDateTime;

public record Message(String name, String email, String phoneNumber, String content, LocalDateTime time) {

    public static final class Builder {
        private String name;
        private String email;
        private String phoneNumber;
        private String content;
        private LocalDateTime time;

        private Builder() {
        }

        public static Builder aMessage() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public Message build() {
            return new Message(name, email, phoneNumber, content, time);
        }
    }
}
