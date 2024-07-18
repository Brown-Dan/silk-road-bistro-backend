package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.MenuItemEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Menu;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.MenuItem;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper.MenuEntitiesToMenuMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.MenuItem.MENU_ITEM;

@Repository
public class MenuRepository {

    private final DSLContext db;
    private final RequestContextManager requestContextManager;
    private final MenuEntitiesToMenuMapper menuItemEntitiesToMenuMapper;

    public MenuRepository(DSLContext db, RequestContextManager requestContextManager, MenuEntitiesToMenuMapper menuItemEntitiesToMenuMapper) {
        this.db = db;
        this.requestContextManager = requestContextManager;
        this.menuItemEntitiesToMenuMapper = menuItemEntitiesToMenuMapper;
    }

    public Menu getMenu() {
        List<MenuItemEntity> entities = db.selectFrom(MENU_ITEM)
                .where(MENU_ITEM.ORGANIZATION_ID.eq(requestContextManager.getRequestContext().currentId()))
                .fetchInto(MenuItemEntity.class);

        return menuItemEntitiesToMenuMapper.map(entities);
    }

    public MenuItem insertMenuItem(MenuItem menuItem) throws FailureInsertingEntityException {
        Optional<MenuItemEntity> menuItemEntity = db.insertInto(MENU_ITEM)
                .set(MENU_ITEM.ORGANIZATION_ID, requestContextManager.getRequestContext().currentId())
                .set(MENU_ITEM.ITEM_NAME, menuItem.name())
                .set(MENU_ITEM.CATEGORY, menuItem.category())
                .set(MENU_ITEM.PRICE, BigDecimal.valueOf(menuItem.price()))
                .set(MENU_ITEM.IS_VEGAN, menuItem.isVegan())
                .returningResult()
                .fetchOptionalInto(MenuItemEntity.class);

        return menuItemEntity.map(menuItemEntitiesToMenuMapper::mapMenuItemEntityToMenuItem)
                .orElseThrow(() -> new FailureInsertingEntityException(menuItemEntity));
    }
}
