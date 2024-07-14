package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.authorization;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.OrganizationAccountEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.OrganizationAccount;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.util.Optional;

import static uk.co.autotrader.generated.tables.OrganizationAccount.ORGANIZATION_ACCOUNT;

@Repository
public class AuthorizationRepository {

    private final DSLContext db;

    public AuthorizationRepository(DSLContext db) {
        this.db = db;
    }

    public boolean organizationAccountExists(String organizationId) {
        return db.fetchExists(db.selectFrom(ORGANIZATION_ACCOUNT)
                .where(ORGANIZATION_ACCOUNT.ORGANIZATION_ID.equalIgnoreCase(organizationId)));
    }

    public OrganizationAccount insertOrganizationAccount(OrganizationAccount organizationAccount) throws FailureInsertingEntityException {
        Optional<OrganizationAccountEntity> organizationAccountEntity = db.insertInto(ORGANIZATION_ACCOUNT)
                .set(ORGANIZATION_ACCOUNT.ORGANIZATION_ID, organizationAccount.organizationId())
                .set(ORGANIZATION_ACCOUNT.EMAIL, organizationAccount.email())
                .set(ORGANIZATION_ACCOUNT.PASSWORD, organizationAccount.password())
                .returningResult().fetchOptionalInto(OrganizationAccountEntity.class);

        return organizationAccountEntity.map(this::mapOrganizationAccountEntityToCreateOrganizationRequest)
                .orElseThrow(() -> new FailureInsertingEntityException(organizationAccount));
    }

    public OrganizationAccount getOrganizationAccountById(String organizationId) throws EntityNotFoundException {
        Optional<OrganizationAccountEntity> organizationAccountEntity = db.selectFrom(ORGANIZATION_ACCOUNT)
                .where(ORGANIZATION_ACCOUNT.ORGANIZATION_ID.equalIgnoreCase(organizationId))
                .fetchOptionalInto(OrganizationAccountEntity.class);
        return organizationAccountEntity.map(this::mapOrganizationAccountEntityToCreateOrganizationRequest)
                .orElseThrow(() -> new EntityNotFoundException(organizationId));
    }

    private OrganizationAccount mapOrganizationAccountEntityToCreateOrganizationRequest(OrganizationAccountEntity organizationAccountEntity) {
        return new OrganizationAccount(organizationAccountEntity.getOrganizationId(), organizationAccountEntity.getPassword(), organizationAccountEntity.getEmail())
;    }
}
