package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.mapper.ArticleResourceMapper;
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
    private final ArticleResourceMapper articleResourceMapper;

    public ArticlesController(ArticlesService articlesService, ArticleResourceMapper articleResourceMapper) {
        this.articlesService = articlesService;
        this.articleResourceMapper = articleResourceMapper;
    }

    @PostMapping
    public ResponseEntity<ArticleResource> createArticle(@RequestBody ArticleResource articleResource) throws FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
        Article createdArticle = articlesService.createArticle(articleResourceMapper.toDomain(articleResource));
        return ResponseEntity.status(201).body(ArticleResource.fromDomain(createdArticle));
    }

    @GetMapping()
    public List<ArticleResource> getArticles(@RequestParam Optional<Integer> limit) {
        return articlesService.getArticles(limit.orElse(3)).stream().map(ArticleResource::fromDomain).toList();
    }
}
