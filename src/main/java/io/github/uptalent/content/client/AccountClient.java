package io.github.uptalent.content.client;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.uptalent.starter.model.common.Author;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("uptalent-account")
@Retry(name = "default")
public interface AccountClient {
    @GetMapping("/api/v1/account/author")
    Author getAuthor();

    @GetMapping("/api/v1/account/sponsors/balance")
    Long getSponsorBalance();

    @PatchMapping("/api/v1/account/sponsors/balance")
    void updateSponsorBalance(@RequestBody Long balance);

    @GetMapping("/api/v1/account/{id}/email")
    String getAccountEmailById(@PathVariable Long id);
}
