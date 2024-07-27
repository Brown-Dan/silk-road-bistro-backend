package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.authorization;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.OrganizationAccountEntity;
import uk.co.autotrader.generated.tables.pojos.UserAccountEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.OrganizationAccount;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.UserAccount;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.util.Optional;

import static uk.co.autotrader.generated.tables.OrganizationAccount.ORGANIZATION_ACCOUNT;
import static uk.co.autotrader.generated.tables.UserAccount.USER_ACCOUNT;

@Repository
public class AuthorizationRepository {

    private final DSLContext db;
    private final RequestContextManager requestContextManager;

    public AuthorizationRepository(DSLContext db, RequestContextManager requestContextManager) {
        this.db = db;
        this.requestContextManager = requestContextManager;
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
        return new OrganizationAccount(organizationAccountEntity.getOrganizationId(), organizationAccountEntity.getPassword(), organizationAccountEntity.getEmail());
    }

    private UserAccount mapUserAccountEntityToUserAccount(UserAccountEntity userAccountEntity) {
        return new UserAccount(userAccountEntity.getUsername(), userAccountEntity.getPassword(), userAccountEntity.getName(), userAccountEntity.getEmail());
    }

    public boolean userAccountExists(String username) {
        return db.fetchExists(db.selectFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.USERNAME.equalIgnoreCase(username)));
    }

    public UserAccount insertUserAccount(UserAccount userAccount) throws FailureInsertingEntityException {
        Optional<UserAccountEntity> userAccountEntity = db.insertInto(USER_ACCOUNT)
                .set(USER_ACCOUNT.ORGANIZATION_ID, requestContextManager.getRequestContext().currentId())
                .set(USER_ACCOUNT.EMAIL, userAccount.email())
                .set(USER_ACCOUNT.PASSWORD, userAccount.password())
                .set(USER_ACCOUNT.USERNAME, userAccount.username())
                .set(USER_ACCOUNT.NAME, userAccount.name())
                .returningResult().fetchOptionalInto(UserAccountEntity.class);

        return userAccountEntity.map(this::mapUserAccountEntityToUserAccount)
                .orElseThrow(() -> new FailureInsertingEntityException(userAccount));
    }

    public UserAccount getUserAccountByUsername(String username) throws EntityNotFoundException {
        Optional<UserAccountEntity> userAccountEntity = db.selectFrom(USER_ACCOUNT)
                .where(USER_ACCOUNT.USERNAME.equalIgnoreCase(username).and(USER_ACCOUNT.ORGANIZATION_ID.eq(requestContextManager.getRequestContext().currentId())))
                .fetchOptionalInto(UserAccountEntity.class);
        return userAccountEntity.map(this::mapUserAccountEntityToUserAccount)
                .orElseThrow(() -> new EntityNotFoundException(username));
    }
}
