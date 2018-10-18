package mep.pox.authentication.token.service;

import mep.pox.authentication.token.JwtToken;
import reactor.core.publisher.Mono;

public interface VerificationService {

    Mono<JwtToken> issueTokenByPkce(String userId, String grantType, String code, String redirectUri, String clientId, String challenge, String verificationMethod);
    Mono<JwtToken> issueTokenBySecret();
}
