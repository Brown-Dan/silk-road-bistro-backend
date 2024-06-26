package uk.danbrown.apprenticeshipchineserestaurantbackend.Service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Domain.Article;
import uk.danbrown.apprenticeshipchineserestaurantbackend.Repository.InformationRepositoryOrchestrator;

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
