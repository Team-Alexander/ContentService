package io.github.uptalent.content.repository;

import io.github.uptalent.content.model.document.KudosHistory;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KudosHistoryRepository extends MongoRepository<KudosHistory, String> {
    @Aggregation(pipeline = {
            "{$match: {proofId: ?0, sponsorId: ?1}}",
            "{$group: { _id: null, totalKudos: { $sum: '$kudos' } } }"
    })
    Optional<Long> sumKudosByProofIdAndSponsorId(String proofId, long sponsorId);
}
