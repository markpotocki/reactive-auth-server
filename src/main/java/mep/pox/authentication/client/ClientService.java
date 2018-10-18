package mep.pox.authentication.client;

import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<Client> getClientByClientId(String clientId);

}
