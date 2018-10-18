package mep.pox.authentication.token.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class AccessCodeToken {
    @Id
    private String id;
    private String codeVerificationMethod;
    private String challengeCode;
    private String accessCode;
    private String userId;
    private String clientId;
    private String redirectUri;
    private Long expiration;

    public AccessCodeToken(String userId, String clientId, String challengeCode, String codeVerificationMethod, String redirectUri) {
        this.userId = userId;
        this.clientId = clientId;
        this.challengeCode = challengeCode;
        this.codeVerificationMethod = codeVerificationMethod;
        this.redirectUri = redirectUri;
    }
}
