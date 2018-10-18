package mep.pox.authentication.client;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
