package mep.pox.authentication.handlers;

import mep.pox.authentication.handlers.oauth.exceptions.RequiredParamNotPresentException;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.function.Supplier;

import static org.springframework.web.reactive.function.server.ServerResponse.temporaryRedirect;

public class OAuthHandler {

    // required by any oauth flow (except password which is purposly not supported)
    private static final List<String> REQUIRED_PARAMS = List.of("client_id", "redirect_uri", "scopes");

    private static final List<String> CODE_REQUEST_PARAMS = List.of("response_type", "client_id", "redirect_uri", "scope", "state");

    public Mono<ServerResponse> extractOAuthInfoAndRedirectToConfirm(final ServerRequest request, Model model) {
        String grant_type = request.queryParam("grant_type").orElseThrow(requiredParamNotPresent("grant_type"));

        return null;
    }

    // auth code
    private Mono<ServerResponse> handleGETCodeRequest(final ServerRequest request, Model model) {
        for (String item: CODE_REQUEST_PARAMS) {
            model.addAttribute(item, request.queryParam(item).orElseThrow(requiredParamNotPresent(item)));
        }
        return temporaryRedirect(URI.create("/oauth/confirm_access")).build();
    }

    private Mono<ServerResponse> handlePOSTCodeRequest(final ServerRequest request) {
        return null;
    }

    private Mono<ServerResponse> handleAuthCodeGrant(final ServerRequest request, Model model) {
        return null;
    }

    // implicit
    private Mono<ServerResponse> handleImplicitFlow(final ServerRequest request, Model model) {
        for (String item : REQUIRED_PARAMS) {
            model.addAttribute(item, request.queryParam(item).orElseThrow(requiredParamNotPresent(item)));
        }
        return null;
    }

    private Supplier<RuntimeException> requiredParamNotPresent(String param) {
        return () -> new RequiredParamNotPresentException(param);
    }
}
