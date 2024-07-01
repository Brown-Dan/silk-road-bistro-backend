package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.ArticlesService;

@RestController
@RequestMapping("/homepage")
public class HomepageController {


    private final ArticlesService articlesService;

    public HomepageController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GetMapping
    public Homepage getHomepage() {
        return new Homepage(articlesService.getArticles(3));
    }
}
