package mep.pox.authentication.handlers.oauth.exceptions;

public class RequiredParamNotPresentException extends RuntimeException {

    public RequiredParamNotPresentException(String param) {
        super(param + " is required but was not included in the login request.");
    }
}
