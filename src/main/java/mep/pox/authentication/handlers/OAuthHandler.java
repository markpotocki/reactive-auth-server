package mep.pox.authentication.handlers;

import lombok.RequiredArgsConstructor;
import mep.pox.authentication.handlers.oauth.exceptions.RequiredParamNotPresentException;
import mep.pox.authentication.token.service.VerificationService;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;
import java.util.function.Supplier;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
public class OAuthHandler {

    private final VerificationService verificationService;

    // required by any oauth flow (except password which is purposly not supported)
    private static final List<String> REQUIRED_PARAMS = List.of("client_id", "redirect_uri", "scopes");

    private static final List<String> CODE_REQUEST_PARAMS = List.of("response_type", "client_id", "redirect_uri", "scope", "state");

    // /oauth/token fetches the access token
    public Mono<ServerResponse> handleAuthCodeExchange(final ServerRequest request, Principal principal) {
        String grantType = request.queryParam("grant_type").orElseThrow(requiredParamNotPresent("grant_type"));
        String code = request.queryParam("code").orElseThrow(requiredParamNotPresent("code"));
        String redirect_uri = request.queryParam("redirect_uri").orElseThrow(requiredParamNotPresent("redirect_uri"));
        String client_id = request.queryParam("client_id").orElseThrow(requiredParamNotPresent("client_id"));
        String challenge_code = request.queryParam("challenge_code").get(); // TODO add handle for client secret
        String challengeVerfication = request.queryParam("challenge_verification_method").orElse("plain");

        return verificationService.issueTokenByPkce(principal.getName(), grantType, code, redirect_uri, client_id, challenge_code, challengeVerfication)
                .flatMap( jwtToken -> ok().body(BodyInserters.fromObject(jwtToken)));
    }

    private Supplier<RuntimeException> requiredParamNotPresent(String param) {
        return () -> new RequiredParamNotPresentException(param);
    }
}
