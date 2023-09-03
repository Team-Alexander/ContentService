package io.github.uptalent.content.controller;

import io.github.uptalent.content.model.request.ContentModerationStatusChange;
import io.github.uptalent.content.model.response.ContentReport;
import io.github.uptalent.content.service.AdminContentService;
import io.github.uptalent.starter.model.enums.ModerationStatus;
import io.github.uptalent.starter.pagination.PageWithMetadata;
import io.github.uptalent.starter.util.enums.EnumValue;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/content/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminContentController {
    private final AdminContentService adminContentService;

    @PostMapping("/block")
    public void blockContent(@RequestBody @Valid ContentModerationStatusChange contentModerationStatusChange) {
        adminContentService.blockContent(contentModerationStatusChange);
    }

    @PostMapping("/unblock")
    public void unblockContent(@RequestBody @Valid ContentModerationStatusChange contentModerationStatusChange) {
        adminContentService.unblockContent(contentModerationStatusChange);
    }

    @GetMapping("/contents")
    public PageWithMetadata<ContentReport> getUsersWithModerationStatus(
            @Min(value = 0, message = "Page should be greater or equals 0")
            @RequestParam(defaultValue = "0") int page,
            @Positive(message = "Size should be positive")
            @RequestParam(defaultValue = "9") int size,
            @EnumValue(enumClass = ModerationStatus.class)
            @RequestParam(required = false, defaultValue = "ON_MODERATION") String status){
        return adminContentService.getContentsWithModerationStatus(page, size, status);
    }
}
