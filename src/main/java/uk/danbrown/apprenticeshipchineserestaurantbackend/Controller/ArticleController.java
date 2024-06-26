package uk.danbrown.apprenticeshipchineserestaurantbackend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Controller.Model.ArticleResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<ArticleResource> addArticle(@RequestBody ArticleResource articleResource) throws FailureInsertingEntityException, EntityAlreadyExistsWithIdException {
        Article createdArticle = articleService.addArticle(articleResource.toDomain());
        return ResponseEntity.status(201).body(ArticleResource.fromDomain(createdArticle));
    }
}
