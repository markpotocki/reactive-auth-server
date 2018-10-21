package mep.pox.authentication.handler;

import mep.pox.authentication.token.service.VerificationService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.mock;

public class OAuthHandlerTest {

    @Autowired
    private WebTestClient webTestClient;
    private VerificationService verificationService;

    @Before
    public void setup() {
        this.verificationService = mock(VerificationService.class);
    }


}
