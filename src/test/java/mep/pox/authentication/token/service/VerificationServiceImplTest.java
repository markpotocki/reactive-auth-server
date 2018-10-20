package mep.pox.authentication.token.service;

import mep.pox.authentication.token.JwtToken;
import mep.pox.authentication.token.domain.AccessCodeToken;
import org.junit.Test;
import org.springframework.util.Base64Utils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VerificationServiceImplTest {

    @Test
    public void givenCorrectAuthCodeIssueJwtToken() throws Exception {
        AccessCodeService accessCodeService = mock(AccessCodeService.class);
        TokenIssuingService tokenIssuingService = mock(TokenIssuingService.class);

        VerificationServiceImpl verificationService = new VerificationServiceImpl(accessCodeService, tokenIssuingService);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest("CHALLENGECODE123".getBytes(StandardCharsets.UTF_8));
        String fCode = Base64Utils.encodeToString(hash);

        when(accessCodeService.decodeAccessCode(anyString())).thenReturn(Mono.just(new AccessCodeToken("foo", "client", fCode, "SH256", "mep.local")));
        when(tokenIssuingService.issueToken("foo", "client")).thenReturn(Mono.just(new JwtToken("foo", List.of("client"))));

        JwtToken jwtToken = verificationService.issueTokenByPkce("foo", "code", "CODE", "mep.local", "client", "CHALLENGECODE123", "SH256").block();
        System.out.println(jwtToken.getEncodedToken());
    }
}
