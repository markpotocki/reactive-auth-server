package mep.pox.authentication.token.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class AccessCodeToken {
    @Id
    private String id;
    private String codeVerificationMethod;
    private String challengeCode;
    private String accessCode;
    private String userId;
    private String clientId;
    private Long expiration;

    public AccessCodeToken(String userId, String clientId, String challengeCode, String codeVerificationMethod) {
        this.userId = userId;
        this.clientId = clientId;
        this.challengeCode = challengeCode;
        this.codeVerificationMethod = codeVerificationMethod;
    }
}
