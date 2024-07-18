package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Menu;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.MenuItem;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<Menu> getMenu() {
        return ResponseEntity.ok(menuService.getMenu());
    }

    @PostMapping
    public ResponseEntity<MenuItem> insertMenuItem(@RequestBody MenuItem menuItem) throws FailureInsertingEntityException {
        return ResponseEntity.ok(menuService.insertMenuItem(menuItem));
    }
}
