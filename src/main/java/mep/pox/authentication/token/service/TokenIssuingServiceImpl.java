package mep.pox.authentication.token.service;

import lombok.RequiredArgsConstructor;
import mep.pox.authentication.client.ClientService;
import mep.pox.authentication.identity.SignatureService;
import mep.pox.authentication.token.JwtToken;
import mep.pox.authentication.token.RefreshToken;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TokenIssuingServiceImpl implements TokenIssuingService {

    private final SignatureService signatureService;
    private final ClientService authCodeService;

    @Override
    public Mono<Boolean> verifyToken(JwtToken token) {
        return null;
    }

    @Override
    public Mono<JwtToken> issueToken(String userId, String clientId) {
        return null;
    }

    @Override
    public Mono<String> createAuthCode() {
        return null;
    }

    @Override
    public Mono<RefreshToken> createRefreshToken(String userId, String clientId) {
        return null;
    }
}
