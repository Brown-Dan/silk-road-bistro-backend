package uk.danbrown.apprenticeshipchineserestaurantbackend.domain;

import java.util.List;
import java.util.Map;

public record Menu(Map<String, List<MenuItem>> categories) {
}
