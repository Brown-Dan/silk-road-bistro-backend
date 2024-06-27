package uk.danbrown.apprenticeshipchineserestaurantbackend.cucumber.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import uk.co.autotrader.traverson.Traverson;
import uk.co.autotrader.traverson.http.ApacheHttpTraversonClientAdapter;
import uk.co.autotrader.traverson.http.Response;
import uk.co.autotrader.traverson.http.TextBody;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.ArticleResource;

public class TraversonClient {

    private final Traverson traverson = new Traverson(new ApacheHttpTraversonClientAdapter(HttpClientBuilder.create().build()));

    public Response<JSONObject> getArticles(Integer limit) {
        return traverson.from("http://localhost:8080/articles?limit=" + limit).get();
    }

    public Response<JSONObject> createArticle(String articleResource) {
        return traverson.from("http://localhost:8080/articles").post(new TextBody(articleResource, "application/json"));
    }
}
