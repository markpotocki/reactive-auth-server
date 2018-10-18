package mep.pox.authentication.token.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccessCodeTokenRepository extends ReactiveMongoRepository<AccessCodeToken, String> {
}
