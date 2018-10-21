package mep.pox.authentication;

import lombok.RequiredArgsConstructor;
import mep.pox.authentication.handlers.OAuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@RequiredArgsConstructor
public class OAuthRouter {

    private final OAuthHandler oAuthHandler;

    @Bean
    public RouterFunction<ServerResponse> oauthRouter(OAuthHandler handler) {
        return RouterFunctions.route(POST("/oauth/token").and(accept(MediaType.APPLICATION_JSON)), handler::handleAuthCodeExchange);
    }
}
