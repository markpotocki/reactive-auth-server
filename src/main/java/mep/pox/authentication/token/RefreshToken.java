package mep.pox.authentication.token;

import lombok.Data;

@Data
public class RefreshToken implements Token {

    private String token;
    private Long expires;
    private Long issued;
    private String userId;
    private String clientId;

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Long getExpirationDate() {
        return this.expires;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getEncoded() {
        return null;
    }
}
