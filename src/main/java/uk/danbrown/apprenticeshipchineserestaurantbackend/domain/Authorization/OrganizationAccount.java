package uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization;

public record OrganizationAccount(String organizationId, String password, String email) {

    public static final class Builder {
        private String organizationId;
        private String password;
        private String email;

        private Builder() {
        }

        public static Builder aCreateOrganizationRequest() {
            return new Builder();
        }

        public Builder withOrganizationId(String organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public OrganizationAccount build() {
            return new OrganizationAccount(organizationId, password, email);
        }
    }
}
