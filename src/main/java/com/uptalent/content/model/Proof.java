package com.uptalent.content.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("proofs")
@Data
@Builder
public class Proof {

    @Id
    private String id;
    private Integer iconNumber;
    private String title;
    private String summary;
    private String content;
    private LocalDateTime published;
    private ContentStatus status;


}
