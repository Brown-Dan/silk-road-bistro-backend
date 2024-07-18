package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

import java.time.LocalDateTime;

public record Message(String summary, String email, String phoneNumber, String content, LocalDateTime time) {

    public static final class Builder {
        private String summary;
        private String email;
        private String phoneNumber;
        private String content;
        private LocalDateTime time;

        private Builder() {
        }

        public static Builder aMessage() {
            return new Builder();
        }

        public Builder withSummary(String summary) {
            this.summary = summary;
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
            return new Message(summary, email, phoneNumber, content, time);
        }
    }
}
