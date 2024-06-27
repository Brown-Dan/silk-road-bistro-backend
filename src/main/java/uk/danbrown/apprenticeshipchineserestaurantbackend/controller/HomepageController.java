package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.mapper.ArticleResourceMapper;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ArticleResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.Articles;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.HomepageService;

import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class HomepageController {

    private final HomepageService homepageService;
    private final ArticleResourceMapper articleResourceMapper;

    public HomepageController(HomepageService homepageService, ArticleResourceMapper articleResourceMapper) {
        this.homepageService = homepageService;
        this.articleResourceMapper = articleResourceMapper;
    }

    @PostMapping
    public ResponseEntity<ArticleResource> createArticle(@RequestBody ArticleResource articleResource) throws FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
        Article createdArticle = homepageService.createArticle(articleResourceMapper.toDomain(articleResource));
        return ResponseEntity.status(201).body(ArticleResource.fromDomain(createdArticle));
    }

    @GetMapping()
    public Articles getArticles(@RequestParam Optional<Integer> limit) {
        return new Articles(homepageService.getArticles(limit.orElse(3)).stream().map(ArticleResource::fromDomain).toList());
    }
}
