package mep.pox.authentication.token;

public interface Token {

    String getEncoded();
    Long getExpirationDate();
    String getUserId();
    String getClientId();
}
