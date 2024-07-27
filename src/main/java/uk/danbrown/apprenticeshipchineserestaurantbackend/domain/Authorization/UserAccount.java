package uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization;

public record UserAccount(String username, String password, String name, String email) {

    public static final class Builder {
        private String username;
        private String password;
        private String name;
        private String email;

        private Builder() {
        }

        public static Builder anUserAccount() {
            return new Builder();
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserAccount build() {
            return new UserAccount(username, password, name, email);
        }
    }
}
