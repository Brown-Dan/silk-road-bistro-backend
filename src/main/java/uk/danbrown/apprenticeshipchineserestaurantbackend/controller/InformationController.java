package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ArticleResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.InformationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/information")
public class InformationController {

    private final InformationService informationService;

    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/articles")
    public List<ArticleResource> getArticles(@RequestParam Optional<Integer> limit) {
        return informationService.getArticles(limit.orElse(3)).stream().map(ArticleResource::fromDomain).toList();
    }
}
