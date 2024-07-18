package uk.danbrown.apprenticeshipchineserestaurantbackend.repository.mapper;

import org.springframework.stereotype.Component;
import uk.co.autotrader.generated.tables.pojos.MenuItemEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Menu;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.MenuItem.Builder.aMenuItem;

@Component
public class MenuEntitiesToMenuMapper {

    public Menu map(List<MenuItemEntity> menuItemEntities) {
        Map<String, List<MenuItem>> categories = new HashMap<>();

        menuItemEntities.forEach(item -> {
            categories.computeIfPresent(item.getCategory(), (k, v) -> {
                v.add(mapMenuItemEntityToMenuItem(item));
                return v;
            });
            categories.computeIfAbsent(item.getCategory(), (k) -> new ArrayList<>(List.of(mapMenuItemEntityToMenuItem(item))));
        });
        return new Menu(categories);
    }

    public MenuItem mapMenuItemEntityToMenuItem(MenuItemEntity menuItemEntity) {
        return aMenuItem()
                .withName(menuItemEntity.getItemName())
                .withCategory(menuItemEntity.getCategory())
                .withPrice(menuItemEntity.getPrice().doubleValue())
                .withIsVegan(menuItemEntity.getIsVegan())
                .build();
    }
}
