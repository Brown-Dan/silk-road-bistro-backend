package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.TakeawayOrderEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Location;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.MenuItem;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Order;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.TakeawayOrder.TAKEAWAY_ORDER;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Order.Builder.anOrder;

@Repository
public class OrdersRepository {

    private final DSLContext db;
    private final RequestContextManager requestContextManager;
    private final MenuRepository menuRepository;
    private final ObjectMapper objectMapper;

    public OrdersRepository(DSLContext db, RequestContextManager requestContextManager, MenuRepository menuRepository, ObjectMapper objectMapper) {
        this.db = db;
        this.requestContextManager = requestContextManager;
        this.menuRepository = menuRepository;
        this.objectMapper = objectMapper;
    }

    public Order insertOrder(Order order) throws FailureInsertingEntityException {
        Optional<TakeawayOrderEntity> takeawayOrderEntity = db.insertInto(TAKEAWAY_ORDER)
                .set(TAKEAWAY_ORDER.ORGANIZATION_ID, requestContextManager.getRequestContext().currentId())
                .set(TAKEAWAY_ORDER.ITEM_NAME, order.items().stream().map(MenuItem::name).toArray(String[]::new))
                .set(TAKEAWAY_ORDER.USER_ID, order.username())
                .set(TAKEAWAY_ORDER.ADDRESS, mapLocationToString(order.address()))
                .returningResult().fetchOptionalInto(TakeawayOrderEntity.class);

        return takeawayOrderEntity.map(this::mapOrderEntityToOrder).orElseThrow(() -> new FailureInsertingEntityException(order));
    }

    public Order getOrdersByUserId(String userId) {
        Optional<TakeawayOrderEntity> takeawayOrderEntity = db.selectFrom(TAKEAWAY_ORDER)
                .where(TAKEAWAY_ORDER.USER_ID.eq(userId).and(TAKEAWAY_ORDER.ORGANIZATION_ID.eq(requestContextManager.getRequestContext().currentId())))
                .fetchOptionalInto(TakeawayOrderEntity.class);

        return takeawayOrderEntity.map(this::mapOrderEntityToOrder).orElse(null);
    }

    public List<Order> getOrders() {
        List<TakeawayOrderEntity> takeawayOrderEntities = db.selectFrom(TAKEAWAY_ORDER)
                .where(TAKEAWAY_ORDER.ORGANIZATION_ID.eq(requestContextManager.getRequestContext().currentId()))
                .fetchInto(TakeawayOrderEntity.class);

        return takeawayOrderEntities.stream().map(this::mapOrderEntityToOrder).toList();
    }

    private Order mapOrderEntityToOrder(TakeawayOrderEntity takeawayOrderEntity) {
        return anOrder()
                .withItems(Arrays.stream(takeawayOrderEntity.getItemName()).map(menuRepository::getMenuItemById).toList())
                .withUsername(takeawayOrderEntity.getUserId())
                .withAddress(mapStringToLocation(takeawayOrderEntity.getAddress()))
                .withDelivered(takeawayOrderEntity.getDelivered())
                .build();
    }

    private String mapLocationToString(Location location) {
        try {
            return objectMapper.writeValueAsString(location);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    private Location mapStringToLocation(String location) {
        try {
            return objectMapper.readValue(location, Location.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
