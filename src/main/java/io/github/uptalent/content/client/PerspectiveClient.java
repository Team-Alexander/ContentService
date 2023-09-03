package io.github.uptalent.content.client;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.uptalent.starter.model.common.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "perspective-api", url = "https://commentanalyzer.googleapis.com")
@Retry(name = "default")
public interface PerspectiveClient {
    @PostMapping("/v1alpha1/comments:analyze?key={apiKey}")
    String analyzeText(@PathVariable String apiKey, @RequestBody Comment comment);
}
