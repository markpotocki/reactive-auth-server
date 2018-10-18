package mep.pox.authentication.token.service;

import mep.pox.authentication.token.JwtToken;
import mep.pox.authentication.token.RefreshToken;
import reactor.core.publisher.Mono;

public interface TokenIssuingService {

    Mono<JwtToken> issueToken(String userId, String clientId);
    Mono<Boolean> verifyToken(JwtToken token);
    Mono<String> createAuthCode();
    Mono<RefreshToken> createRefreshToken(String userId, String clientId);
}
