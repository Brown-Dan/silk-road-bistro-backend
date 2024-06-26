package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.InformationRepositoryOrchestrator;

import java.util.List;

@Service
public class InformationService {

    private final InformationRepositoryOrchestrator informationRepositoryOrchestrator;

    public InformationService(InformationRepositoryOrchestrator informationRepositoryOrchestrator) {
        this.informationRepositoryOrchestrator = informationRepositoryOrchestrator;
    }

    public List<Article> getArticles(Integer limit) {
        return informationRepositoryOrchestrator.getArticles(limit);
    }
}
