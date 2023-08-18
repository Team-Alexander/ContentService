package io.github.uptalent.content.mapper;

import io.github.uptalent.content.model.document.TemplatedFeedback;
import io.github.uptalent.content.model.request.TemplatedFeedbackInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TemplatedFeedbackMapper {
    TemplatedFeedback toTemplatedFeedback(TemplatedFeedbackInfo templatedFeedbackInfo);
    List<TemplatedFeedbackInfo> toTemplatedFeedbackInfoList(List<TemplatedFeedback> templatedFeedbacks);
}
