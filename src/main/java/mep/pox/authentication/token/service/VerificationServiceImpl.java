package mep.pox.authentication.token.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mep.pox.authentication.token.JwtToken;
import mep.pox.authentication.token.domain.AccessCodeToken;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class VerificationServiceImpl implements VerificationService {

    private final AccessCodeService accessCodeService;
    private final TokenIssuingService tokenIssuingService;

    @Override
    public Mono<JwtToken> issueTokenByPkce(String userId, String grantType, String code, String redirectUri, String clientId, String challenge, String verificationMethod) {
        Mono<AccessCodeToken> accessCodeToken = accessCodeService.decodeAccessCode(code);
        Mono<String> transformedCode = Mono.fromSupplier( () -> {
            try {
                if (verificationMethod == "SH256") {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(challenge.getBytes(StandardCharsets.UTF_8));
                    return Base64Utils.encodeToUrlSafeString(hash);
                } else if (verificationMethod == "plain") {
                    return challenge;
                } else {
                    throw new RuntimeException("Unsupported verification method.");
                }
            }catch (NoSuchAlgorithmException nsae) {
                log.error("Invalid algorithum for hashing");
                throw new RuntimeException(nsae);
            }
        });

        return Mono
                .zip(accessCodeToken, transformedCode)
                .doOnNext( acTcTuple -> {
                    AccessCodeToken act = acTcTuple.getT1();
                    assertEquals(act.getUserId(), userId);
                    assertEquals(act.getRedirectUri(), redirectUri);
                    assertEquals(act.getClientId(), clientId);
                    assertEquals(act.getChallengeCode(), acTcTuple.getT2());
                }).map( tuple2 -> new JwtToken(userId, List.of(clientId)));
    }

    private Mono<Void> assertEquals(String expected, String actual) {
        if (expected.equals(actual)) {
            return Mono.empty();
        } else {
            return Mono.error(new AuthenticationException("Invalid authentication request"));
        }
    }

    @Override
    public Mono<JwtToken> issueTokenBySecret() {
        return null;
    }
}
