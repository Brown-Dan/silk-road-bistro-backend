package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Menu;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.MenuItem;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.MenuService;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuService menuService;

    private MenuController menuController;

    @BeforeEach
    void setUp() {
        menuController = new MenuController(menuService);
    }

    @Test
    void getMenu_shouldCallMenuService() {
        when(menuService.getMenu()).thenReturn(getMenu());

        ResponseEntity<Menu> result = menuController.getMenu();

        verify(menuService).getMenu();
        assertThat(result).isEqualTo(ResponseEntity.ok(getMenu()));
    }

    @Test
    void insertMenuItem_givenMenuItem_shouldCallMenuService() throws FailureInsertingEntityException {
        MenuItem menuItem = new MenuItem("Fried Rice", "Rice", 5, false);
        when(menuService.insertMenuItem(menuItem)).thenReturn(menuItem);

        ResponseEntity<MenuItem> result = menuController.insertMenuItem(menuItem);

        verify(menuService).insertMenuItem(menuItem);
        assertThat(result).isEqualTo(ResponseEntity.ok(menuItem));
    }


    private Menu getMenu() {
        return new Menu(Map.of("Rice", List.of(new MenuItem("Fried Rice", "Rice", 5, false))));
    }
}