package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ArticleResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.ArticlesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticlesService articlesService;

    public ArticlesController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @PostMapping
    public ResponseEntity<ArticleResource> createArticle(@RequestBody ArticleResource articleResource) throws FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
        Article createdArticle = articlesService.createArticle(articleResource.toDomain());
        return ResponseEntity.status(201).body(ArticleResource.fromDomain(createdArticle));
    }

    @GetMapping()
    public List<ArticleResource> getArticles(@RequestParam Optional<Integer> limit) {
        return articlesService.getArticles(limit.orElse(3)).stream().map(ArticleResource::fromDomain).toList();
    }
}
